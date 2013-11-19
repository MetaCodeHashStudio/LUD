
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Runnetty
 */
public final class LUD extends javax.swing.JFrame {

    /**
     * Creates new form LUD
     */
    public LUD() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setLocationByPlatform(true);
        setName("MetaCode Updater"); // NOI18N
        setResizable(false);

        jProgressBar1.setName(""); // NOI18N
        jProgressBar1.setStringPainted(true);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("MetaCode Updater v1");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Candara", 1, 12)); // NOI18N
        jLabel3.setText("Updating Launcher...");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/small_logo2.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    private static boolean c_Folder;
    private static int Status;
    private String commandToRun;
    private boolean waitState = true;
    public ActionListener actionListener;
    public static String sep = System.getProperty("file.separator");
    private static String path = ".."+sep+""; //Download path
    private static BufferedInputStream in = null;
    private static String jar = "https://dl.dropboxusercontent.com/u/57469303/Yahtzoid/Launcher.jar"; //File URL

    public void Start() {
        System.out.println("Starting");
        try {
            update();
        } catch (MalformedURLException ex) {
            Logger.getLogger(LUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(LUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        waitState = false;
        synchronized (this) {
            notifyAll();
        }
    }

    public void update() throws MalformedURLException, IOException, InterruptedException {
        deleteOldLauncher(path + "Launcher.jar");
        getFilesFromServer(jar, "Launcher.jar");
    }

    public static void gen_Folders(String newLoc) {
        c_Folder = (new File(path + newLoc)).mkdirs();
    }

    public static void deleteOldLauncher(String oldLoc) {
        boolean success = (new File(oldLoc)).delete();
    }

    public void getFilesFromServer(String fileUrl, String saveLocation) throws IOException, InterruptedException {
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(path + saveLocation);

            URL url = new URL(fileUrl);
            URLConnection conection = url.openConnection();
            conection.connect();

            byte data[] = new byte[1024];
            int count;
            int total = 0;
            int lenghtOfFile = conection.getContentLength();

            while ((count = in.read(data, 0, 1024)) != -1) {
                total += count;
                onProgressUpdate("" + (int) ((total * 100) / lenghtOfFile));
                fout.write(data, 0, count);
                Thread.sleep(10);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }

    protected void onProgressUpdate(String... progress) {
        System.out.println(Integer.parseInt(progress[0]));
        jProgressBar1.setValue(Integer.parseInt(progress[0]));
    }

    public boolean getWaitState() {
        return waitState;
    }
}
