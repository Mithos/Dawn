package dawn.swing;

import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEADING;

import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	
	private PrevButton prev = new PrevButton();
	private PlayButton play = new PlayButton();
	private NextButton next = new NextButton();
	
	public ControlPanel(){
		super(new FlowLayout(LEADING));
		add(prev);
		add(play);
		add(next);
	}
	
}
