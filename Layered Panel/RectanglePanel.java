import javax.swing.*;
import java.awt.*;

public class RectanglePanel extends JPanel {

	private int w;
	private int h;
	private int x;
	private int y;
	private Color c = Color.RED;

	public RectanglePanel(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public RectanglePanel(int x, int y, int w, int h, Color c) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
	}

	@Override
	public void paintComponent(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
		g.setColor(c);
		g.fillRect(x, y, w, h);
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}
}