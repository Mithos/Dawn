package dawn.lowLevel;

// Util and file imports
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;

// Swing imports
import javax.swing.*;
import javax.swing.table.*;

// GStreamer imports
import org.gstreamer.*;
import org.gstreamer.elements.*;

public class Library extends AbstractTableModel{

	//
	// Path
	//
	
	private Path libraryPath = Paths.get(System.getProperty("user.home"), "Music"); // Initialize to a sensible default
	
	public void setPath(Path path){
		libraryPath = path;
	}
	
	public Path getPath(){
		return libraryPath;
	}

	//
	// Table model data
	//
	
	private String[] columnNames = { "Track", "Title", "Artist", "Album" }; // create title names
	private Vector<Track> data = new Vector<Track>();

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/**
	 * Return the value at the given row/column position
	 * 
	 * For the interval [0,3] values are selected from the relevant fields of the track object (track, title, artisit, album).
	 * For all other values the null is returned.
	 * 
	 */
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0: return new Short(data.get(row).trackNumber);
		case 1: return data.get(row).title;
		case 2: return data.get(row).artist;
		case 3: return data.get(row).album;
		default: return null;
		}
	}
	
	/** Return the track for the given row */
	public Track getTrackAt(int row){
		return data.get(row);
	}

	/** Return the class of the data in the column */
	public Class getColumnClass(int c) {
		if(0 == c) return Short.class;
		else return String.class;
	}
	
	/** Allow data to be added and notify listeners. */
	public void add(Track t){
		data.add(t);
		fireTableDataChanged();
	}
	
	/** clear the track db */
	public void clear(){
		data = new Vector<Track>();
		fireTableDataChanged();
	}
	
	/** Always returns false, table cannot be edited by a user. */
	public boolean isCellEditable(int row, int col) {return false;}
	
	//
	// Library rescan code
	//
	
	/** Create a new worker thread and execute it to walk file tree */
	public void rescan(){
		(new RescanTask(getPath())).execute();
	}
}
