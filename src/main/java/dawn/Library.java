package dawn;

import java.nio.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.nio.charset.*;
import java.util.Vector;
/**
 * The library
 */
public class Library{
	
	private static Path libraryPath = null;

	private static Vector<Track> tracks = new Vector<Track>();
	
	public static Path getPath(){
		return libraryPath;
	}
	
	public static void setPath(Path path){
		libraryPath = path;
	}
	
	public static void rebuildLibrary(){
		
		// Define File visitor
		
		SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){

			//Print information about each type of file.
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
				try{
					if(Files.probeContentType(file).startsWith("audio")){
						tracks.add(new Track(file.toFile(), null)); // toFile() method needed as Gst uses File
						System.out.println(file);
					}
				}catch(Exception e){
					// EXCEPTION, EXCEPTION, EXCEPTION
				}
				return CONTINUE;
			}
		};
		
		// Walk file tree
		
		try{
			Files.walkFileTree(libraryPath, fileVisitor);
		} catch (Exception e){
			// HANDLE YOUR EXCEPTIONS!
		}
		
	}
	
}
