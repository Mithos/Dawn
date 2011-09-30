package dawn.swing;

import dawn.action.*;
import dawn.swing.*;

import javax.swing.*;

public class DawnMenu extends JMenuBar{
	
	private JMenu dawn;
	private JMenu control;
	private JMenu help;
	
	public DawnMenu(){
		dawn = new JMenu("Dawn");
		control = new JMenu("Control");
		help = new JMenu("Help");
		
		add(dawn);
		add(control);
		add(help);
		
		control.add(PlayAction.get());
		control.add(PrevAction.get());
		control.add(NextAction.get());
		
		// Temporary until moved into action
		java.awt.event.ActionListener mediaDirAction = new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e){
				TrackList.get().scanMediaLibrary(java.nio.file.Paths.get(System.getProperty("user.home"), "Music"));
			}
		};
		JMenuItem mediaDir = new JMenuItem("Set Media Folder");
		mediaDir.addActionListener(mediaDirAction);
		dawn.add(mediaDir);
		
		
	}
	
	
}
