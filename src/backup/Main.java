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

/**
 *
 * @author Eric Marchand
 */
public class Main {

    public static void main(String[] args){
        if (args.length == 0){ // UI mode
            lookAndFeel();
            java.awt.EventQueue.invokeLater(() -> {
                View view = new View();
                view.setVisible(true);
                Controller controller = new Controller(view);
                view.setController(controller);
            });
        } else if (args.length == 1){ // console mode
            System.out.println(Controller.APPNAME + " - " + Controller.VERSION);
            System.out.println("pass a .csb filename as argument");
            System.out.println("Starting backup ...");
            try {
                Controller.doConsoleJob(args[0]);
                System.out.println("backup successfull !");
            } catch (Exception ex) {
                System.err.println("backup.Main.main() Error : " + ex.getMessage());
            }
        }
    }
    
    private static void lookAndFeel(){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println("backup.Main.lookAndFeel() Unable to set the Nimbus L&F");
        }
    }
}
