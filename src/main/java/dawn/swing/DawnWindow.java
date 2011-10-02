package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import dawn.*;
import dawn.action.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import static java.awt.BorderLayout.*;

/**
 * The top-level dawn window.
 * 
 * On creation, this handles setting up the window (theme, objects, etc.). It also implements WindowListener to
 * make sure Gstreamer is closed correctly on an exit).
 */
public class DawnWindow extends JFrame implements WindowListener{
	
	private TrackList trackList = TrackList.get();
	private NowPlaying nowPlaying = new NowPlaying();
	private ControlPanel controlPanel = ControlPanel.get();
	
	public DawnWindow(){
		
		// Super and set title
		super("Dawn");
		
		// Set Nimbus Look and Feel
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Setup window events
		addWindowListener(this);
		
		// Create accessible 'content' panel
		JPanel content = new JPanel(new BorderLayout());
		this.setContentPane(content);
		
		//Create a split pane with the two scroll panes in it.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, trackList, nowPlaying);
		splitPane.setResizeWeight(1.0);
		splitPane.setOneTouchExpandable(false);
		
		content.add(splitPane, CENTER);
		content.add(controlPanel, SOUTH);
		
		setJMenuBar(new DawnMenu());
		
		// Set Window close operation and set visible
		this.pack();
		this.setVisible(true);	
	}
	
	// WindowListener methods, most do nothing, quit GST on close event
	public void windowOpened(WindowEvent e){}
	public void windowClosing(WindowEvent e){
		QuitAction.get().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Quit"));
	}
	public void windowClosed(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	
}
