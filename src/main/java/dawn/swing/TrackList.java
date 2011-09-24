package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.Vector;

import dawn.*;

import org.gstreamer.*;

public class TrackList extends JPanel implements MouseListener, KeyListener, TableModelListener{
	
	private final JTable table;
	private final Library model;
	
	public TrackList(){
		// Create Panel
		super(new BorderLayout());
		
		// Create Table
		model = Dawn.library;
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setDragEnabled(true);
		
		model.addTableModelListener(this);
		table.addMouseListener(this);
		table.addKeyListener(this);
		
		// Create Scroll Pane and assemble
		JScrollPane tableScroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tableScroller.setPreferredSize(new Dimension(500, 200));
		
		this.add(tableScroller, BorderLayout.CENTER);
	}
	
	// Model update handling
	/** Refresh the table when model data changes */
	public void tableChanged(TableModelEvent e){
		table.revalidate();
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
    
    /**
     * Set the track
     * Use table conversion methods to get the correct object from the model
     * Model getValueAt method returns the Track object when given a column of -1
     */
    private void setTrack(){
		Dawn.playQueue.add( (Track)model.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), -1));
	}	
	
}
