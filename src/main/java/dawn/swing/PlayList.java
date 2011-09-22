package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;

import dawn.*;

import org.gstreamer.*;

public class PlayList extends JPanel implements MouseListener, KeyListener, Bus.EOS{
	
	private JList<Track> list;
	
	
	public PlayList(){
		// Initialize panel
		super(new BorderLayout());
		
		// Setup list
		list = new JList<Track>(Dawn.playlist);
		list.addMouseListener(this);
		list.addKeyListener(this);
		Dawn.playlist.getBus().connect(this);
		list.setCellRenderer(new TrackRenderer());
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(200, 200));
		
		// Construct panel
		this.add(listScroller, BorderLayout.CENTER);
		this.add(new JLabel("Now Playing", BorderLayout.NORTH));
	}

	// Renderer code
	
	private class TrackRenderer extends JLabel implements ListCellRenderer<Track> {
		public TrackRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
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

			//Set and text.
			setText(value.title);

			return this;
		}
	}
	
	
	// End of stream handling
	public void endOfStream(GstObject source) {
		list.setSelectedIndex(list.getSelectedIndex() + 1); //Select next track
		setTrack();
		
	}
	
	// Double click handling
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			setTrack();
		}
    }
    
	// Key handling
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			setTrack();
		}
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    /** Set the track to be the selected track and start playing */
    private void setTrack(){
		Dawn.playbin.setState(State.NULL);
		Dawn.playbin.setInputFile( list.getSelectedValue().file );
		Dawn.playbin.setState(State.PLAYING);
	}
}
