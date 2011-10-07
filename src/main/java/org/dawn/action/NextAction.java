package org.dawn.action;

import org.dawn.*;
import org.dawn.lowLevel.*;
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
		// Keyboard shortcut
		putValue(MNEMONIC_KEY, KeyEvent.VK_RIGHT);
		putValue(SHORT_DESCRIPTION, "Alt + Right");
		
		// image
		putValue(LARGE_ICON_KEY, ImageCache.getImage(ImageCache.NEXT, ImageCache.LARGE));
		putValue(SMALL_ICON, ImageCache.getImage(ImageCache.NEXT, ImageCache.SMALL));
	}
	
	private static NextAction singleton = null;
	
	public static NextAction get(){
		if(null == singleton) singleton = new NextAction();
		return singleton;
	}
	
	// Action code
	
	
	public void actionPerformed(ActionEvent e){
		PlayQueue.get().next();
	}
	
}
