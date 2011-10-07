package org.dawn.swing;

import org.dawn.action.*;

import javax.swing.*;


public class ControlPanel extends JToolBar{
	
	private static ControlPanel singleton = null;
	
	public static ControlPanel get(){
		if(null == singleton) singleton = new ControlPanel();
		return singleton;
	}
	
	private PrevAction prev = PrevAction.get();
	private PlayAction play = PlayAction.get();
	private NextAction next = NextAction.get();
	
	private ShuffleAction shuffle = ShuffleAction.get();
	
	private ControlPanel(){
		super("Dawn Controls");
		add(prev);
		add(play);
		add(next);
		
		addSeparator();
		
		add(shuffle);
		
		addSeparator();
		add(VolumeSpinner.get());
	}
	
}
