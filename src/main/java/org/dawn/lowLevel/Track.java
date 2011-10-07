package org.dawn.lowLevel;

import java.io.File;
import org.gstreamer.TagList;

/** An imutable track structure */
public class Track{
	
	public final File file;
	public final String title;
	public final String artist;
	public final String album ;
	public final short trackNumber;
	
	/** Create a new Track object from the given File and tags */
	public Track(File file, TagList tags){
		// Assign File fields
		this.file = file;
		
		// Todo: complete parsing of all tags?
		// Parsed: artist, album, track-number, title
		// Not-Parsed: duration, date, genre, image, album-artist, composer, encoded-by, comment, extended-comment, private-id3v2-frame, isrc,  container-format, layer, mode, emphasis, bitrate
		if(null == tags){
			title = file.toString();
			album = "Unknown Album";
			artist = "Unknown Artist";
			trackNumber = 0;
		}else {
			title = tags.getValue("title", 0).toString();
			album = tags.getValue("album", 0).toString();
			artist = tags.getValue("artist", 0).toString();
			trackNumber = Short.parseShort(tags.getValue("track-number", 0).toString());
		}
	}
	
	/**
	 * Check for equality.
	 * 
	 * Two tracks are equivalent if they refere to the same file
	 */ 
	public boolean equals(Track that){
		return (this.file == that.file);
	}
}
