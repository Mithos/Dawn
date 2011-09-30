package dawn.action;

import dawn.*;
import dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * The play/pause action, as a singleton
 */
public class NextAction extends AbstractAction {
	
	// Singleton Code
	private NextAction(){
		super("Next");
	}
	
	private static NextAction singleton = null;
	
	public static NextAction get(){
		if(null == singleton) singleton = new NextAction();
		return singleton;
	}
	
	// Action code
	
	
	public void actionPerformed(ActionEvent e){
		Dawn.playQueue.next();
	}
	
}
