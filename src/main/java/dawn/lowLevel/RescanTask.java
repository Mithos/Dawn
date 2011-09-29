package dawn.lowLevel;

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
	private TagList tagCache = null;
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
		
		// Create a cyclic barrier to synchronize threads
		final CyclicBarrier barrier = new CyclicBarrier(2);
		
		// Update the tagCache if this is the first tag event for a given file
		playbin.getBus().connect(new Bus.TAG(){
			public void tagsFound(GstObject source, TagList tagList) {
				System.out.println("tag event");
				if(!currentPathScanned){
					RescanTask.this.publish(new Track(currentPath.toFile(), tagList));
					currentPathScanned = true;
				}
			}
		});
		
		/*
		// Async done should correspond to all tags found, therefore release the lock
		playbin.getBus().connect(new Bus.ASYNC_DONE(){
			public void asyncDone(GstObject source) {
				System.out.println("Async");
				playbin.setState(State.NULL);
				try{
					barrier.await();
				} catch (BrokenBarrierException e){
				} catch (InterruptedException e) {
				}
			}
		});
		
		// Data flowing means that all tags are found. Alternative to ASYNC_DONE
		audio.set("signal-handoffs", true);
        video.set("signal-handoffs", true);
		
		BaseSink.HANDOFF handoff = new BaseSink.HANDOFF() {
            public void handoff(BaseSink sink, Buffer buffer, Pad pad) {
				System.out.println("Handoff");
				playbin.setState(State.NULL);
				try{
					barrier.await();
				} catch (BrokenBarrierException e){
				} catch (InterruptedException e) {
				}
            }
        };
        //audio.connect(handoff);
        //video.connect(handoff);
		*/
		int taskLength = musicFiles.size();
		System.out.println(taskLength);
		
		// Set each file to the pipe and scan for tags
		for(int i = 0; i < taskLength; i++){
			currentPath = musicFiles.get(i);
			synchronized(currentPath){
				System.out.println("File Started: " + musicFiles.get(i).toString());
				
				
				currentPathScanned = false;
				
				playbin.setInputFile(currentPath.toFile());
				playbin.setState(State.PAUSED);
				playbin.setState(State.NULL);
				/*
				// Wait for Bus events
				try{
					barrier.await(500, TimeUnit.MILLISECONDS); //wait at most half a second
				} catch (BrokenBarrierException e) {
				} catch (InterruptedException e) {
				} catch (TimeoutException e) { // if too long passes without finding tags assume they are null
					tagCache = null;
				}*/
				System.out.println("Barrier passed");
				// Publish resulting track object
				//Track t = new Track(currentPath.toFile(), tagCache);
				System.out.println("Track Created");
				
				//publish(t);
				System.out.println("Published");
				
				
				// Set progress as percentage
				setProgress((100*i)/taskLength);
				
				System.out.println("progress set");
				
				// Reset barrier for next iteration in loop
				//barrier.reset();
				System.out.println("Barrier reset");
			}
		}
		
		System.out.println("Done Scanning");
		// Return from method
		
		playbin.setState(State.NULL);
		return null;
	}
	
	/** Add the tracks published by the background thread to the model on the EDT */
	public void process(List<Track> chunks){
		for(Track chunk : chunks){
			Dawn.library.add(chunk);
		}
		System.out.println("Processed chunks");	
	}
}
