package dawn.action;

import dawn.*;
import dawn.lowLevel.*;
import org.gstreamer.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * The play/pause action, as a singleton
 */
public class PlayAction extends AbstractAction {
	
	// Singleton Code
	private PlayAction(){
		super("Play");
		
		
		// Text handling code
		final PlayQueue q = PlayQueue.get();
		q.getBus().connect(new Bus.STATE_CHANGED(){
			public void stateChanged(GstObject source, State old, State current, State pending){
				if(State.PLAYING == q.getState()){
					PlayAction.get().putValue(PlayAction.NAME, "Pause");
				} else {
					PlayAction.get().putValue(PlayAction.NAME, "Play");
				}
			}
		});
	}
	
	private static PlayAction singleton = null;
	
	public static PlayAction get(){
		if(null == singleton) singleton = new PlayAction();
		return singleton;
	}
	
	// Action code
	
	public void actionPerformed(ActionEvent e){
		if (PlayQueue.get().getState() == State.PLAYING){
			PlayQueue.get().pause();
		} else {
			PlayQueue.get().play();
		}
	}
	
}
