package backup;

/**
 *
 * @author Eric Marchand
 */
public class Main {

    public static void main(String[] args){
        lookAndFeel();
        java.awt.EventQueue.invokeLater(() -> {
            View view = new View();
            view.setVisible(true);
            Controller controller = new Controller(view);
            view.setController(controller);
        });
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
