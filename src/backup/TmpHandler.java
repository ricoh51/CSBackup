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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Eric Marchand
 */
public class TmpHandler {
    
    /**
     * Delete previous temp dir and Create a temporary working dir
     * @param tmpPrefix
     */
    public static Path cleanAndCreate(String tmpPrefix) throws Exception{
        cleanUpPreviousSessions(tmpPrefix);
        return createTmpDir(tmpPrefix);
    }
    
    
     /**
     * Delete previous temp dir
     */
    private static void cleanUpPreviousSessions(String tmpPrefix)throws Exception{
        String tempDir = System.getProperty("java.io.tmpdir");
        File[] directories = new File(tempDir).listFiles(File::isDirectory);
        for (File file : directories){
            if (file.toString().startsWith(tempDir + tmpPrefix)){   
                for (File f: file.listFiles()) {
                    f.delete();
                }
                file.delete();
            }
        }
    }  
    
    /**
    * Create a temporary working dir
    */
    private static Path createTmpDir(String tmpPrefix) throws IOException{
        Path tmpDirPath = Files.createTempDirectory(tmpPrefix);
        tmpDirPath.toFile().deleteOnExit();
        System.out.println("backup.TmpHandler.createTmpDir() : " + tmpDirPath);
        return tmpDirPath;
    }
}
