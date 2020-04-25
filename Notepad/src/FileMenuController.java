import java.awt.Cursor;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class FileMenuController {
    private MainView view;
    private JFileChooser fc;
    private File file;
    private boolean saved;
    public FileMenuController(MainView view){
	this.view=view;
	saved=false;
	fc=new JFileChooser("D:");
	file=null;
    }
    private void createFile(){
	view.getWindow().setTitle("Untitled");
	view.getNote().setText("");
    }
    public void newFile(){
	if(view.getNote().getText().length()>0&&!saved){
	    int ans=JOptionPane.showConfirmDialog(view,"Do you want to save changes to "+view.getWindow().getTitle());
	    if(ans==JOptionPane.OK_OPTION){
		save();
		if(saved)	createFile();
	    }else if (ans==JOptionPane.NO_OPTION) {
		createFile();
	    }
	}
    }

    public void save() {
	if (file == null) {
	    int ans = fc.showSaveDialog(view);
	    if (ans == JFileChooser.APPROVE_OPTION)
		file = fc.getSelectedFile();
	}
	if (file != null)
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
		String text = view.getNote().getText();
		bos.write(text.getBytes(Charset.forName("utf-8")));
		saved = true;
	    } catch (Exception e) {
		JOptionPane.showMessageDialog(view, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
	    }
    }

    public void open() {
	int ans = fc.showOpenDialog(view);
	if (ans == JFileChooser.APPROVE_OPTION) {
	    file = fc.getSelectedFile();
	    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		@Override
		protected Void doInBackground(){
		    view.getNote().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		    try {
			view.getNote().read(new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")),null);
			view.getWindow().setTitle(file.getName());
		    } catch (IOException e) {
			JOptionPane.showMessageDialog(view,e.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
		    }
		    return null;
		}

		@Override
		protected void done() {
		    saved=false;
		    view.getNote().setCursor(Cursor.getDefaultCursor());
		}	

	    };
	    worker.execute();
	}
    }
    public void exit(){
	if(view.getNote().getText().length()>0&&!saved){
	    int ans=JOptionPane.showConfirmDialog(view,"Do you want to save changes to "+view.getWindow().getTitle());
	    if(ans==JOptionPane.OK_OPTION){
	    save();
	    if(saved)	System.exit(0);
	    }else if (ans==JOptionPane.NO_OPTION) {
		System.exit(0);
	    }
	}
	else System.exit(0);
    }

}
