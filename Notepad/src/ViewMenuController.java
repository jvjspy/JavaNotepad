import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class ViewMenuController {
    private MainView view;
    private boolean wordWrap = true;
    private String[] themeNameList;
    private LookAndFeelInfo[] themeList;

    public ViewMenuController(MainView view) {
	this.view = view;
	themeList = UIManager.getInstalledLookAndFeels();
	themeNameList = Stream.of(themeList).map(x -> x.getName()).toArray(String[]::new);
    }

    public void wordWrap() {
	view.getNote().setWrapStyleWord(wordWrap = !wordWrap);
    }

    public void changeTheme() {
	JComboBox<String> menu = new JComboBox<String>(themeNameList);
	int ans = JOptionPane.showConfirmDialog(view, menu, "Choose theme", JOptionPane.OK_CANCEL_OPTION);
	if (ans == JOptionPane.OK_OPTION) {
	    try {
		UIManager.setLookAndFeel(themeList[menu.getSelectedIndex()].getClassName());
		SwingUtilities.updateComponentTreeUI(view.getWindow());
	    } catch (Exception e) {
		JOptionPane.showMessageDialog(view, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}
