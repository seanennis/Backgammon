import java.awt.Color;
import java.awt.Point;
import javax.swing.*;

public class Pips {
	public JLabel[] label;
	private int numOfPips;
	private boolean[] selected;
	private Point[] offset;
	private int xOffset = 80;
	private int pipWidth = 45;
	private int pipHeight = 300;
	
	public Pips() {

		this.numOfPips = 25;

		selected = new boolean[numOfPips];
		offset = new Point[numOfPips];

		for(int i = 0; i < numOfPips; i++) {
			if(i < 6)
				offset[i] = new Point(1012 - (i*xOffset), 300);
			else if(i >= 6 && i < 12)
				offset[i] = new Point(454 - ((i-6)*xOffset), 300);
			else if(i >= 12 && i < 18)
				offset[i] = new Point(60 + ((i-12)*xOffset), 0);
			else if(i >= 18 && i < 24)
				offset[i] = new Point(618 + ((i-18)*xOffset), 0);
			else
				offset[i] = new Point(1120, 0);
		}

		label = new JLabel[numOfPips];
		
		for(int i = 0; i < numOfPips; i++) {
			label[i] = new JLabel();
			label[i].setName(String.valueOf(-1 * (i + 1)));
/*			label[i].setOpaque(true);
			label[i].setForeground(Color.BLACK);
			label[i].setBackground(Color.BLACK);*/
			if(i == 24)
				label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), 116, 645);
			else
				label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), pipWidth, pipHeight);
		}
	}
	public void setSelected(int i, boolean p) {
		selected[i] = p;
	}
	public boolean getSelected(int i) {
		return selected[i];
	}
}