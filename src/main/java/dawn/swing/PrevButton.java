package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import dawn.*;

public class PrevButton extends JButton implements ActionListener{
	
	public PrevButton(){
		setText("Previous");
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		Dawn.playQueue.previous();
	}
	
}
