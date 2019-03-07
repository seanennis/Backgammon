/* 
*	Luke - 17426404
* 	Adam - 17364606 
*	Sean - 17469914
*/

//import java.lang.*;
import java.awt.*;
import java.util.*;

public class CheckerLayout {

	private int numOfPips = 26;
	private Point[] initialOffset = new Point[numOfPips];
	private int gapOfPips = 80;
	private int halfWidth = 25;
	public ArrayList<ArrayList<Checker>> pips = new ArrayList<ArrayList<Checker>>(numOfPips); 

	public CheckerLayout() {

		for(int i = 0;i < numOfPips;i++) {
			ArrayList<Checker> temp = new ArrayList<Checker>();
			pips.add(i, temp); 
		}


		for(int i = 0;i < numOfPips;i++) {
			if(i < 6)
				initialOffset[i] = new Point(1035 - (i * gapOfPips) - halfWidth, 545);
			else if(i >= 6 && i < 12)
				initialOffset[i] = new Point(478 - ((i - 6) * gapOfPips) - halfWidth, 545);
			else if(i >= 12 && i < 18)
				initialOffset[i] = new Point(82 + ((i - 12) * gapOfPips) - halfWidth, 55);
			else if(i >= 18 && i < 24)
				initialOffset[i] = new Point(640 + ((i - 18) * gapOfPips) - halfWidth, 55);
			else if(i == 24)
				initialOffset[i] = new Point(1178 - halfWidth, 0);
			else 
				initialOffset[i] = new Point(560 - halfWidth, 52);
		}
	}

	public Point[] getInitialOffset() {
		return initialOffset;
	}
}