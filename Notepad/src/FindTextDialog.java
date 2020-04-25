import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class FindTextDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField findWhat;
    private JTextField replaceTxt;
    private JCheckBox matchCaseBox;
    private JTextArea textArea;
    private MainWindow window;
    private Pattern pat;
    private Matcher mat;
    private boolean flag = true;

    public FindTextDialog(MainWindow window, JTextArea textArea) {
	super(window, "Find and replace");
	this.window = window;
	this.textArea = textArea;
	setModal(true);
	setBounds(window.getX() + window.getWidth() / 3, window.getY() + window.getHeight() / 3, 320, 200);
	getContentPane().setLayout(new FormLayout(
		new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("65px"),
			FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("109px:grow"),
			FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("83px"), FormSpecs.RELATED_GAP_COLSPEC,
			FormSpecs.DEFAULT_COLSPEC, },
		new RowSpec[] { RowSpec.decode("23px"), FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("23px"),
			FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("23px"), FormSpecs.RELATED_GAP_ROWSPEC,
			FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

	initComponents();
    }

    private void initComponents() {
	JLabel lblNewLabel = new JLabel("Find what:");
	getContentPane().add(lblNewLabel, "2, 1, 2, 1, left, center");

	findWhat = new JTextField();
	getContentPane().add(findWhat, "4, 1, fill, center");
	findWhat.setColumns(10);
	findWhat.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent e) {
		flag = true;
	    }
	});

	JButton next = new JButton("Find next");
	next.setMargin(new Insets(2, 10, 2, 10));
	getContentPane().add(next, "6, 1, 2, 1, default, center");
	next.addActionListener((e) -> findNext());

	JLabel lblNewLabel_1 = new JLabel("Replace with:");
	getContentPane().add(lblNewLabel_1, "2, 3, 2, 1, right, center");

	replaceTxt = new JTextField();
	getContentPane().add(replaceTxt, "4, 3, fill, center");
	replaceTxt.setColumns(10);

	JButton replace = new JButton("Replace");
	replace.setMargin(new Insets(2, 10, 2, 10));
	getContentPane().add(replace, "6, 3, 2, 1, default, center");
	replace.addActionListener((e) -> replace());

	matchCaseBox = new JCheckBox("Match case");
	getContentPane().add(matchCaseBox, "4, 5, 1, 2, center, center");
	matchCaseBox.addActionListener((e) -> flag = true);

	JButton replaceAll = new JButton("Replace all");
	replaceAll.setMargin(new Insets(2, 10, 2, 2));
	replaceAll.setAlignmentY(0.0f);
	getContentPane().add(replaceAll, "6, 5, 2, 1, default, center");
	replaceAll.addActionListener((e) -> replaceAll());

	JButton btnNewButton = new JButton("Cancel");
	btnNewButton.setMargin(new Insets(2, 10, 2, 10));
	getContentPane().add(btnNewButton, "6, 7, 2, 1, default, center");
	btnNewButton.addActionListener((e) -> exit());
    }

    public void exit() {
	setVisible(false);
    }

    public void replace() {
	String txt = replaceTxt.getText();
	textArea.replaceSelection(txt);
    }

    public void replaceAll() {
	buildRegex(findWhat.getText(), matchCaseBox.isSelected());
	String txt = replaceTxt.getText();
	int count = 0;
	while (mat.find()) {
	    textArea.replaceRange(txt, mat.start(), mat.end());
	    count++;
	}
	JOptionPane.showMessageDialog(window, "Replace all " + count + " occurences.", "Information",
		JOptionPane.INFORMATION_MESSAGE);
    }

    private void buildRegex(String regex, boolean matchCase) {
	if (matchCase)
	    pat = Pattern.compile(regex, Pattern.MULTILINE);
	else
	    pat = Pattern.compile(regex, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	mat = pat.matcher(textArea.getText());
    }

    public void findNext() {
	if (flag) {
	    String regex = findWhat.getText();
	    boolean matchCase = matchCaseBox.isSelected();
	    buildRegex(regex, matchCase);
	    flag = false;
	}
	if (mat.find()) {
	    int start = mat.start();
	    int end = mat.end();
	    textArea.setSelectionStart(start);
	    textArea.setSelectionEnd(end);
	} else {
	    flag = true;
	    JOptionPane.showMessageDialog(window, "Can't not find " + findWhat.getText(), "Information",
		    JOptionPane.WARNING_MESSAGE);
	}
    }
}
