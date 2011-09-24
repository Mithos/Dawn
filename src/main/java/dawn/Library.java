package dawn;

import javax.swing.*;
import javax.swing.table.*;

import java.util.Vector;

public class Library extends AbstractTableModel{

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
	
	/** Always returns false, table cannot be edited by a user. */
	public boolean isCellEditable(int row, int col) {return false;}
	
	/** Allow data to be added and notify listeners. */
	public void add(Track t){
		data.add(t);
		fireTableDataChanged();
	}

}
