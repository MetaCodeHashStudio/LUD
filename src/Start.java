

/**
 * @author Runnetty aka Mats Harwiss
 * @ MetaCode Studio www.metacodestudio.com
 */
    // TODO:
    //      - Only Download from metacodestudio.com
    //      - Make it find all files at url locations folder + subfolders.
    //      - Then download them all.
    //      - Show wich files is downloaded on screen.
////////////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;

public class Start {
    public static String version = "Version - 3.0.0 Stable";
    public static LUD window;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException, URISyntaxException, IOException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    window = new LUD();
                    window.setLocation(600, 400);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (window) {
            try {
                while (window.getWaitState()) {
                    window.wait(50);
                    window.Start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + window.mainFolder() + "Launcher.jar");
            } catch (IOException ex) {
            }
            System.exit(0);
        }
    }
}
