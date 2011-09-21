package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;

import dawn.*;

import org.gstreamer.*;

public class TrackList extends JPanel implements MouseListener, KeyListener{
	
	JList<Track> list;
	
	public TrackList(){
		super(new BorderLayout());
		list = new JList<Track>(Dawn.library);
		list.addMouseListener(this);
		list.addKeyListener(this);
		list.setCellRenderer(new TrackRenderer());
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(500, 200));
		
		this.add(listScroller, BorderLayout.CENTER);
	}

	// Renderer code
	
	class TrackRenderer extends JLabel implements ListCellRenderer<Track> {
		public TrackRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
			setFont(new Font("Monospaced", Font.PLAIN, 12));
		}

		public Component getListCellRendererComponent(JList<? extends Track> list, Track value, int index, boolean isSelected, boolean cellHasFocus) {
			// Set the colours
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			// Set the variables
			// Truncate any Strings that are longer than the char limit.
			short num = value.trackNumber;
			String title = value.title;
			String artist = value.artist;
			String album = value.album;
			
			// If a string is longer than the relevant limit, truncate it and add ...
			if(title.length() > 47) title = title.substring(0,47) + "...";
			if(artist.length() > 22) artist = artist.substring(0,22) + "...";
			if(album.length() > 22) album = album.substring(0,22) + "...";

			//Set the icon and text.
			setText(String.format("%-3d | %-50s | %-25s | %-25s",num, title, artist, album));

			return this;
		}
	}
	
	// Double click handling
	public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			Dawn.playbin.setState(State.NULL);
			Dawn.playbin.setInputFile(list.getSelectedValue().file);
			Dawn.playbin.setState(State.PLAYING);
		}
    }
    
	// Key handling
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			Dawn.playbin.setState(State.NULL);
			Dawn.playbin.setInputFile(list.getSelectedValue().file);
			Dawn.playbin.setState(State.PLAYING);
		}
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}
