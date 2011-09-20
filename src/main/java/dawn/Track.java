package dawn;

import java.io.File;
import org.gstreamer.TagList;

/** A track structure */
public class Track{
	
	public final File file;
	public TagList tags;
	
	/** Create a new Track object from the given File and tags */
	public Track(File file){
		// Assign File fields
		this.file = file;
	}
	
	public void setTags(TagList tags){
		this.tags = tags;
	}
}
