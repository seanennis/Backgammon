import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.* ;
import java.io.*;

public class Dice {
	private Random random = new Random();
	private int lastRoll;
	private boolean equalDice = false; // set to true when dice values match, allowing you to use each dice twice

	public Dice() {		
		lastRoll = 0;
	}
	
	public int getLastRoll() {
		return this.lastRoll + 1;
	}
	public void setLastRoll(int i) {
		if(equalDice == true)
			equalDice = false;
		else
			this.lastRoll = i - 1;
	}
	
	public void setEqualDice() {
		this.equalDice = true;
	}
		
	public int roll() {	
		this.lastRoll = random.nextInt(6);
		return this.lastRoll;
	}
}
