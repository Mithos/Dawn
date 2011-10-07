package org.dawn.swing;

import org.dawn.action.*;
import org.dawn.swing.*;
import org.dawn.action.RescanAction;

import javax.swing.*;

public class DawnMenu extends JMenuBar{
	
	private static DawnMenu singleton = null;
	public static DawnMenu get(){
		if(null == singleton) singleton = new DawnMenu();
		return singleton;
	}
	
	
	private JMenu dawn;
	private JMenu control;
	private JMenu help;
	
	private DawnMenu(){
		dawn = new JMenu("Dawn");
		control = new JMenu("Control");
		help = new JMenu("Help");
		
		add(dawn);
		add(control);
		add(help);
		
		dawn.add( RescanAction.get() );
		dawn.addSeparator();
		dawn.add(QuitAction.get());
		
		control.add(PlayAction.get());
		control.add(NextAction.get());
		control.add(PrevAction.get());
		
		// volume needs a little more work than other actions
		JPanel volume = VolumeSpinner.get();
		volume.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 10)); //border lines up the control with the other menu items
		control.add(volume);
		
		help.add(AboutAction.get());
		
	}
	
	
}
