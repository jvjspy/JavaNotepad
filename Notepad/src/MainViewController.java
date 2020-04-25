

public class MainViewController{
    private MainView view;
    private FileMenuController fmc;
    private EditMenuController emc;
    private ViewMenuController vmc;
    private FormatMenuController fmmc;
    public MainViewController(MainView v){
	view=v;
	v.getWindow().setTitle("Untitled");
	fmc=new FileMenuController(view);
	emc=new EditMenuController(view);
	vmc=new ViewMenuController(view);
	fmmc=new FormatMenuController(view);
    }
    public void exit(){
	fmc.exit();
    }
    public void saveFile(){
	fmc.save();
    }
    public void openFile(){
	fmc.open();
    }
    public void newFile(){
	fmc.newFile();
    }
    public void onEditMenuShowing(){
	emc.onShow();
    }
    public void undo(){
	emc.undo();
    }
    public void cutText(){
	emc.cut();
    }
    public void copyText(){
	emc.copy();
    }
    public void pasteText(){
	emc.paste();
    }
    public void deleteSelection(){
	emc.deleteSelection();
    }
    public void deleteLine(){
	emc.deleteLine();
    }
    public void findText(){
	emc.find();
    }
    public void indent(){
	emc.indent();
    }
    public void removeIndent(){
	emc.removeIndent();
    }
    public void wordWrap(){
	vmc.wordWrap();
    }
    public void changeTheme(){
	vmc.changeTheme();
    }
    public void changeFont(){
	fmmc.changeFont();
    }
    public void setTabSize(){
	fmmc.setTabSize();
    }
    public void setColor(){
	fmmc.setColor();
    }
}
