package dawn.action;

import javax.swing.*;
import java.awt.event.*;

import dawn.lowLevel.*;

public class ShuffleAction extends AbstractAction {
	
	private String turnOn = "Turn Shuffle On";
	private String turnOff = "Turn Shuffle Off";
	
	// Singleton Code
	private ShuffleAction(){
		super("Turn Shuffle On");
	}
	
	private static ShuffleAction singleton = null;
	
	public static ShuffleAction get(){
		if(null == singleton) singleton = new ShuffleAction();
		return singleton;
	}
	
	//Action code
	public void actionPerformed(ActionEvent e){
		PlayQueue q = PlayQueue.get();
		q.setShuffle(!q.getShuffle());
		if(true == q.getShuffle()){ //shuffle is now on
			putValue(NAME, turnOff);
			firePropertyChange(NAME, turnOn, turnOff);
		} else {
			putValue(NAME, turnOn);
			firePropertyChange(NAME, turnOff, turnOn);
		}
	}
	
}
