package dawn.swing;

import dawn.action.*;
import dawn.swing.*;

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
		
		dawn.add(RescanAction.get());
		dawn.addSeparator();
		dawn.add(QuitAction.get());
		
		control.add(PlayAction.get());
		control.add(NextAction.get());
		control.add(PrevAction.get());
		
		// Testing adding a spinner to the menu.
		
		JSpinner stest = new JSpinner();
		stest.setPreferredSize(new java.awt.Dimension(100, stest.getPreferredSize().height));
		JPanel volume = new JPanel();
		volume.setOpaque(false);
		volume.setLayout(new java.awt.BorderLayout());
		volume.add(new JLabel("Volume:"), java.awt.BorderLayout.WEST);
		volume.add(stest, java.awt.BorderLayout.CENTER);
		control.add(volume);
		
	}
	
	
}
