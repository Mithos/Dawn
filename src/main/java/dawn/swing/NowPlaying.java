package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;

import dawn.*;
import dawn.lowLevel.*;

import org.gstreamer.*;

public class NowPlaying extends JPanel implements MouseListener, KeyListener {
	
	private JList<Track> list;
	
	private JButton shuffleButton = new JButton();
	private boolean shuffle = false;
	
	public NowPlaying(){
		// Initialize panel
		super(new BorderLayout());
		
		// Setup list
		list = new JList<Track>(Dawn.playQueue);
		list.addMouseListener(this);
		list.addKeyListener(this);
		list.setCellRenderer(new TrackRenderer());
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(200, 200));
		
		// Setup Header
		JLabel header = new JLabel ("Now Playing");
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setVerticalAlignment(JLabel.CENTER);
		
		// Setup button
		shuffleButton.setText("Turn Shuffle On");
		shuffleButton.addMouseListener(this);
		
		// Construct panel
		this.add(listScroller, BorderLayout.CENTER);
		this.add(header, BorderLayout.NORTH);
		this.add(shuffleButton, BorderLayout.SOUTH);
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
				if(0 == index%2){
					setBackground(new Color(0xf2, 0xf2, 0xf2));
				} else { 
					setBackground(Color.WHITE);
				}
				setForeground(list.getForeground());
			}

			//Set text.
			setText(value.title);

			return this;
		}
	}
	
	// Double click handling
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2 && e.getSource() == list && e.getButton() == MouseEvent.BUTTON1){
			setTrack();
		} else if (e.getSource() == shuffleButton && e.getButton() == MouseEvent.BUTTON1) {
			shuffle = !shuffle;
			if(shuffle){
				shuffleButton.setText("Turn Shuffle Off");
			} else {
				shuffleButton.setText("Turn Shuffle On");
			}
			Dawn.playQueue.setShuffle(shuffle);
		}
		/*
		else if(e.getSource() == list && e.getButton() == MouseEvent.BUTTON2){
			list.setSelectedIndex(list.locationToIndex(MouseInfo.getPointerInfo().getLocation()));
			removeTrack();
		}*/
		// Right-click code needs more work.
    }
    
	// Key handling
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER: setTrack(); break;
		case KeyEvent.VK_DELETE: removeTrack(); break;		
		}
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    /** Set the track to be the selected track and start playing */
    private void setTrack(){
		Dawn.playQueue.stop();
		Dawn.playQueue.setIndex( list.getSelectedIndex() );
		Dawn.playQueue.play();
	}
	
	/** Remove the selected track from the queue */
	private void removeTrack(){
		Dawn.playQueue.remove(list.getSelectedIndex());
	}
}
