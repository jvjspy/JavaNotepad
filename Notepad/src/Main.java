import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) throws Throwable{
	
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MainWindow frame = new MainWindow();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
}
