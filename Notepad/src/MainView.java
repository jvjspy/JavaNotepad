import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainView extends JPanel implements ActionListener, MenuListener {
    private static final long serialVersionUID = 1L;
    private MainWindow window;
    private MainViewController controller;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, viewMenu, formatMenu, helpMenu;
    private JMenuItem openFile, newFile, saveFile, changeFont, setTabSize, exit, wrapText, undo, removeIndent, cutText,
	    copyText, pasteText, deleteSelection, deleteLine, findText, indent, changeTheme, colorChooser, about;
    private JTextArea note;
    private final String aboutText = "Notepad-v1.0\nDeveloped by Chung\n© 2020";

    public JTextArea getNote() {
	return note;
    }

    public JMenu getEditJMenu() {
	return editMenu;
    }

    public void setController(MainViewController mvc) {
	this.controller = mvc;
    }

    public MainWindow getWindow() {
	return window;
    }

    public MainView(MainWindow window) {
	this.window = window;
	initView();
	keyBinds();
    }

    class sAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private Runnable action;

	public sAction(Runnable action) {
	    this.action = action;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    action.run();
	}

    }

    private void keyBinds() {
	InputMap input = note.getInputMap();
	input.put(KeyStroke.getKeyStroke("ctrl S"), new sAction(() -> controller.saveFile()));
	input.put(KeyStroke.getKeyStroke("ctrl O"), new sAction(() -> controller.openFile()));
	input.put(KeyStroke.getKeyStroke("ctrl N"), new sAction(() -> controller.newFile()));
	input.put(KeyStroke.getKeyStroke("ctrl X"), new sAction(() -> controller.cutText()));
	input.put(KeyStroke.getKeyStroke("ctrl C"), new sAction(() -> controller.copyText()));
	input.put(KeyStroke.getKeyStroke("ctrl V"), new sAction(() -> controller.pasteText()));
	input.put(KeyStroke.getKeyStroke("ctrl Z"), new sAction(() -> controller.undo()));
	input.put(KeyStroke.getKeyStroke("ctrl H"), new sAction(() -> controller.findText()));
	input.put(KeyStroke.getKeyStroke("ctrl D"), new sAction(() -> controller.deleteLine()));
	input.put(KeyStroke.getKeyStroke("ctrl I"), new sAction(() -> controller.indent()));
	input.put(KeyStroke.getKeyStroke("ctrl R"), new sAction(() -> controller.removeIndent()));
    }

    private void initView() {
	menuBar = new JMenuBar();
	window.setJMenuBar(menuBar);

	fileMenu = new JMenu("File");
	menuBar.add(fileMenu);

	openFile = new JMenuItem("Open(Ctrl+O)");
	fileMenu.add(openFile);

	newFile = new JMenuItem("New(Ctrl+N)");
	fileMenu.add(newFile);

	saveFile = new JMenuItem("Save(Ctrl+S)");
	fileMenu.add(saveFile);

	exit = new JMenuItem("Exit");
	fileMenu.add(exit);

	editMenu = new JMenu("Edit");
	menuBar.add(editMenu);

	undo = new JMenuItem("Undo(Ctrl+Z)");
	editMenu.add(undo);

	cutText = new JMenuItem("Cut(Ctrl+X)");
	editMenu.add(cutText);

	copyText = new JMenuItem("Copy(Ctrl+C)");
	editMenu.add(copyText);

	pasteText = new JMenuItem("Paste(Ctrl+V)");
	editMenu.add(pasteText);

	deleteSelection = new JMenuItem("Delete Selection");
	editMenu.add(deleteSelection);

	deleteLine = new JMenuItem("Delete line(Ctrl+D)");
	editMenu.add(deleteLine);

	findText = new JMenuItem("Find and Replace(Ctrl+H)");
	editMenu.add(findText);

	indent = new JMenuItem("Auto indent(Ctrl+I)");
	editMenu.add(indent);

	removeIndent = new JMenuItem("Remove indent(Ctrl+R)");
	editMenu.add(removeIndent);

	viewMenu = new JMenu("View");
	menuBar.add(viewMenu);

	wrapText = new JMenuItem("Word wrap");
	viewMenu.add(wrapText);

	changeTheme = new JMenuItem("Change theme");
	viewMenu.add(changeTheme);

	formatMenu = new JMenu("Format");
	menuBar.add(formatMenu);

	changeFont = new JMenuItem("Change font");
	formatMenu.add(changeFont);

	colorChooser = new JMenuItem("Change text color");
	formatMenu.add(colorChooser);

	setTabSize = new JMenuItem("Set tab size");
	formatMenu.add(setTabSize);

	helpMenu = new JMenu("Help");
	menuBar.add(helpMenu);
	setLayout(new BorderLayout(0, 0));

	about = new JMenuItem("About");
	helpMenu.add(about);

	note = new JTextArea();
	note.setLineWrap(true);
	JScrollPane scroll = new JScrollPane(note);
	note.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, null, null, null));
	add(scroll, BorderLayout.CENTER);
	note.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

	for (int i = 0; i < menuBar.getMenuCount(); i++)
	    for (int j = 0; j < menuBar.getMenu(i).getItemCount(); j++)
		menuBar.getMenu(i).getItem(j).addActionListener(this);
	window.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		controller.exit();
	    }
	});
	editMenu.addMenuListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Object src = e.getSource();
	if (src == exit)
	    controller.exit();
	else if (src == saveFile)
	    controller.saveFile();
	else if (src == openFile)
	    controller.openFile();
	else if (src == newFile)
	    controller.newFile();
	else if (src == undo)
	    controller.undo();
	else if (src == cutText)
	    controller.cutText();
	else if (src == copyText)
	    controller.copyText();
	else if (src == pasteText)
	    controller.pasteText();
	else if (src == deleteLine)
	    controller.deleteLine();
	else if (src == deleteSelection)
	    controller.deleteSelection();
	else if (src == findText)
	    controller.findText();
	else if (src == indent)
	    controller.indent();
	else if (src == removeIndent)
	    controller.removeIndent();
	else if (src == wrapText)
	    controller.wordWrap();
	else if (src == changeTheme)
	    controller.changeTheme();
	else if (src == changeFont)
	    controller.changeFont();
	else if (src == setTabSize)
	    controller.setTabSize();
	else if (src == colorChooser)
	    controller.setColor();
	else if (src == about)
	    showAbout();
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuSelected(MenuEvent e) {
	controller.onEditMenuShowing();
    }

    public void showAbout() {
	JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }

}
