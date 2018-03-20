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
import javax.swing.DefaultListModel;

/**
 * View interface
 * @author Eric Marchand
 */
public interface IBackup {
    public void setController(Controller controller);
    public void setListModels(DefaultListModel ignoreLM, DefaultListModel whereLM);
    public void fillFields(CSB csb);
    public void setAppTitle(String title);
    public void setBtns(boolean removeIgnoreBtn, boolean removeWhereBtn, boolean backupBtn);
    public int ignoreSelected();
    public int whereSelected();
    public File chooseMainProjectFile(File start);
}
