import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LayeredPanel extends JPanel implements MouseListener {

	private JLayeredPane lp;
	private CheckerLayout positions;
	private Checker[] WHITE_CHECKER_LABEL = new Checker[15];
	private Checker[] BLACK_CHECKER_LABEL = new Checker[15];

	private BufferedImage BOARD;
	private BufferedImage WHITE_CHECKER;
	private BufferedImage BLACK_CHECKER;
	private JLabel BOARD_LABEL;
	private int checkerWidth = 100;
	private int checkerHeight = 100;
	private int whiteCheckerOffset;
	private int blackCheckerOffset;

	public LayeredPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		lp = new JLayeredPane();
		lp.setPreferredSize(new Dimension(1400, 750));
		lp.setMinimumSize(new Dimension(1400, 750));
		lp.setMaximumSize(new Dimension(1400, 750));

		try {
			BOARD = ImageIO.read(getClass().getResource("board.jpg"));
			WHITE_CHECKER = ImageIO.read(getClass().getResource("WhiteChecker.png"));
			BLACK_CHECKER = ImageIO.read(getClass().getResource("BlackChecker.png"));

			BOARD_LABEL = new JLabel(new ImageIcon(BOARD));
			BOARD_LABEL.setBounds(0, 0, 1400, 750);

			for(int i = 0;i < WHITE_CHECKER_LABEL.length;i++) {
				WHITE_CHECKER_LABEL[i].setImage(new JLabel(new ImageIcon(WHITE_CHECKER)));
				WHITE_CHECKER_LABEL[i].setType(1);
				WHITE_CHECKER_LABEL[i].setId(i);
				switch(i) {
					case 0:
					case 1:
						WHITE_CHECKER_LABEL[i].setPosition(23);
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						WHITE_CHECKER_LABEL[i].setPosition(12);
						break;
					case 7:
					case 8:
					case 9:
						WHITE_CHECKER_LABEL[i].setPosition(7);
						break;
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
						WHITE_CHECKER_LABEL[i].setPosition(5);
					default:
						break;
				}
			}
			for(int i = 0;i < BLACK_CHECKER_LABEL.length;i++) {
				BLACK_CHECKER_LABEL[i].setImage(new JLabel(new ImageIcon(BLACK_CHECKER)));
				BLACK_CHECKER_LABEL[i].setType(1);
				BLACK_CHECKER_LABEL[i].setId(i);
				switch(i) {
					case 0:
					case 1:
						BLACK_CHECKER_LABEL[i].setPosition(0);
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						BLACK_CHECKER_LABEL[i].setPosition(11);
						break;
					case 7:
					case 8:
					case 9:
						BLACK_CHECKER_LABEL[i].setPosition(16);
						break;
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
						BLACK_CHECKER_LABEL[i].setPosition(18);
					default:
						break;
				}
			}

			initialiseBoard();

			updateBoard();

		} catch(IOException e) {
			e.printStackTrace();
		}

		for(int i = 0;i < 15;i++) {
			lp.add(WHITE_CHECKER_LABEL[i].getImage(), new Integer(1));
			lp.add(BLACK_CHECKER_LABEL[i].getImage(), new Integer(1));
		}

		add(lp);

	}

	public void initialiseBoard() {

		positions = new CheckerLayout();


	}
	public void updateBoard() {

	}

	public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){} 
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }
}