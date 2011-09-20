package dawn;

import java.io.File;

/** A track structure */
public class Track{
	
	public final File file;
	public String artist = "";
	public String album = "";
	public String title = "";
	public int trackNumber = 0;
	
	/** Create a new Track object from the given File and tags */
	public Track(File file){
		// Assign File fields
		this.file = file;
	}
	
	public void assignTags(String[] tags){
		
		// Parse the given tags and assign the relevent fields
		// Largely discard tag data, only store the bits we're interested in
		// TODO work out parse settings, simple regex should be enough
		artist = "";
		album = "";
		title = "";
		trackNumber = 0;
	}
}
