package dawn.swing;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.*;

import java.util.Vector;

import dawn.*;

public class TrackList extends JPanel{
	
	public TrackList(){
		super(new BorderLayout());
		Vector<String> trackTitles = new Vector<String>();
		for(int i = 0; i < Library.tracks.size(); i++)
			trackTitles.add(Library.tracks.get(i).title);
		JList<String> list = new JList<String>(trackTitles);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		this.add(listScroller, BorderLayout.CENTER);
	}
	
	
}
