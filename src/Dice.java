import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.* ;
import java.io.*;

public class Dice {
	private BufferedImage[] diceSide;
	private JLabel[] diceLabel;
	private Random random = new Random();
	private int lastRoll;

	public Dice() {
		
		lastRoll = 0;
		diceSide = new BufferedImage[6];
		diceLabel = new JLabel[6];
		
		try {
			diceSide[0] = ImageIO.read(getClass().getResource("Dice1.png"));
			diceSide[1] = ImageIO.read(getClass().getResource("Dice2.png"));
			diceSide[2] = ImageIO.read(getClass().getResource("Dice3.png"));
			diceSide[3] = ImageIO.read(getClass().getResource("Dice4.png"));
			diceSide[4] = ImageIO.read(getClass().getResource("Dice5.png"));
			diceSide[5] = ImageIO.read(getClass().getResource("Dice6.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0;i < 6;i++) {
			diceLabel[i] = new JLabel(new ImageIcon(diceSide[i]));
		}
		
	}
	
	public int getLastRoll() {
		return this.lastRoll + 1;
	}
	public void setLastRoll(int i) {
		this.lastRoll = i - 1;
	}
		
	public int roll() {	
		this.lastRoll = random.nextInt(6);
		return this.lastRoll;
	}
}
