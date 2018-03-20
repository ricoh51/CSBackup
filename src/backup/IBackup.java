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
