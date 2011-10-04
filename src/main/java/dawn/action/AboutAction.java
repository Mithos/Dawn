package dawn.action;

import javax.swing.*;
import java.awt.event.*;

import dawn.swing.*;

public class AboutAction extends AbstractAction{
	
	private static AboutAction singleton;
	public static AboutAction get(){
		if(null == singleton) singleton = new AboutAction();
		return singleton;
	}
	
	private AboutAction(){
		super("About");
	}
	
	// Action code
	
	private String about = "<html><b>Dawn v0.0.0</b><br /></html>";
	
	@Override
	public void actionPerformed(ActionEvent e){
		JOptionPane.showMessageDialog(DawnWindow.get(), about);
	}
	
}
