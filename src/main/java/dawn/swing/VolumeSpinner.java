package dawn.swing;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

import static java.awt.BorderLayout.*;

import dawn.lowLevel.*;

public class VolumeSpinner extends JPanel{
	
	// Singleton code
	
	private static VolumeSpinner singleton = null;
	public static VolumeSpinner get(){
		if(null == singleton) singleton = new VolumeSpinner();
		return singleton;
	}
	
	// Spinner code
	
	private SpinnerNumberModel model;
	private JSpinner spinner;
	private JLabel label;
	private ChangeListener listener;
	
	private VolumeSpinner(){
		super(new BorderLayout());
		
		// pieces
		model = new SpinnerNumberModel(PlayQueue.INITIAL_VOLUME, 0, 100, 1);
		spinner = new JSpinner(model);
		label = new JLabel("Volume");
		
		// Change handling
		listener = new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				int value = model.getNumber().intValue();
				PlayQueue.get().setVolumePercent(value);
			}
		};
		spinner.addChangeListener(listener);
		
		// assembly
		//spinner.setPreferredSize(new Dimension(100, spinner.getPreferredSize().height)); //set up size
		add(label, WEST);
		add(spinner, CENTER);
		
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 10)); //border lines up the control with the other menu items
		
	}
	
	
}
