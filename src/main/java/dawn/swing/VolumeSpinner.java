package dawn.swing;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

import static java.awt.BorderLayout.*;

import dawn.lowLevel.*;

/**
 * Volume spinner
 * 
 * Unlike most of the components of Dawn, this is not a singleton, as there are two spinners in existance
 * (one in the control panel and the other in the menu). The listener and the model are singletons created
 * the first time #get() is called. However, each time #get() is called a new Spinner is returned using these
 * components.
 */
public class VolumeSpinner extends JPanel{
	
	// Singleton code
	
	private static ChangeListener listener = null;
	private static SpinnerNumberModel model = null;
	
	/**
	 * Returns a new Volume spinner
	 * 
	 * The volume spinner sets and displays the current volume of the Dawn playbin.
	 */
	public static VolumeSpinner get(){
		if(null == listener) init(); // if this is the first spinner to be created initialize the singletons
		return new VolumeSpinner(); //return a new spinner that uses the singletons
	}
	
	/**
	 * Initialize the singletons
	 */
	private static void init(){
		model = new SpinnerNumberModel(PlayQueue.INITIAL_VOLUME, 0, 100, 1);
		listener = new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int value = model.getNumber().intValue();
				PlayQueue.get().setVolumePercent(value);
			}
		};
	}
	
	// Spinner code
	private JSpinner spinner;
	private JLabel label = new JLabel("Volume:");
	
	private VolumeSpinner(){
		super(new BorderLayout());
		
		spinner = new JSpinner(model);

		spinner.addChangeListener(listener);

		add(label, WEST);
		add(spinner, CENTER);
		
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 10)); //border lines up the control with the other menu items
		
	}
	
	
}
