import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.* ;
import java.io.*;

public class RollDice {
	private BufferedImage dice1;
	private BufferedImage dice2;
	private BufferedImage dice3;
	private BufferedImage dice4;
	private BufferedImage dice5;
	private BufferedImage dice6;
	private JLabel diceLabel1;
	private JLabel diceLabel2;
	private JLabel diceLabel3;
	private JLabel diceLabel4;
	private JLabel diceLabel5;
	private JLabel diceLabel6;

	public RollDice() {
		
		int rand1 = new Random().nextInt(6);
		int rand2 = new Random().nextInt(6);
		
		try {
			dice1 = ImageIO.read(getClass().getResource("Dice1.png"));
			dice2 = ImageIO.read(getClass().getResource("Dice2.png"));
			dice3 = ImageIO.read(getClass().getResource("Dice3.png"));
			dice4 = ImageIO.read(getClass().getResource("Dice4.png"));
			dice5 = ImageIO.read(getClass().getResource("Dice5.png"));
			dice6 = ImageIO.read(getClass().getResource("Dice6.png"));
		
			switch(rand1) {
			case 0:
				diceLabel1 = new JLabel(new ImageIcon(dice1));
				diceLabel1.setBounds(765, 325, 500, 500);
			case 1:
				diceLabel2 = new JLabel(new ImageIcon(dice2));
				diceLabel2.setBounds(765, 325, 500, 500);
			case 2:
				diceLabel3 = new JLabel(new ImageIcon(dice3));
				diceLabel3.setBounds(765, 325, 500, 500);
			case 3:
				diceLabel4 = new JLabel(new ImageIcon(dice4));
				diceLabel4.setBounds(765, 325, 500, 500);
			case 4:
				diceLabel5 = new JLabel(new ImageIcon(dice5));
				diceLabel5.setBounds(765, 325, 500, 500);
			case 5:
				diceLabel6 = new JLabel(new ImageIcon(dice6));
				diceLabel6.setBounds(765, 325, 500, 500);
			}
			
			switch(rand2) {
			case 0:
				diceLabel1 = new JLabel(new ImageIcon(dice1));
				diceLabel1.setBounds(765, 325, 500, 500);
			case 1:
				diceLabel2 = new JLabel(new ImageIcon(dice2));
				diceLabel2.setBounds(765, 325, 500, 500);
			case 2:
				diceLabel3 = new JLabel(new ImageIcon(dice3));
				diceLabel3.setBounds(765, 325, 500, 500);
			case 3:
				diceLabel4 = new JLabel(new ImageIcon(dice4));
				diceLabel4.setBounds(765, 325, 500, 500);
			case 4:
				diceLabel5 = new JLabel(new ImageIcon(dice5));
				diceLabel5.setBounds(765, 325, 500, 500);
			case 5:
				diceLabel6 = new JLabel(new ImageIcon(dice6));
				diceLabel6.setBounds(765, 325, 500, 500);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
