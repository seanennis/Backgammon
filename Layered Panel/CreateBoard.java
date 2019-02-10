import javax.swing.*;
import java.awt.*;

public class CreateBoard extends JFrame {
	
	private static int WIDTH = 1236;
	private static int HEIGHT = 675;

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