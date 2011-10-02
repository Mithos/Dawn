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
		
		dawn.add(RescanAction.get());
		dawn.addSeparator();
		dawn.add(QuitAction.get());
		
		control.add(PlayAction.get());
		control.add(NextAction.get());
		control.add(PrevAction.get());
		
	}
	
	
}
