package dawn.action;

import dawn.*;
import dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

public class PrevAction extends AbstractAction{
	
	
	// Singleton Code
	private PrevAction(){
		super("Previous");
	}
	
	private static PrevAction singleton = null;
	
	public static PrevAction get(){
		if(null == singleton) singleton = new PrevAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		Dawn.playQueue.previous();
	}
	
}
