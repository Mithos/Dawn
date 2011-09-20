package dawn;

import java.io.File;
import org.gstreamer.TagList;

/** A track structure */
public class Track{
	
	public final File file;
	public String title = "Unknown";
	public String artist = "Unknown";
	public String album = "Unknown";
	public short trackNumber = -1;
	
	/** Create a new Track object from the given File and tags */
	public Track(File file){
		// Assign File fields
		this.file = file;
	}
	
	public void setTags(TagList tags){
		
		// Todo: complete parsing of all tags?
		// Parsed: artist, album, track-number, title
		// Not-Parsed: duration, date, genre, image, album-artist, composer, encoded-by, comment, extended-comment, private-id3v2-frame, isrc,  container-format, layer, mode, emphasis, bitrate
	
		title = tags.getValue("title", 0).toString();
		album = tags.getValue("album", 0).toString();
		artist = tags.getValue("artist", 0).toString();
		trackNumber = Short.parseShort(tags.getValue("track-number", 0).toString());
	}
}
