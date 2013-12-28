

/**
 * @author Runnetty aka Mats Harwiss
 * @ MetaCode Studio www.metacodestudio.com
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;

public final class LUD extends javax.swing.JFrame {

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
        setTitle(Start.version);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setForeground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setName("MetaCode Updater"); // NOI18N
        setResizable(false);

        jProgressBar1.setName(""); // NOI18N
        jProgressBar1.setStringPainted(true);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("MetaCode Updater");
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
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
    private static int Status;
    private boolean waitState = true;
    public static String seperator = System.getProperty("file.separator");
    protected static URL url;
    protected static URLConnection connection;
    private static BufferedInputStream in = null;
    protected static String jar = "https://dl.dropboxusercontent.com/u/57469303/Yahtzoid/Launcher.jar"; //File URL

    public void Start() throws URISyntaxException, IOException, InterruptedException {
        if (connectToMain(jar)) {
            System.out.println("| - DELETING OLD FILES:\n| - " + mainFolder() + "Launcher.jar");
            deleteOldLauncher(mainFolder() + "Launcher.jar");

            System.out.println("| - DOWNLOADING UPDATE:\n| - " + jar);
            getFilesFromServer(jar, "Launcher.jar");

            System.out.println("| - DOWNLOAD: COMPLEETE");
        }
        waitState = false;
        synchronized (this) {
            notifyAll();
        }
    }

    protected boolean connectToMain(String fileUrl) {
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLException ex) {
            System.out.println("Invalid URL.");
            JOptionPane.showMessageDialog(Start.window, "Invalid URL.");
            return false;
        }
        try {
            connection = url.openConnection();
        } catch (IOException ex) {
            System.out.println("There was a error while trying to open connection to URL");
            JOptionPane.showMessageDialog(Start.window, "There was a error while trying to open connection to URL");
            return false;
        }
        System.out.println("| - Opening Connection...");
        try {
            connection.connect();
            System.out.println("| - Connected");
        } catch (IOException ex) {
            System.out.println("Connection to the URL was rejected. Make sure you are connected to the internet and try again.");
            JOptionPane.showMessageDialog(Start.window, "Connection to the URL was rejected. Make sure you are connected to the internet and try again.");
            return false;
        }
        return true;
    }
    
    public static void createFolder(String newLocation) throws URISyntaxException {
        boolean createFolder = (new File(mainFolder() + newLocation)).mkdirs();
    }

    public static void deleteOldLauncher(String oldLoc) {
        boolean success = (new File(oldLoc)).delete();
    }

    public void getFilesFromServer(String fileUrl, String saveLocation) throws IOException, InterruptedException, URISyntaxException {
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(mainFolder() + saveLocation);

            byte data[] = new byte[1024];
            int count;
            int total = 0;
            int lenghtOfFile = connection.getContentLength();

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
        jProgressBar1.setValue(Integer.parseInt(progress[0]));
    }

    public boolean getWaitState() {
        return waitState;
    }

    public static String mainFolder() throws URISyntaxException {
        String filePath = new File(Start.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI().getPath()).toString();
        filePath = filePath.substring(0, filePath.length() - 13);
        return filePath;
    }
}
