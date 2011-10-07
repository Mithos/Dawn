package org.dawn.action;

import org.dawn.*;
import org.dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

public class PrevAction extends AbstractAction{
	
	
	// Singleton Code
	private PrevAction(){
		super("Previous");
		
		// Keyboard shortcut
		putValue(MNEMONIC_KEY, KeyEvent.VK_LEFT);
		putValue(SHORT_DESCRIPTION, "Alt + Left");
		
		// image
		putValue(LARGE_ICON_KEY, ImageCache.getImage(ImageCache.PREVIOUS, ImageCache.LARGE));
		putValue(SMALL_ICON, ImageCache.getImage(ImageCache.PREVIOUS, ImageCache.SMALL));
	}
	
	private static PrevAction singleton = null;
	
	public static PrevAction get(){
		if(null == singleton) singleton = new PrevAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		PlayQueue.get().previous();
	}
	
}
