package backup;

import java.util.prefs.Preferences;

/**
 * Project preferences
 * @author Eric Marchand
 */
public class Prefs {
    
    private static final String LASTPROJECT_KEY = "last project";
    private static final String LASTPROJECT_DEFAULT = "";
    private String lastProject = LASTPROJECT_DEFAULT;
    
    private final Preferences preferences;
    
    public Prefs(Class mainClass){
        preferences = Preferences.userNodeForPackage(mainClass); 
    }
    
    /**
     * Load project preferences
     */
    public void loadPrefs() throws BackupException{
        try {
            lastProject = preferences.get(LASTPROJECT_KEY, LASTPROJECT_DEFAULT);
        } catch (Exception ex) {
            throw new BackupException("Unable to load preferences ! : " + ex.getMessage());
        }
    }
    
    /**
     * @return the absolute name of last opened project
     */
    public String getLastproject(){
        return lastProject;
    }
    
    
    public void setLastProject(String projectFileName){
        preferences.put(LASTPROJECT_KEY, projectFileName);
        lastProject = projectFileName;
    }
    
    
}
