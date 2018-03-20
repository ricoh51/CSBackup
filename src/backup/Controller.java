package backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Main controller
 * @author Eric Marchand
 */
public class Controller  implements ListDataListener{
    /** Application name **/
    private final String appName = "CSBackup";
    /** Application version **/
    private final String version = "0.1";
    /** Temporary dir prefix **/
    private final String tmpPrefix = "CSBackup-";
    
    private final String extension = "csb";
    /** ListModels for JList **/
    private DefaultListModel ignoreLM, whereLM;
    /** Datas Model **/
    private final CSB csb;
    /** project chooser **/
    private JFileChooser chooserDialog;
    /** project file **/
    private File mainFile;
    /** Zip controller **/
    private final ZipHandler zip;
    /** Prefs controller **/
    private final Prefs prefs;
    
    /** true if the project is saved **/
    private boolean saved;

    private IBackup view;
    
    public Controller(IBackup view){
        this.view = view;
        Path tmpDirPath = null;
        try { // tmp dir stuff
            tmpDirPath = TmpHandler.cleanAndCreate(tmpPrefix);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Unable to create a tmp dir ! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        // project prefs
        prefs = new Prefs(this.getClass());
        try {
            prefs.loadPrefs();
        } catch (BackupException ex) {
            JOptionPane.showMessageDialog(null, "Unable to load prefs! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
        } 
        // datas
        csb = new CSB();
        // zip stuff
        zip = new ZipHandler(tmpDirPath);
        
        setListModels();
        reset();
        
        if (!prefs.getLastproject().trim().equals("")){
            openProject(new File(prefs.getLastproject()));
        } else {
            System.out.println("backup.Main.<init>() last : " + prefs.getLastproject());
            updateUI();
        }
        
    }
    
    public String getProjectDir(){
        return csb.projectDir;
    }
    
    
    private void setListModels(){
        ignoreLM = new DefaultListModel();
        ignoreLM.addListDataListener(this);      
        whereLM = new DefaultListModel();
        whereLM.addListDataListener(this);
        view.setListModels(ignoreLM, whereLM);
    }
    
    /**
     * Reset all controls
     */
    private void reset(){
        csb.reset();
        view.fillFields(csb);
        view.setBtns(false, false, false);
        saved = false;
        mainFile = null;
    }
    
    private void updateUI(){
        view.fillFields(csb);
        boolean ok = Utils.dirExists(csb.projectDir);
        Enumeration<Object> e = ignoreLM.elements();
        while (e.hasMoreElements()){
            ok &= Utils.dirExists(e.nextElement().toString());
        }
        e = whereLM.elements();
        while (e.hasMoreElements()){
            ok &= Utils.dirExists(e.nextElement().toString());
        }
        ok &= !"".equals(csb.zipName.trim());
        ok &= whereLM.getSize() > 0;
        String projectName = mainFile == null ? "No name" : mainFile.getName();
        view.setAppTitle(appName + " - " + version + ", [" + projectName + "]" + (saved ? "" : "*"));
        view.setBtns(view.ignoreSelected() >= 0, view.whereSelected() >= 0, ok);
    }
    
    public final void openProject(File file){
        try {
            String json = Utils.loadTextDiskFile(file);
            csb.fromJSON(json);
            ignoreLM.removeAllElements();
            for (String ignore : csb.ignores){
                ignoreLM.addElement(ignore);
            }
            whereLM.removeAllElements();
            for (String where : csb.wheres){
                whereLM.addElement(where);
            }
            //view.fillFields(csb);
            saved = true;
            mainFile = file;
            updateUI();
            prefs.setLastProject(mainFile.getCanonicalPath());
        } catch (BackupException ex) {
            JOptionPane.showMessageDialog(null, "Unable to open the project ! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to save preferences ! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public File getMainFile(){
        return mainFile;
    }
    
    
    private void save(){
        try {
            String json = csb.toJSON();
            Utils.saveTextFileDisk(mainFile, json);
            saved = true;
            updateUI();
        } catch (BackupException ex) {
            JOptionPane.showMessageDialog(null, "Unable to save the project ! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void saveProjectAs(File file){
        mainFile = changeExtension(file);
        try {
            prefs.setLastProject(mainFile.getCanonicalPath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to save preferences ! " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
        }
        save();
    }
    
    public void saveProject(){
        if (mainFile == null){
            mainFile = view.chooseMainProjectFile(mainFile);
            if (mainFile != null){
                saveProjectAs(mainFile);
            }
            
        } else {
            save();
        }
    }
    
    private String getExtension(File f){
        String ext = "";
        String path = f.getAbsolutePath();
        int i = path.lastIndexOf('.');
        if (i >= 0) {
            ext = path.substring(i+1);
        }
        return ext;
    }
    
    private File changeExtension(File f){
        String ext = getExtension(f);
        if (!extension.toLowerCase().equals(ext.toLowerCase())){
            return new File(f.getAbsolutePath() + "." + extension);
        }
        return f;
    }
    
    public void quit(){
        System.exit(0);
    }
    
    public void keepEmptyChanged(boolean keepEmpty){
       csb.keepEmpty = keepEmpty; 
       saved = false;
       updateUI();
    }
    
    public void dirProjectChoosed(File file){
        //File good = changeExtension(file);
        csb.projectDir = file.toString();
        saved = false;
        updateUI();
    }
    
    public void ignoreAdded(File file){
        csb.ignores.add(file.toString());
        ignoreLM.addElement(file.toString());
        saved = false;
        updateUI();
    }
    
    public void ignoreRemoved(int index){
        if (index >= 0){
            csb.ignores.remove(index);
            ignoreLM.remove(index);
            saved = false;
            updateUI();
        }
    }
    
    public void whereAdded(File file){
        csb.wheres.add(file.toString());
        whereLM.addElement(file.toString());
        saved = false;
        updateUI();
    }
    
    public void whereRemoved(int index){
        if (index >= 0){
            System.out.println("backup.Controller.whereRemoved() index : " + index);
            csb.wheres.remove(index);
            whereLM.remove(index);
            saved = false;
            updateUI();
        }
    }
    
    public void itemListSelected(){
        updateUI();
    }
    
    public void zipNameChanged(String name){
        csb.zipName = name;
        saved = false;
        updateUI();
    }
    
    public void backup(){
        if (!saved){
            saveProject();
        }
        if (saved){
            try {
                zip.zip(csb);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to zip the project ! : " + ex.getMessage(), "Error !", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Something is bad ! : ", "Error !", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void newProject(){
        if (!saved){
            int result = JOptionPane.showConfirmDialog(null, 
                    "Current project is not saved ! Save ?", "Warning", JOptionPane.WARNING_MESSAGE,
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION){
                saveProject();
            }
            
        }

        reset();
        saved = false;
        updateUI();
       
    }
    
    @Override
    public void intervalAdded(ListDataEvent e) {
        updateUI();
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        updateUI();
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        updateUI();
    }
    
    
}
