package dawn;

// Import statements
// Java utils
import java.util.Vector;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;

// Gstreamer classes
import org.gstreamer.*;
import org.gstreamer.elements.*;

// Dawn classes
import dawn.library.*;
import dawn.swing.*;

/**
 * The main class of Dawn.
 * 
 * Use of GStreamer leaves a very procedural manner necessary. It also requires the use of at least
 * one global component (The playbin has to be accessible from all parts of the application). Currently,
 * the playbin and library are accessible globally.
 */
public class Dawn{
	
	// Public playqueue for universal access
	public static PlayQueue playQueue = null;

	// public track library 
	public static Library library = new Library();
	
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
        playQueue = new PlayQueue();
        
        // Load configuration from file
        loadConfig();
        
        // Create Dawn Window
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new DawnWindow();
			}
		});
        
	}
	
	/** Load the config from the config file */
	private void loadConfig(){
		
		// Open config file, stored in ~/.dawn/dawnrc
		Path config = Paths.get(System.getProperty("user.home"), ".dawn", "dawnrc");
		Charset charset = Charset.forName("US-ASCII");
		
		// Read Music Library path. This is the first line of the config file atm.
		try{
			library.setPath(Paths.get(Files.readAllLines(config, charset).get(0)));
		} catch (Exception e){
			//Todo error handling
		}
		
	}
}
