import java.lang.*;
import java.awt.*;
import java.util.*;

public class CheckerLayout {

	private Point[] initialOffset = new Point[23];
	private int y_offset;
	Stack<Checker>[] pips = new Stack[23];

	public CheckerLayout() {
		for(int i = 0;i < 23;i++) {
			if(i < 6)
				initialOffset[i] = new Point(0,0);
			else if(i >= 6 && i < 12)
				initialOffset[i] = new Point(0,0);
			else if(i >= 12 && i < 18)
				initialOffset[i] = new Point(0,0);
			else
				initialOffset[i] = new Point(0,0);
		}

		y_offset = 50;
	}
}