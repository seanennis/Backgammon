package frame;

import java.awt.Color;
import java.awt.Point;

import javax.swing.*;
public class Pips {
	public JLabel[] label;
	private int numOfPips;
	private Point[] offset;
	private int xOffset = 80;
	private int pipWidth = 45;
	private int pipHeight = 250;
	
	public Pips() {
		offset = new Point[24];
		this.numOfPips = 24;
		for(int i = 0; i < numOfPips; i++) {
			if(i < 6)
				offset[i] = new Point(1012 - (i*xOffset), 312);
			else if(i >= 6 && i < 12)
				offset[i] = new Point(454 - ((i-6)*xOffset), 312);
			else if(i >= 12 && i < 18)
				offset[i] = new Point(60 + ((i-12)*xOffset), 30);
			else
				offset[i] = new Point(618 + ((i-18)*xOffset), 30);
		}
		
		for(int i = 0; i < numOfPips; i++) {
			label = new JLabel[24];
			label[i] = new JLabel();
			label[i].setName(String.valueOf(i));
			label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), pipWidth, pipHeight);
			label[i].setForeground(Color.BLACK);
		}
	}
}
