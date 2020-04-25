
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import say.swing.JFontChooser;

public class FormatMenuController {
    private MainView view;
    private JFontChooser font;
    public FormatMenuController(MainView view){
	this.view=view;
	font=new JFontChooser();
    }
    public void changeFont(){
	int ans=font.showDialog(view);
	if(ans==JFontChooser.OK_OPTION){
	    view.getNote().setFont(font.getSelectedFont());
	}
    }
    public void setTabSize(){
	String ans=JOptionPane.showInputDialog(view,"Tab size:","4");
	if(ans!=null && ans.matches("^\\d$")){
	    view.getNote().setTabSize(Integer.parseInt(ans));
	}else if(ans!=null){
	    JOptionPane.showMessageDialog(view,"Invalid tab size","Error",JOptionPane.ERROR_MESSAGE);
	}
    }
    public void setColor(){
	Color c=JColorChooser.showDialog(view,"Choose color",Color.BLACK);
	if(c!=null){
	    view.getNote().setForeground(c);
	}
    }

}
