package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;

import dawn.*;

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
					setBackground(list.getBackground());
				} else { 
					setBackground(Color.WHITE);
				}
				setForeground(list.getForeground());
			}

			//Set and text.
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
		if(e.getClickCount() == 2 && e.getSource() == list){
			setTrack();
		} else if (e.getSource() == shuffleButton) {
			shuffle = !shuffle;
			if(shuffle){
				shuffleButton.setText("Turn shuffle off");
			} else {
				shuffleButton.setText("Turn shuffle on");
			}
			Dawn.playQueue.setShuffle(shuffle);
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
		Dawn.playQueue.stop();
		Dawn.playQueue.setIndex( list.getSelectedIndex() );
		Dawn.playQueue.play();
	}
}
