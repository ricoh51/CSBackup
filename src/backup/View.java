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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * View
 * @author Eric Marchand
 */
public class View extends javax.swing.JFrame implements IBackup{

    private Action newAction, openAction, saveAction, saveAsAction, quitAction;
    /** project chooser **/
    private JFileChooser chooserDialog;
    
    private Controller controller;
    
    public View() {
        initComponents();
        setFileChooser();
        createActions();
        try {
            setIcon();
        } catch (BackupException ex) {
            System.err.println("backup.View.<init>() Unable to load icon");
        }
    }
    
    private void setIcon() throws BackupException{
        BufferedImage icon = loadImageResources("/res/icon-60x60.png");
        setIconImage(icon);
        
    }
    
    /**
     * Charge une image depuis un des repertoires resources de l'application
     * @param path le chemin vers l'image (commence par un "/"
     * @return la BufferedImage ou null si l'image n'a pas été trouvée
     * @throws VGException si le path donné ne correcpond pas à une image
     */
    public BufferedImage loadImageResources(String path) throws BackupException{
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null){
                BufferedImage image = ImageIO.read(url);
                return image;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new BackupException("Unable to load Image : " + path + " : " + e.getMessage());
        }        
    }      
    
    @Override
    public void setController(Controller controller){
        this.controller = controller;
    }
    
    @Override
    public void setListModels(DefaultListModel ignoreLM, DefaultListModel whereLM){
        ignoreList.setModel(ignoreLM);
        whereList.setModel(whereLM);
    }
    
    /**
     * Set UI from csb values
     */
    @Override
    public void fillFields(CSB csb){
        projectDirTF.setText(csb.projectDir);
        zipNameTF.setText(csb.zipName);
        keepEmptyCB.setSelected(csb.keepEmpty);
    }
    
    @Override
    public void setAppTitle(String title){
        setTitle(title);
    }

    @Override
    public void setBtns(boolean removeIgnoreBtn, boolean removeWhereBtn, boolean backupBtn){
        this.removeIgnoreBtn.setEnabled(removeIgnoreBtn);
        this.removeWhereBtn.setEnabled(removeWhereBtn);
        this.backupBtn.setEnabled(backupBtn);
    }
    
    @Override
    public int ignoreSelected(){
        return ignoreList.getSelectedIndex();
    }
    
    @Override
    public int whereSelected(){
        return whereList.getSelectedIndex();
    }
    
    @Override
    public File chooseMainProjectFile(File start){
        chooserDialog.setCurrentDirectory(start);
        int result = chooserDialog.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            return chooserDialog.getSelectedFile();
        }
        else {
            return null;
        }
    }
    
    private void createActions(){
        newAction = new GenericAction(ActionType.New, "New project", null,
                "New Project", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        openAction = new GenericAction(ActionType.Open,"Open project", null,
                "Open Project", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveAction = new GenericAction(ActionType.Save,"Save project", null,
                "Save Project", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveAsAction = new GenericAction(ActionType.SaveAs,"Save as ...", null,
                "Save as ...", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_MASK));
        quitAction = new GenericAction(ActionType.Quit,"Quit", null,
                "Quit", null);
        newMI.setAction(newAction);
        openMI.setAction(openAction);
        saveMI.setAction(saveAction);
        saveAsMI.setAction(saveAsAction);
        quitMI.setAction(quitAction);
    }
    
    
    
    private void setFileChooser(){
        chooserDialog = new JFileChooser();
        chooserDialog.addChoosableFileFilter(new FileNameExtensionFilter("Backup file (*.csb)", "csb"));
        chooserDialog.setAcceptAllFileFilterUsed(false);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        projectDirTF = new javax.swing.JTextField();
        projectDirChooseBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ignoreList = new javax.swing.JList<>();
        addIgnoreBtn = new javax.swing.JButton();
        removeIgnoreBtn = new javax.swing.JButton();
        keepEmptyCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        whereList = new javax.swing.JList<>();
        addWhereBtn = new javax.swing.JButton();
        removeWhereBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        zipNameTF = new javax.swing.JTextField();
        backupBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newMI = new javax.swing.JMenuItem();
        openMI = new javax.swing.JMenuItem();
        saveMI = new javax.swing.JMenuItem();
        saveAsMI = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        quitMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BackUp 1.0");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Directory to compress :");

        projectDirTF.setEditable(false);
        projectDirTF.setText("jTextField1");

        projectDirChooseBtn.setText("...");
        projectDirChooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectDirChooseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(projectDirTF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectDirChooseBtn)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectDirTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectDirChooseBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ignoreList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ignoreList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ignoreList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ignoreListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ignoreList);

        addIgnoreBtn.setText("Add");
        addIgnoreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIgnoreBtnActionPerformed(evt);
            }
        });

        removeIgnoreBtn.setText("Remove");
        removeIgnoreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeIgnoreBtnActionPerformed(evt);
            }
        });

        keepEmptyCB.setText("Keep Empty");
        keepEmptyCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keepEmptyCBActionPerformed(evt);
            }
        });

        jLabel1.setText("Sub-directories to ignore :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(170, Short.MAX_VALUE)
                        .addComponent(removeIgnoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addIgnoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(keepEmptyCB))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(keepEmptyCB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addIgnoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeIgnoreBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Where to backup ?");

        whereList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        whereList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        whereList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                whereListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(whereList);

        addWhereBtn.setText("Add");
        addWhereBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWhereBtnActionPerformed(evt);
            }
        });

        removeWhereBtn.setText("Remove");
        removeWhereBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWhereBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(removeWhereBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addWhereBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addWhereBtn)
                    .addComponent(removeWhereBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("Zip base name :");

        zipNameTF.setText("jTextField2");
        zipNameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                zipNameTFKeyReleased(evt);
            }
        });

        backupBtn.setText("Back Up");
        backupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backupBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zipNameTF)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backupBtn)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zipNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backupBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        newMI.setText("New Backup project");
        jMenu1.add(newMI);

        openMI.setText("Open Backup project");
        jMenu1.add(openMI);

        saveMI.setText("Save Backup project");
        jMenu1.add(saveMI);

        saveAsMI.setText("Save as ...");
        jMenu1.add(saveAsMI);
        jMenu1.add(jSeparator1);

        quitMI.setText("Quit");
        jMenu1.add(quitMI);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void projectDirChooseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectDirChooseBtnActionPerformed
        File file = Utils.dirChooser(this, controller.getProjectDir(), "Choose the project dir");
        if (file != null){
            controller.dirProjectChoosed(file);
        }
    }//GEN-LAST:event_projectDirChooseBtnActionPerformed

    private void addIgnoreBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIgnoreBtnActionPerformed
        File file = Utils.dirChooser(this, controller.getProjectDir(), "Choose a sub-dir to ignore");
        if (file != null){
            controller.ignoreAdded(file);
        }
    }//GEN-LAST:event_addIgnoreBtnActionPerformed

    private void removeIgnoreBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIgnoreBtnActionPerformed
        int index = ignoreList.getSelectedIndex();
        if (index >= 0){
            controller.ignoreRemoved(index);
        }
    }//GEN-LAST:event_removeIgnoreBtnActionPerformed

    private void ignoreListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ignoreListValueChanged
        System.out.println("backup.Main.ignoreListValueChanged() " );
        controller.itemListSelected();
    }//GEN-LAST:event_ignoreListValueChanged

    private void addWhereBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWhereBtnActionPerformed
        File file = Utils.dirChooser(this, "", "Choose a dir to save the zip");
        if (file != null){
            controller.whereAdded(file);
        }
    }//GEN-LAST:event_addWhereBtnActionPerformed

    private void removeWhereBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWhereBtnActionPerformed
        int index = whereList.getSelectedIndex();
        if (index >= 0){
            controller.whereRemoved(index);
        }
    }//GEN-LAST:event_removeWhereBtnActionPerformed

    private void zipNameTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_zipNameTFKeyReleased
        controller.zipNameChanged(zipNameTF.getText());
    }//GEN-LAST:event_zipNameTFKeyReleased

    private void backupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backupBtnActionPerformed
        controller.backup();
    }//GEN-LAST:event_backupBtnActionPerformed

    private void whereListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_whereListValueChanged
        System.out.println("backup.Main.whereListValueChanged() " );
        controller.itemListSelected();
    }//GEN-LAST:event_whereListValueChanged

    private void keepEmptyCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keepEmptyCBActionPerformed
        controller.keepEmptyChanged(keepEmptyCB.isSelected());
    }//GEN-LAST:event_keepEmptyCBActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            View view = new View();
            view.setVisible(true);
            Controller controller = new Controller(view);
            view.setController(controller);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addIgnoreBtn;
    private javax.swing.JButton addWhereBtn;
    private javax.swing.JButton backupBtn;
    private javax.swing.JList<String> ignoreList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JCheckBox keepEmptyCB;
    private javax.swing.JMenuItem newMI;
    private javax.swing.JMenuItem openMI;
    private javax.swing.JButton projectDirChooseBtn;
    private javax.swing.JTextField projectDirTF;
    private javax.swing.JMenuItem quitMI;
    private javax.swing.JButton removeIgnoreBtn;
    private javax.swing.JButton removeWhereBtn;
    private javax.swing.JMenuItem saveAsMI;
    private javax.swing.JMenuItem saveMI;
    private javax.swing.JList<String> whereList;
    private javax.swing.JTextField zipNameTF;
    // End of variables declaration//GEN-END:variables


    
    private enum ActionType{New, Open, Save, SaveAs, Quit}
    
    /**
     * Actions
     */
    private class GenericAction extends AbstractAction{
        private final ActionType at;
        
        public GenericAction(ActionType at, String text, ImageIcon icon, String desc, KeyStroke keyStroke) {
            super(text, icon);
            init(desc, keyStroke);
            this.at = at;
        }
        
        private void init(String desc, KeyStroke keyStroke){
            putValue(SHORT_DESCRIPTION, desc);
            putValue(Action.ACCELERATOR_KEY, keyStroke);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            File mainFile = controller.getMainFile();
            int result;
            switch(at){
                case New : 
                    controller.newProject();
                    break;
                case Open :
                    if (mainFile != null) chooserDialog.setCurrentDirectory(mainFile);
                    result = chooserDialog.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION){
                        controller.openProject(chooserDialog.getSelectedFile());
                    }
                    break;
                case Save : 
                    controller.saveProject();
                    break;
                case SaveAs :
                    if (mainFile != null) chooserDialog.setCurrentDirectory(mainFile);
                    result = chooserDialog.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION){
                        controller.saveProjectAs(chooserDialog.getSelectedFile());
                    }
                    break;
                case Quit:
                    controller.quit();
                    break;
            }
        }
    }
    
    
}
