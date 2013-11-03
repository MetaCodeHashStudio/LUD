import java.awt.EventQueue;

public class Start {

    public static LUD frame;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
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
    }
}
