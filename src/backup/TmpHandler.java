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
