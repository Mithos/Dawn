package dawn.swing;

import java.awt.event.*;
import javax.swing.*;

import org.gstreamer.*;

import dawn.*;

public class DawnMenuBar extends JMenuBar implements ActionListener{

	private JMenu dawn;
	private JMenuItem buildLibrary;
	
	public DawnMenuBar(){
		dawn = new JMenu("Dawn");
		buildLibrary = new JMenuItem("Rescan Library");
		buildLibrary.addActionListener(this);
		dawn.add(buildLibrary);
		add(dawn);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == buildLibrary){
			Dawn.rebuildLibrary();
			// need to force a repaint/validate somehow
		}
	}

}
