package dawn.action;

import dawn.*;
import dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * The play/pause action, as a singleton
 */
public class PlayAction extends AbstractAction {
	
	// Singleton Code
	private PlayAction(){
		super("Play");
	}
	
	private static PlayAction singleton = null;
	
	public static PlayAction get(){
		if(null == singleton) singleton = new PlayAction();
		return singleton;
	}
	
	// Action code
	
	private String pause = "Pause";
	private String play = "Play";
	
	public void actionPerformed(ActionEvent e){
		if (PlayQueue.get().getState() == State.PLAYING){
			PlayQueue.get().pause();
		} else {
			PlayQueue.get().play();
		}
	}
	
}
