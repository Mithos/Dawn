package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import dawn.*;

/** The great big play button in the control panel */
public class PlayButton extends JButton implements ActionListener, Bus.STATE_CHANGED{
	
	public PlayButton(){
		addActionListener(this);
		Dawn.playbin.getBus().connect(this);
		setText("Play");
	}

	public void actionPerformed(ActionEvent e){
		if (Dawn.playbin.getState() == State.PLAYING){
			Dawn.playbin.setState(State.PAUSED);
		} else {
			Dawn.playbin.setState(State.PLAYING);
		}
	}
	
	public void stateChanged(GstObject source, State old, State current, State pending) {
		if(source == Dawn.playbin){
			switch(current){
			case PLAYING: setText("Pause"); break;
			default: setText("Play"); break;
			}
		}
	}

}
