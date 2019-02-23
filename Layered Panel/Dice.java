import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.* ;
import java.io.*;

public class Dice {
	private BufferedImage[] dice = new BufferedImage[6];
	private JLabel[] diceLabel = new JLabel[6];

	public Dice() {
		try {
			dice[0] = ImageIO.read(getClass().getResource("Dice1.png"));
			dice[1] = ImageIO.read(getClass().getResource("Dice2.png"));
			dice[2] = ImageIO.read(getClass().getResource("Dice3.png"));
			dice[3] = ImageIO.read(getClass().getResource("Dice4.png"));
			dice[4] = ImageIO.read(getClass().getResource("Dice5.png"));
			dice[5] = ImageIO.read(getClass().getResource("Dice6.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void rollDice() {	
		for(int i = 0; i < 6; i++) {
			diceLabel[i] = new JLabel(new ImageIcon(dice[i]));
		}
		
		int rand1 = new Random().nextInt(6);
		int rand2 = new Random().nextInt(6);
		
		diceLabel[rand1].setBounds(765, 325, 80, 80);
		diceLabel[rand2].setBounds(840, 325, 80, 80);
	}
}
