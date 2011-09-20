package dawn;

// Import statements
// Java utils
import java.util.Vector;
import java.nio.*;
import java.nio.file.*;
import static java.nio.file.FileVisitResult.*;
import java.nio.charset.*;
import javax.swing.SwingUtilities;

// Gstreamer classes
import org.gstreamer.*;
import org.gstreamer.elements.*;

/**
 * The main class of Dawn.
 * 
 * Use of GStreamer leaves a very procedural manner necessary. It also requires the use of at least
 * one global component (The playbin has to be accessible from all parts of the application). Currently,
 * the playbin and library are accessible globally.
 */
public class Dawn{
	
	public static PlayBin2 playbin = null;
	public static Vector<Track> library = new Vector<Track>();
	
	// Main method (and associated constructor)
		
	public static void main(String[] args){
		new Dawn(args);
	}
	
	/**
	 * Creates a new instance of Dawn.
	 * 
	 * This class performs all of the initialization required.
	 * It sets up the GStreamer interface, loads config from rc file and creates a main window.
	 * Currently, the database of tracks is rebuilt every startup - may take a while.
	 */
	private Dawn(String[] args){
		
		// Initialize the gstreamer framework, and let it interpret any command line flags it is interested in.
        args = Gst.init("Dawn", args);
        
        // Initialize the playbin
        playbin = new PlayBin2("AudioPlayer");
        
        // Prevent any sort of video window from being created
        playbin.setVideoSink(ElementFactory.make("fakesink", "videosink"));
        
        // Load configuration from file
        loadConfig();
        
        // Build library
        Library.rebuildLibrary();
       
        // Test block
        
        for(Track t : Library.tracks){
			System.out.print(t.file.toString() + " -- "+ t.title.toString() + "\n"); 
		} 
        
        // Create Dawn Window
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new DawnWindow();
			}
		});
		
		// Initialize GST
		playbin.setInputFile(Paths.get(System.getProperty("user.home"),"Music","dd.ogg").toFile());
        
	}
	
	/** Load the config from the config file */
	private void loadConfig(){
		
		// Open config file, stored in ~/.dawn/dawnrc
		Path config = Paths.get(System.getProperty("user.home"), ".dawn", "dawnrc");
		Charset charset = Charset.forName("US-ASCII");
		
		// Read Music Library path. This is the first line of the config file.
		try{
			Library.setPath(Paths.get(Files.readAllLines(config, charset).get(0)));
		} catch (Exception e){
			//Todo error handling
		} finally {
			// Set path to defult if not specified
			if(null == Library.getPath()) Library.setPath(Paths.get(System.getProperty("user.home"), "Music"));
		}
		
	}
}
