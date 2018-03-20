package backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Eric Marchand
 */
public class Utils {
    
    public static boolean dirExists(String dir){
        File file = new File(dir);
        return file.isDirectory();
    }
    
     /**
     * @return a File which is a directory choosed (dir = start directory) 
     * or null if cancelled
     */
    public static File dirChooser(JFrame parent, String dir, String title){
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(dir));
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) { 
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
    
    
    /**
     * @return a File choosed or null
     */
    public static File fileChooser(JFrame parent, String dir, String title){
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(dir));
        fc.setDialogTitle(title);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file;
        } else {
            return null;
        }
    }
    
    /**
     * Save a text to the disk
     */
    public static void saveTextFileDisk(File file, String text) throws BackupException{
        Writer writer = null ;
        try {
            writer =  new FileWriter(file) ;
            writer.write(text) ;
        }  catch (IOException e) {
            //System.err.println("backup.Utils.saveTextFileDisk() error : " + e.getMessage());
            throw new BackupException("Unable to save : " + file.getName() + " : " + e.getMessage());
        }  finally {
            if (writer != null) {
                try {
                     writer.close() ;
                }  catch (IOException e) {
                     System.err.println("Erreur " + e.getMessage()) ;
                }
           }
        }
    }
    
    /**
     * Read a text from a disk
     */
    public static String loadTextDiskFile(File file) throws BackupException{
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            //System.err.println("backup.Utils.saveTextFileDisk() error : " + e.getMessage());
            throw new BackupException(e.getMessage());
        }
        return sb.toString();
    }
    
    
    
}
