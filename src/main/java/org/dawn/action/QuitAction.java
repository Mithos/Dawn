package org.dawn.action;

import org.dawn.*;
import org.dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The action for quit events. Shuts down gstreamer cleanly and exits program.
 */
public class QuitAction extends AbstractAction{
	
	// Singleton Code
	private QuitAction(){
		super("Quit");
	}
	
	private static QuitAction singleton = null;
	
	public static QuitAction get(){
		if(null == singleton) singleton = new QuitAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		// Stop gstreamer
		PlayQueue.get().stop();
		Gst.quit();
		
		// dispose of all frames
		for(Frame f : Frame.getFrames()){
			f.dispose();
		}
	}
	
}

