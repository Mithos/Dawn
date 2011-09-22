package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.Vector;

import dawn.*;

import org.gstreamer.*;

public class TrackList2 extends JPanel implements MouseListener, KeyListener{
	
	private JTable table;
	private TrackTableModel model;
	
	public TrackList2(){
		// Create Panel
		super(new BorderLayout());
		
		// Create Table
		model = new TrackTableModel();
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(this);
		table.addKeyListener(this);
		
		// Create Scroll Pane and assemble
		JScrollPane tableScroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tableScroller.setPreferredSize(new Dimension(500, 200));
		
		this.add(tableScroller, BorderLayout.CENTER);
	}
	
	
	
	// Model Settings
	private class TrackTableModel extends AbstractTableModel{
		private String[] columnNames = { "Track", "Title", "Artist", "Album" }; // create title names
		private Vector<Track> data = Dawn.library; //get reference to library

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			switch(col){
			case 0: return String.valueOf(data.get(row).trackNumber);
			case 1: return data.get(row).title;
			case 2: return data.get(row).artist;
			case 3: return data.get(row).album;
			default: return data.get(row);
			}
		}
	
		/** Always returns String.class() */
		public Class getColumnClass(int c) {
			return String.class;
		}
		
		/** Always returns false */
		public boolean isCellEditable(int row, int col) {return false;}
					
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
    
    // Enter key handling
    public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			setTrack();
			e.consume(); // prevent other actions (e.g. row progression)
		}
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    private void setTrack(){
		Dawn.playbin.setState(State.NULL);
		Dawn.playbin.setInputFile(((Track)table.getValueAt(table.getSelectedRow(), -1)).file); // Using table methods allows for sorting
		
		/*
		 * Table#get value at returns the object that represents the row selected when column specified is -1
		 * Combining this with rowSelected allows correct song to be picked despite sorting
		 */
		 
		Dawn.playbin.setState(State.PLAYING);
	}	
	
}
