package dawn;

import dawn.library.*;

import javax.swing.*;
import javax.swing.table.*;

import java.nio.file.*;

import java.util.Vector;

public class Library extends AbstractTableModel{

	// Path variables
	private Path libraryPath = Paths.get(System.getProperty("user.home"), "Music"); // Initialize to a sensible default
	
	public void setPath(Path path){
		libraryPath = path;
	}
	
	public void rebuild(){
		// Walk file tree
		try{
			LibraryFileVisitor fileVisitor = new LibraryFileVisitor();
			Files.walkFileTree(libraryPath, fileVisitor);
		} catch (Exception e){
			// HANDLE YOUR EXCEPTIONS!
		}
	}

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
	 * For all other values the Track object for the row is returned.
	 * 
	 */
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0: return new Short(data.get(row).trackNumber);
		case 1: return data.get(row).title;
		case 2: return data.get(row).artist;
		case 3: return data.get(row).album;
		default: return data.get(row);
		}
	}

	/** Return the class of the data in the column */
	public Class getColumnClass(int c) {
		if(0 == c) return Short.class;
		else return String.class;
	}
	
	/** Always returns false, table cannot be edited by a user. */
	public boolean isCellEditable(int row, int col) {return false;}
	
	/** Allow data to be added and notify listeners. */
	public void add(Track t){
		data.add(t);
		fireTableDataChanged();
	}
	
	/** clear the track db */
	public void clear(){
		data = new Vector<Track>();
	}

}