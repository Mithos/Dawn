package org.dawn.action;

import org.dawn.*;
import org.dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

import java.net.*;


/**
 * The play/pause action, as a singleton
 */
public class PlayAction extends AbstractAction {
	
	private ImageIcon play;
	private ImageIcon pause;
	
	// Singleton Code
	private PlayAction(){
		super("Play");
		
		// Keyboard shortcut
		putValue(MNEMONIC_KEY, KeyEvent.VK_DOWN);
		putValue(SHORT_DESCRIPTION, "Alt + Down");
		
		// initial image
		putValue(LARGE_ICON_KEY, ImageCache.getImage(ImageCache.PLAY, ImageCache.LARGE));
		putValue(SMALL_ICON, ImageCache.getImage(ImageCache.PLAY, ImageCache.SMALL));
		
		// Text and image handling code
		final PlayQueue q = PlayQueue.get();
		q.getBus().connect(new Bus.STATE_CHANGED(){
			public void stateChanged(GstObject source, State old, State current, State pending){
				if(State.PLAYING == q.getState()){
					PlayAction.get().putValue(PlayAction.NAME, "Pause");
					PlayAction.get().putValue(LARGE_ICON_KEY, ImageCache.getImage(ImageCache.PAUSE, ImageCache.LARGE));
					PlayAction.get().putValue(SMALL_ICON, ImageCache.getImage(ImageCache.PAUSE, ImageCache.SMALL));
				} else {
					PlayAction.get().putValue(PlayAction.NAME, "Play");
					PlayAction.get().putValue(LARGE_ICON_KEY, ImageCache.getImage(ImageCache.PLAY, ImageCache.LARGE));
					PlayAction.get().putValue(SMALL_ICON, ImageCache.getImage(ImageCache.PLAY, ImageCache.SMALL));
				}
			}
		});
	}
	
	private static PlayAction singleton = null;
	
	public static PlayAction get(){
		if(null == singleton) singleton = new PlayAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		if (PlayQueue.get().getState() == State.PLAYING){
			PlayQueue.get().pause();
		} else {
			PlayQueue.get().play();
		}
	}
	
}
