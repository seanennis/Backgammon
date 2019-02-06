import javax.swing.*;
import javax.accessibility.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class LayeredPanel extends JPanel implements MouseListener {

	private JLayeredPane lp;
	private CheckerLayout positions;
	private Checker[] white_Checker = new Checker[15];
	private Checker[] black_Checker = new Checker[15];

	private BufferedImage BOARD;
	private BufferedImage whiteChecker;
	private BufferedImage blackChecker;
	private JLabel BOARD_LABEL;
	private int numOfPips = 24;
	private int checkerWidth = 60;
	private int checkerHeight = 60;
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
			whiteChecker = ImageIO.read(getClass().getResource("WhiteChecker.png"));
			blackChecker = ImageIO.read(getClass().getResource("BlackChecker.png"));

			BOARD_LABEL = new JLabel(new ImageIcon(BOARD));
			BOARD_LABEL.setBounds(0, 0, 1400, 750);

			for(int i = 0;i < white_Checker.length;i++) {
				white_Checker[i] = new Checker();
				white_Checker[i].label = new JLabel(new ImageIcon(whiteChecker));
				white_Checker[i].label.addMouseListener(this);
				white_Checker[i].label.setText(String.valueOf(i));
				white_Checker[i].setType(1);
				white_Checker[i].setId(i);
				switch(i) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
						white_Checker[i].setPosition(5);
						break;
					case 5:
					case 6:
					case 7:
						white_Checker[i].setPosition(7);
						break;
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
						white_Checker[i].setPosition(12);
						break;
					case 13:
					case 14:
						white_Checker[i].setPosition(23);
						break;
					default:
						System.out.println("LayeredPanel.java: LayeredPanel(): switch(): ERROR default case reached");
						break;
				}
			}
			for(int i = 0;i < black_Checker.length;i++) {
				black_Checker[i] = new Checker();
				black_Checker[i].label = new JLabel(new ImageIcon(blackChecker));
				black_Checker[i].label.addMouseListener(this);
				black_Checker[i].label.setText(String.valueOf(i));
				black_Checker[i].setType(1);
				black_Checker[i].setId(i);
				switch(i) {
					case 0:
					case 1:
						black_Checker[i].setPosition(0);
						break;
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						black_Checker[i].setPosition(11);
						break;
					case 7:
					case 8:
					case 9:
						black_Checker[i].setPosition(16);
						break;
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
						black_Checker[i].setPosition(18);
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

			lp.add(white_Checker[i].label, (Integer) 1);
			lp.add(black_Checker[i].label, (Integer) 1);
		}

		lp.add(BOARD_LABEL, (Integer) 0 );

		add(lp);

	}

	public void initialiseBoard() {

		positions = new CheckerLayout();

		for(int i = 0;i < 15;i++) {

			if(i < 5)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() - (i * 60), checkerWidth, checkerHeight);	
			else if(i >= 5 && i < 8)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() - ((i - 5) * 60), checkerWidth, checkerHeight);
			else if(i >= 8 && i < 13)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + ((i - 8) * 60), checkerWidth, checkerHeight);
			else
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + ((i - 13) * 60), checkerWidth, checkerHeight);

			if(i < 2)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() - (i * 60), checkerWidth, checkerHeight);	
			else if(i >= 2 && i < 7)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() - ((i - 2) * 60), checkerWidth, checkerHeight);
			else if(i >= 7 && i < 10)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + ((i - 7) * 60), checkerWidth, checkerHeight);
			else
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + ((i - 10) * 60), checkerWidth, checkerHeight);

			positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
		}

	}

	public void printSelected() {
		System.out.println("List of Selected values from White Checkers");
		for(int i = 0;i < 15;i++) {
			System.out.println("\t" + white_Checker[i].getSelected());
		}
		System.out.println("");
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

    	int t = Integer.valueOf( ((JLabel)e.getSource()).getText());

    	for(Checker i : white_Checker) {
    		if(i.getSelected())
    		i.setSelected(false);
    	}
    	white_Checker[t].setSelected(true);

    	printSelected();

// will eventually be called but hvent written it yet

//    	updateBoard();
    }
}