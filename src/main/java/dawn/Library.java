package dawn;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Vector;

import dawn.library.LibraryFileVisitor;
/**
 * The library.
 * 
 * The library is mainly done statically, with its own playbin to scan metadata tags.
 */
public class Library {

	public static Vector<Track> tracks = new Vector<Track>();
	
	private static Path libraryPath = null;
	
	public static Path getPath(){
		return libraryPath;
	}
	
	public static void setPath(Path path){
		libraryPath = path;
	}
	
	public static void rebuildLibrary(){
		
		// Walk file tree
		
		try{
			LibraryFileVisitor fileVisitor = new LibraryFileVisitor();
			Files.walkFileTree(libraryPath, fileVisitor);
		} catch (Exception e){
			// HANDLE YOUR EXCEPTIONS!
		}
		
	}
	
}
