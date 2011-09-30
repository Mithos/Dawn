package dawn.swing;

import dawn.action.*;

import javax.swing.*;


public class ControlPanel extends JToolBar{
	
	private PrevAction prev = PrevAction.get();
	private PlayAction play = PlayAction.get();
	private NextAction next = NextAction.get();
	
	public ControlPanel(){
		super("Dawn Controls");
		add(prev);
		add(play);
		add(next);
	}
	
}
