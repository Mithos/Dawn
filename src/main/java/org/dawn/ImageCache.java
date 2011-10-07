package org.dawn;

import javax.swing.*;
import java.net.*;
import java.util.*;

public abstract class ImageCache{
	
	// private cache
	private static final HashMap<String, ImageIcon> CACHE = new HashMap<String, ImageIcon>(10, 1); //create hash map sufficient to hold all entries
	
	// public api
	public static final String PLAY = "play";
	public static final String PAUSE = "pause";
	public static final String NEXT = "next";
	public static final String PREVIOUS = "previous";
	
	public static final int SMALL = 16;
	public static final int LARGE = 22;
	
	/**
	 * Initialize the cache, load the images.
	 * 
	 * Currently, the images loaded are the freedesktop tango images included in the gstreamer-java jar archive.
	 */
	public static void init(){
		URL imageURL = null;
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/22x22/actions/media-playback-start.png");
		CACHE.put(PLAY+LARGE, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/16x16/actions/media-playback-start.png");
		CACHE.put(PLAY+SMALL, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/22x22/actions/media-playback-pause.png");
		CACHE.put(PAUSE+LARGE, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/16x16/actions/media-playback-pause.png");
		CACHE.put(PAUSE+SMALL, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/22x22/actions/media-seek-forward.png");
		CACHE.put(NEXT+LARGE, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/16x16/actions/media-seek-forward.png");
		CACHE.put(NEXT+SMALL, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/22x22/actions/media-seek-backward.png");
		CACHE.put(PREVIOUS+LARGE, new ImageIcon(imageURL));
		
		imageURL = ImageCache.class.getResource("/org/freedesktop/tango/16x16/actions/media-seek-backward.png");
		CACHE.put(PREVIOUS+SMALL, new ImageIcon(imageURL));
		
	}
	
	public static ImageIcon getImage(String name, int size){
		return CACHE.get(name+size);
	}
	
	
}
