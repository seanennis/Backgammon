import javax.swing.*;
import java.awt.*;

public class CreateBoard extends JFrame {
	
	private static int WIDTH = 1120;
	private static int HEIGHT = 630;

	public CreateBoard (){
		setSize(WIDTH,HEIGHT);
		createComponents();
	}

	public void createComponents() {
		JPanel panel = new JPanel();

//		LayeredPaneDemo lp = new LayeredPaneDemo();

		LayeredPanel p = new LayeredPanel();

//		panel.add(lp);
		panel.add(p);

		add(panel);
	}

}