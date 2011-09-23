package dawn.swing;

import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	
	private PrevButton prev = new PrevButton();
	private PlayButton play = new PlayButton();
	private NextButton next = new NextButton();
	
	public ControlPanel(){
		add(prev);
		add(play);
		add(next);
	}
	
}
