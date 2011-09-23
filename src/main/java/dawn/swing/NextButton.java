package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import dawn.*;

public class NextButton extends JButton implements ActionListener{
	
	public NextButton(){
		setText("Next");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		Dawn.playQueue.next();
	}
	
}
