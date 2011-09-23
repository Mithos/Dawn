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
		Dawn.playQueue.getBus().connect(this);
		setText("Play");
	}

	public void actionPerformed(ActionEvent e){
		if (Dawn.playQueue.getState() == State.PLAYING){
			Dawn.playQueue.pause();
		} else {
			Dawn.playQueue.play();
		}
	}
	
	// Todo, gui controller
	public void stateChanged(GstObject source, State old, State current, State pending) {
		switch(current){
		case PLAYING: setText("Pause"); break;
		default: setText("Play"); break;
		}
	}

}
