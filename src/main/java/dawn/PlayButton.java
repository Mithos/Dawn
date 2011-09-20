package dawn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

/** The great big play button in the control panel */
public class PlayButton extends JButton implements ActionListener{
	
	public PlayButton(){
		addActionListener(this);
		setText("Play");
	}

	public void actionPerformed(ActionEvent e){
		if (Dawn.playbin.getState() == State.PLAYING){
			Dawn.playbin.setState(State.PAUSED);
			setText("Play");
		} else {
			Dawn.playbin.setState(State.PLAYING);
			setText("Pause");
		}
	}
}
