package org.dawn.action;

import org.dawn.swing.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * The action for quit events. Shuts down gstreamer cleanly and exits program.
 */
public class RescanAction extends AbstractAction{
	
	// Singleton Code
	private RescanAction(){
		super("Select Music Folder");
	}
	
	private static RescanAction singleton = null;
	
	public static RescanAction get(){
		if(null == singleton) singleton = new RescanAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		TrackList.get().scanMediaLibrary(java.nio.file.Paths.get(System.getProperty("user.home"), "Music")); // Todo: file chooser
	}
	
}
