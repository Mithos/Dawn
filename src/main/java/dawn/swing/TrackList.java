package dawn.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.beans.*;

import java.util.*;
import java.nio.file.*;

import dawn.*;
import dawn.lowLevel.*;

import org.gstreamer.*;

/**
 * The dawn window track listing.
 * 
 * This lightweight component extends JPanel to provide an area for displaying the track listing.
 * This includes a table of tracks, as well as a filter bar and progress bar for library scanning.
 * 
 */
public class TrackList extends JPanel implements MouseListener, KeyListener{
	
	private final JTable table;
	private final Library model;
	private final TableRowSorter<Library> sorter;
	
	private final JProgressBar progressBar;
	private final JTextField searchBox;
	
	public TrackList(){
		// Create Panel
		super(new BorderLayout());
		
		// Create Table
		model = Dawn.library;
		table = new JTable(model);
		table.setDragEnabled(true);
		
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(10); 
			} else {
				column.setPreferredWidth(100);
			}
		}
		
		table.addMouseListener(this);
		table.addKeyListener(this);
		
		// Setup sorting and filtering
		sorter = new TableRowSorter<Library>(model);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys); 
		table.setRowSorter(sorter);
		
		// Create Scroll Pane
		JScrollPane tableScroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		tableScroller.setPreferredSize(new Dimension(500, 200));
		
		// Setup "North" panel, progress bar and search box
		JPanel northPanel = new JPanel(new BorderLayout());
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(false);
		progressBar.setVisible(false);
		
		searchBox = new JTextField();
		searchBox.setColumns(15);
		searchBox.getDocument().addDocumentListener(new DocumentListener(){ // watch for text changes and update filter accordingly
			public void	changedUpdate(DocumentEvent e){
				documentChanged(e);
			}
			public void insertUpdate(DocumentEvent e){
				documentChanged(e);
			}
			public void removeUpdate(DocumentEvent e){
				documentChanged(e);
			}
			private void documentChanged(DocumentEvent e){
				Document doc = e.getDocument();
				RowFilter<Library, Object> rf = null;
				// If text doesn't parse do not update
				try{
					String text = doc.getText(0, doc.getLength());
					rf = RowFilter.regexFilter(text);
				} catch (java.util.regex.PatternSyntaxException patternE) {
					return;
				} catch (BadLocationException locE){
					return;
				}
				sorter.setRowFilter(rf);
			}
		});
		
		northPanel.add(progressBar, BorderLayout.EAST);
		northPanel.add(searchBox, BorderLayout.WEST);
		
		//Assemble TrackList
		this.add(northPanel, BorderLayout.NORTH);
		this.add(tableScroller, BorderLayout.CENTER);
	}
	
	public void scanMediaLibrary(Path p){
		final RescanTask task = new RescanTask(p);
		
		TrackList.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);
		
		task.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				if(task.getState() == SwingWorker.StateValue.DONE){
					TrackList.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					progressBar.setVisible(false);
				} else {
					int progress = task.getProgress();
					if(0 == progress){
						progressBar.setIndeterminate(true);
					} else {
						progressBar.setIndeterminate(false);
						progressBar.setValue(progress);
					}
				}
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
