/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    Copyright (C) 2017-2018  Marchand Eric <ricoh51@free.fr>
    
    This file is part of CSBackup.

    CSBackup is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CSBackup is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Freegressi.  If not, see <http://www.gnu.org/licenses/>.

 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package backup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author Eric Marchand
 */
public class ZipHandler {
    /** date added to zip name **/
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    
    /** Model **/
    private CSB csb;
    /** Output stream for zip **/
    private ZipOutputStream out;
    private final int buffer = 2048;
    private final byte data[ ]= new byte[buffer];
    /** size of the projectDir string **/
    private int rootDirLength;
    
    private Path tmpDirPath = null;
    
    public ZipHandler(Path tmpDirPath){
        this.tmpDirPath = tmpDirPath;
    }
    
    public void zip(CSB csb) throws IOException{
        this.csb = csb;
        System.out.println("backup.ZipHandler.zip() \n"
            + "project dir : " + csb.projectDir + "\n"
            + "zip name : " + csb.zipName + "\n" 
        );
        setRootLength();
        String fileName = csb.zipName + "-" + dateFormatter.format(LocalDateTime.now()) + ".zip";
        String absoluteZipName = tmpDirPath + File.separator + fileName;
        FileOutputStream zipFos= new FileOutputStream(absoluteZipName);
        BufferedOutputStream buff = new BufferedOutputStream(zipFos);
        out = new ZipOutputStream(buff);
        out.setMethod(ZipOutputStream.DEFLATED);
        out.setLevel(9);
        
        FileVisitor<Path> fileProcessor = new ProcessFile();
        Files.walkFileTree(Paths.get(csb.projectDir), fileProcessor);
        
        out.close();
        
        Path source = Paths.get(absoluteZipName); 
        for (String dir : csb.wheres){
            System.out.println("backup.ZipHandler.zip() start copying in : " + dir);
            Path desti = Paths.get(dir + File.separator + fileName);
            Files.copy(source, desti, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("backup.ZipHandler.zip() Terminated");
    }
    
    /**
     * Set the length of root, for relative paths
     */
    private void setRootLength(){
        String projectName = csb.projectDir;
        Path p = Paths.get(projectName);
        Path root = p.getParent();
        if (root != null){
            rootDirLength = root.toString().length();
        } else {
            rootDirLength = projectName.length();
        }
    }
    
    
    /**
     * @return true only if path is not in csb.ignores
     */
    private boolean acceptDir(Path path){
        for (String dir : csb.ignores){
            if (dir.equals(path.toString())){
                return false;
            }
        }
        return true;
    }
    /**
     * @return the path relative to csb.projectDir
     */
    private String relativePath(Path path){
        String pathString = path.toString();
        return pathString.substring(rootDirLength + 1, pathString.length());
    }
    /**
     * Add a file to zip
     */
    private void addFile(Path path) throws IOException{
        FileInputStream fi = new FileInputStream(path.toFile());
        try (BufferedInputStream buffi = new BufferedInputStream(fi, buffer)) {
            String relativeName = relativePath(path);
            ZipEntry entry= new ZipEntry(relativeName);
            
            out.putNextEntry(entry);
            int count;
            while((count = buffi.read(data, 0, buffer)) != -1) {
                out.write(data, 0, count);
            }
            out.closeEntry();
        } 
    }
    
    /**
     * Add an empty dir to zip
     */
    private void addEmptyDir(Path dirPath) throws IOException{
        String relativeName = relativePath(dirPath);
        ZipEntry dirEntry = new ZipEntry(relativeName + System.getProperty("file.separator") + ".");
        out.putNextEntry(dirEntry);
    }
    
    
    private final class ProcessFile extends SimpleFileVisitor<Path> {
   
        /**
         * Add a file to zip
         */
        @Override public FileVisitResult visitFile(Path pathFile, BasicFileAttributes aAttrs ) throws IOException {           
            addFile(pathFile);
            return FileVisitResult.CONTINUE;
        }

        /**
         * Add a dir to zip only if rejected (so dir is in the zip and empty)
         */
        @Override  public FileVisitResult preVisitDirectory(Path dirPath, BasicFileAttributes aAttrs) throws IOException {
            boolean hidden = dirPath.toFile().isHidden();
            if (hidden) {
                return FileVisitResult.SKIP_SUBTREE;
            }
            if (acceptDir(dirPath)){
                return FileVisitResult.CONTINUE;
            }
            else { // rejected, keep the dir but empty
                if (csb.keepEmpty){
                    addEmptyDir(dirPath);
                }
                return FileVisitResult.SKIP_SUBTREE;
            }

 

        }
        
  }
    
}
