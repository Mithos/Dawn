package dawn.lowLevel;

// Util imports
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
		(new RescanWorker()).execute();
	}
	
	/** The worker thread to rescan the library and update the model */
	private class RescanWorker extends SwingWorker<Void, Track> {
		
		/** create a new LibraryFileVisitor and walk the file tree */
		@Override
		public Void doInBackground(){
			// Walk file tree
			try{
				LibraryFileVisitor fileVisitor = new LibraryFileVisitor();
				Files.walkFileTree(Library.this.getPath(), fileVisitor);
			} catch (Exception e){
				// HANDLE YOUR EXCEPTIONS!
			}
			return null;
		}
		
		/** 
		 * A class used by doInBackground to handle each file as it is encountered 
		 * 
		 * This class has its own instance of PlayBin2, and is synchronized so that only one file is scanned at a time.
		 * The scanned files are published by the instance of the outer RescanThread
		 */
		private class LibraryFileVisitor extends SimpleFileVisitor<Path> implements Bus.TAG{
	
			// Create tag scanning playbin
			private final PlayBin2 playbin = new PlayBin2("LibraryFileVisitor");
		
			// Initialize utility variables
			private Path currentPath = null;
			private boolean currentPathAdded = false; // Used to make sure each file is only added once
			
			public LibraryFileVisitor(){
				playbin.setVideoSink(ElementFactory.make("fakesink", "video-sink"));
				playbin.setAudioSink(ElementFactory.make("fakesink", "audio-sink"));
				playbin.getBus().connect(this);
			}
			
			/**
			 * On file visit, add file to playbin, add file to library, set playbin to catch 
			 */
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
				try{
					if(Files.probeContentType(file).startsWith("audio")){
						currentPath = file;
						synchronized(currentPath){ // Synchronize so cannot start next file until previous file is completed
							currentPathAdded = false;
							playbin.setInputFile(file.toFile());
							playbin.setState(State.PAUSED);
							playbin.setState(State.NULL);
						}
					}
				}catch(Exception e){
					// EXCEPTION, EXCEPTION, EXCEPTION
				}
				// Move on to next file
				return CONTINUE;
			}
			
			
			/** Action to take on tag events */
			public void tagsFound(GstObject source, TagList tagList) {
				if(!currentPathAdded){
					RescanWorker.this.publish(new Track(currentPath.toFile(), tagList));
				}
				currentPathAdded = true;
			}
		} // End of LibraryFileVisitor class
		
		/** Add the tracks published by the file walk to the model on the EDT */
		public void process(List<Track> chunks){
			for(Track chunk : chunks){
				Library.this.add(chunk);
			}	
		}
	} // End of RescanWorker Class 
	
	

}
