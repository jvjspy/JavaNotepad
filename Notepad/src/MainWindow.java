
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainWindow() {
	setBounds(100, 100, 800, 500);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
	MainView mv = new MainView(this);
	MainViewController mvc = new MainViewController(mv);
	mv.setController(mvc);
	setContentPane(mv);
    }
}
