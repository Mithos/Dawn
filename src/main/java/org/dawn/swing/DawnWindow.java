package org.dawn.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.dawn.*;
import org.dawn.action.*;

import org.gstreamer.*;
import org.gstreamer.elements.*;

import static java.awt.BorderLayout.*;

/**
 * The top-level dawn window.
 * 
 * On creation, this handles setting up the window (theme, objects, etc.). 
 */
public class DawnWindow extends JFrame{
	
	private static DawnWindow singleton = null;
	public static DawnWindow get(){
		return singleton;
	}
	public static void init(){
		singleton = new DawnWindow();
	}
	
	
	private DawnWindow(){
		
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
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				QuitAction.get().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Quit"));
			}	
		});
		
		// Create accessible 'content' panel
		JPanel content = new JPanel(new BorderLayout());
		this.setContentPane(content);
		
		//Create a split pane with the two scroll panes in it.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TrackList.get(), NowPlaying.get());
		splitPane.setResizeWeight(1.0);
		splitPane.setOneTouchExpandable(false);
		
		content.add(splitPane, CENTER);
		content.add(ControlPanel.get(), SOUTH);
		
		setJMenuBar(DawnMenu.get());
		
		// set visible
		this.pack();
		this.setVisible(true);	
	}
	
}
