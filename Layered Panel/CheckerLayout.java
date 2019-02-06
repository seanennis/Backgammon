import java.lang.*;
import java.awt.*;
import java.util.*;

public class CheckerLayout {

	private Point[] initialOffset = new Point[24];
	private int gapOfPips = 100;
	private int halfWidth = 30;
//	public ArrayList<Stack<Checker>> pips = new ArrayList<Stack<Checker>>(24);
//	public int[][] pips = new int[24][50];
	public ArrayList<ArrayList<Checker>> pips = new ArrayList<ArrayList<Checker>>(24); 

	public CheckerLayout() {

		for(int i = 0;i < 24;i++) {
			ArrayList<Checker> temp = new ArrayList<Checker>();
			pips.add(i, temp); 
		}


		for(int i = 0;i < 24;i++) {
			if(i < 6)
				initialOffset[i] = new Point(1300 - (i * gapOfPips) - halfWidth, 650);
			else if(i >= 6 && i < 12)
				initialOffset[i] = new Point(600 - ((i - 6) * gapOfPips) - halfWidth, 650);
			else if(i >= 12 && i < 18)
				initialOffset[i] = new Point(100 + ((i - 12) * gapOfPips) - halfWidth, 40);
			else
				initialOffset[i] = new Point(800 + ((i - 18) * gapOfPips) - halfWidth, 40);
		}
	}

	public Point[] getInitialOffset() {
		return initialOffset;
	}
}