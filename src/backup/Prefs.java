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
