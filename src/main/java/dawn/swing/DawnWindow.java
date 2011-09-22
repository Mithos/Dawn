package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import dawn.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;
import static java.awt.BorderLayout.CENTER;

/**
 * The top-level dawn window.
 * 
 * On creation, this handles setting up the window (theme, objects, etc.). It also implements WindowListener to
 * make sure Gstreamer is closed correctly on an exit).
 */
public class DawnWindow extends JFrame implements WindowListener{
	
	public DawnWindow(){
		
		// Super and set title
		super("Dawn");
		
		// Setup window events
		addWindowListener(this);
		
		// Set Nimbus Look and Feel
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// Use Default Look and feel on systems where Nimbus is not present
		}
		
		// Create accessible 'content' panel
		JPanel content = new JPanel(new BorderLayout());
		this.setContentPane(content);
		
		content.add(new TrackList2(), CENTER);
		content.add(new PlayButton(), SOUTH);
		
		// Set Window close operation and set visible
		this.pack();
		this.setVisible(true);	
	}
	
	// WindowListener methods, most do nothing, quit GST on close event
	public void windowOpened(WindowEvent e){}
	public void windowClosing(WindowEvent e){
		Dawn.playbin.setState(State.NULL);
		Gst.quit();
		this.dispose();
	}
	public void windowClosed(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	
	
}
