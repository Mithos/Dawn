package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.beans.*;

import java.util.*;
import java.nio.file.*;

import dawn.*;
import dawn.lowLevel.*;

import org.gstreamer.*;

public class TrackList extends JPanel implements MouseListener, KeyListener{
	
	private final JTable table;
	private final Library model;
	private final TableRowSorter<Library> sorter;
	
	public TrackList(){
		// Create Panel
		super(new BorderLayout());
		
		// Create Table
		model = Dawn.library;
		table = new JTable(model);
		table.setDragEnabled(true);
		
		//model.addTableModelListener(this);
		table.addMouseListener(this);
		table.addKeyListener(this);
		
		// Setup sorting and filtering -- fails
		sorter = new TableRowSorter<Library>(model);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys); 
		table.setRowSorter(sorter);
		
		// Create Scroll Pane and assemble
		JScrollPane tableScroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tableScroller.setPreferredSize(new Dimension(500, 200));
		
		this.add(tableScroller, BorderLayout.CENTER);
	}
	
	public void scanMediaLibrary(Path p){
		final RescanTask task = new RescanTask(p);
		task.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				if(task.getState() == SwingWorker.StateValue.DONE)
					System.out.println("Worker reports done!");
			}
		});
		task.execute();
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
		if(table.getSelectedRowCount() > 0){ // Quick fix for null pointers that can potentially be generated when table is updating
			for(int index : table.getSelectedRows()){
				Dawn.playQueue.add( (Track)model.getTrackAt(table.convertRowIndexToModel(index)));
			}
		}
	}	
	
}
