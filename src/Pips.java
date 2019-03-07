/* 
*	Luke - 17426404
* 	Adam - 17364606 
*	Sean - 17469914
*/

import java.awt.Color;
import java.awt.Point;
import javax.swing.*;

public class Pips {
	public JLabel[] label;
	private int numOfPips = 26;
	private boolean[] selected;
	private Point[] offset;
	private int xOffset = 80;
	private int pipWidth = 45;
	private int pipHeight = 300;
	
	public Pips() {

		selected = new boolean[numOfPips];
		offset = new Point[numOfPips];
		
		/*
		assigns an initial offset point by which the pip labels will be placed
		*/

		for(int i = 0; i < numOfPips; i++) {
			if(i < 6)
				offset[i] = new Point(1012 - (i*xOffset), 300);
			else if(i >= 6 && i < 12)
				offset[i] = new Point(454 - ((i-6)*xOffset), 300);
			else if(i >= 12 && i < 18)
				offset[i] = new Point(60 + ((i-12)*xOffset), 0);
			else if(i >= 18 && i < 24)
				offset[i] = new Point(618 + ((i-18)*xOffset), 0);
			else if(i == 24)
				offset[i] = new Point(1120, 0);
			else
				offset[i] = new Point(518, 52);	
		}

		label = new JLabel[numOfPips];
		
		/*
		assigns an unique identity
		and assigns it to an X,Y coordinate on the board based off the position it was assigned above 
		*/
		
		for(int i = 0; i < numOfPips; i++) {
			label[i] = new JLabel();
			label[i].setName(String.valueOf(-1 * (i + 1)));
/*			label[i].setOpaque(true);
			label[i].setForeground(Color.BLACK);
			label[i].setBackground(Color.BLACK);*/
			
			if(i == 24) {
				label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), 116, 645);
			}
			else if(i == 25) {
				label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), 80, 538);
			}
			else {
				label[i].setBounds((int) offset[i].getX(), (int) offset[i].getY(), pipWidth, pipHeight);
			}
			System.out.println(label[i].toString());
		}
	}
	public void setSelected(int i, boolean p) {
		selected[i] = p;
	}
	public boolean getSelected(int i) {
		return selected[i];
	}
}
