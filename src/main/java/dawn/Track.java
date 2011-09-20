package dawn;

import java.io.File;

/** An imutable track structure */
public class Track{
	
	public final File file;
	public final String artist;
	public final String album;
	public final String title;
	public final int trackNumber;
	
	/** Create a new Track object from the given File and tags */
	public Track(File file, String[] tags){
		
		// Assign File fields
		this.file = file;
		
		// Parse the given tags and assign the relevent fields
		// Largely discard tag data, only store the bits we're interested in
		// TODO work out parse settings, simple regex should be enough
		artist = "";
		album = "";
		title = "";
		trackNumber = 0;
	}
}
