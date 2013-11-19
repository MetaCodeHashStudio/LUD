
import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class Start {

    public static LUD frame;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    frame = new LUD();
                    frame.setLocation(600, 400);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (frame) {
            try {
                while (frame.getWaitState()) {
                    frame.wait(50);
                    frame.Start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           try {
            Process proc = Runtime.getRuntime().exec("java -jar .."+ LUD.sep+"Launcher.jar");
        } catch (IOException ex) {}
           System.exit(0);
        }
    }
}
