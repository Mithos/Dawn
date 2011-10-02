package dawn.action;

// Util and file imports
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.util.concurrent.*;

// Swing imports
import javax.swing.*;
import javax.swing.table.*;

// GStreamer imports
import org.gstreamer.*;
import org.gstreamer.elements.*;

// Dawn imports
import dawn.*;
import dawn.lowLevel.*;

/** 
 * This class extends SwingWorker to provide an executable instance that will scan a given folder for
 * Music files, and add these files to the Library.
 * 
 * This class is seperated from the library so that it need not be the library instance itself
 * that calls it. For example, a GUI object containing the table that represents the library
 * could create and execute an instance of this, allowing a progress bar to be shown
 * based on the progress while this worker works.
 */
public class RescanTask extends SwingWorker<Void, Track> {
	
	/** The path to scan */
	private final Path libraryPath;
	
	/**
	 * Create a new Rescan task to scan the given folder
	 */
	public RescanTask(Path libraryPath){
		this.libraryPath = libraryPath;
	}
	
	
	// Instance variables for doInBackground to use
	private Path currentPath = null;
	private boolean currentPathScanned = false;
	private Vector<Path> musicFiles = new Vector<Path>();
	
	/** 
	 * The background method of this thread. It will search the Path given
	 * when the worker was created for files and add them to the library, if
	 * the library does not already contain the same track
	 */
	@Override
	public Void doInBackground(){
		
		setProgress(0); // set the initial progress
		
		// Walk file tree to find all music files.
		try{
			SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
					try{
						if(Files.probeContentType(file).startsWith("audio") && !(file.toString().endsWith("m3u"))) musicFiles.add(file);
					}catch(Exception e){
						// EXCEPTION, EXCEPTION, EXCEPTION
					}
					// Move on to next file
					return CONTINUE;
				}
			};
			Files.walkFileTree(libraryPath, fileVisitor);
		} catch (Exception e){
			// HANDLE YOUR EXCEPTIONS!
		}
		
		// Create playbin to check for tags
		final PlayBin2 playbin = new PlayBin2("TagScanner");
		FakeSink audio = (FakeSink)ElementFactory.make("fakesink", "audio-sink");
		FakeSink video = (FakeSink)ElementFactory.make("fakesink", "video-sink");
		playbin.setVideoSink(video);
		playbin.setAudioSink(audio);
			
		// Update the tagCache if this is the first tag event for a given file
		playbin.getBus().connect(new Bus.TAG(){
			public void tagsFound(GstObject source, TagList tagList) {
				if(!currentPathScanned){
					RescanTask.this.publish(new Track(currentPath.toFile(), tagList));
					currentPathScanned = true;
				}
			}
		});
		
		int taskLength = musicFiles.size();
		
		// Set each file to the pipe and scan for tags
		for(int i = 0; i < taskLength; i++){
			currentPath = musicFiles.get(i);
			synchronized(currentPath){
				currentPathScanned = false;
				
				playbin.setInputFile(currentPath.toFile());
				playbin.setState(State.PAUSED);
				playbin.setState(State.NULL);
				
				// Set progress as percentage
				setProgress((100*i)/taskLength);

			}
		}
		
		// Return from method
		playbin.setState(State.NULL);
		return null;
	}
	
	/** Add the tracks published by the background thread to the model on the EDT */
	public void process(List<Track> chunks){
		for(Track chunk : chunks){
			Library.get().add(chunk);
		}
	}
}
