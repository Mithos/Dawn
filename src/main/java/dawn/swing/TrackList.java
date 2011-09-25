package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.*;
import java.nio.file.*;

import dawn.*;

import org.gstreamer.*;

public class TrackList extends JPanel implements MouseListener, KeyListener, TableModelListener{
	
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
//		table.setRowSorter(sorter);
		
		// Disable user sorting
		for(MouseListener listener : table.getTableHeader().getMouseListeners()){
			table.getTableHeader().removeMouseListener(listener);
		}

		
		// Create Scroll Pane and assemble
		JScrollPane tableScroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tableScroller.setPreferredSize(new Dimension(500, 200));
		
		this.add(tableScroller, BorderLayout.CENTER);
	}
	
	// Model update handling
	/** Refresh the table when model data changes */
	public void tableChanged(TableModelEvent e){
		//table.revalidate();
	}
	
	public void scanMediaLibrary(Path p){
		model.setPath(p);
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			public Void doInBackground(){
				TrackList.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				model.rebuild(); // rebuild the library
				TrackList.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				try{
					table.setRowSorter(sorter);
				} catch (Exception e){
					e.printStackTrace();
					System.exit(1);
				}
				return null;
			}
		};
		worker.execute();
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
				Dawn.playQueue.add( (Track)model.getValueAt(table.convertRowIndexToModel(index), -1));
			}
		}
	}	
	
}
