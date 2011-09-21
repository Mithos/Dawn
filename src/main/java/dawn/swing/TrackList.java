package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;

import dawn.*;

import org.gstreamer.*;

public class TrackList extends JPanel implements ListSelectionListener{
	
	JList<String> list;
	
	public TrackList(){
		super(new BorderLayout());
		Vector<String> trackTitles = new Vector<String>();
		for(int i = 0; i < Library.tracks.size(); i++)
			trackTitles.add(Library.tracks.get(i).title);
		list = new JList<String>(trackTitles);
		list.addListSelectionListener(this);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(400, 200));
		
		this.add(listScroller, BorderLayout.CENTER);
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (list.getSelectedIndex() == -1) {
			//No selection, do nothing

			} else {
			//Selection, enable the fire button.
				Dawn.playbin.setState(State.NULL);
				Dawn.playbin.setInputFile(Library.tracks.get(list.getSelectedIndex()).file);
			}
		}
}

	
	
}
