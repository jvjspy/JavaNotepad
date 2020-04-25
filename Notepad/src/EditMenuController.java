
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

import javax.swing.JMenu;
import javax.swing.undo.UndoManager;

public class EditMenuController {
    private MainView view;
    private Clipboard clipboard;
    private FindTextDialog dialog;
    private UndoManager undoman;

    public EditMenuController(MainView view) {
	this.view = view;
	clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	dialog = new FindTextDialog(view.getWindow(), view.getNote());
	undoman = new UndoManager();
	view.getNote().getDocument().addUndoableEditListener((e) -> {
	    undoman.addEdit(e.getEdit());
	});
    }

    private String getClipboardText() {
	String text = null;
	try {
	    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
		text = clipboard.getData(DataFlavor.stringFlavor).toString();
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	}
	return text;
    }

    private void addTextToClipboard(String text) {
	clipboard.setContents(new StringSelection(text), null);
    }

    public void cut() {
	String text = view.getNote().getSelectedText();
	view.getNote().replaceSelection("");
	addTextToClipboard(text);
    }

    public void copy() {
	addTextToClipboard(view.getNote().getSelectedText());
    }

    public void paste() {
	view.getNote().replaceSelection(getClipboardText());
    }

    public void deleteSelection() {
	view.getNote().replaceSelection("");
    }

    public void deleteLine() {
	int start = Math.max(0, view.getNote().getCaretPosition() - 1);
	int end = start + 1;
	while (start > 0 && view.getNote().getText().charAt(start) != '\n')
	    start--;
	while (end < view.getNote().getText().length() && view.getNote().getText().charAt(end) != '\n')
	    end++;
	if (end <= view.getNote().getText().length())
	    view.getNote().replaceRange("", start, end);
    }

    public void find() {
	dialog.setVisible(true);
    }

    public void indent() {
	view.getNote().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	view.getNote().insert("\t", 0);
	for (int i = 0; i < view.getNote().getText().length(); i++) {
	    if (view.getNote().getText().charAt(i) == '\n')
		view.getNote().insert("\t", i + 1);
	}
	view.getNote().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void undo() {
	if (undoman.canUndo())
	    undoman.undo();
    }

    public void removeIndent() {
	view.getNote().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	if (view.getNote().getText().length() > 0 && view.getNote().getText().charAt(0) == '\t')
	    view.getNote().replaceRange("", 0, 1);
	for (int i = 0; i < view.getNote().getText().length(); i++) {
	    if (view.getNote().getText().charAt(i) == '\t')
		view.getNote().replaceRange("", i, i + 1);
	}
	view.getNote().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void onShow() {
	JMenu menu = view.getEditJMenu();
	menu.getItem(0).setEnabled(undoman.canUndo());
	menu.getItem(1).setEnabled(view.getNote().getSelectedText() != null);
	menu.getItem(2).setEnabled(view.getNote().getSelectedText() != null);
	menu.getItem(3).setEnabled(getClipboardText() != null);
	menu.getItem(4).setEnabled(view.getNote().getSelectedText() != null);
	menu.getItem(5).setEnabled(view.getNote().getText().length() > 0);
    }

}
