import javax.swing.*;
//import javax.accessibility.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
//import java.util.*;
import javax.imageio.*;
//import java.lang.*;

public class LayeredPanel extends JPanel implements MouseListener {

	public JLayeredPane lp;
	public CheckerLayout positions;
	public Pips clearPips;
	public int numOfCheckers = 15;
	public Checker[] white_Checker = new Checker[numOfCheckers];
	public Checker[] black_Checker = new Checker[numOfCheckers];
	public BufferedImage BOARD;
	public BufferedImage whiteChecker;
	public BufferedImage blackChecker;
	public JLabel BOARD_LABEL;
	public int numOfPips = 25;
	public int checkerWidth = 50;
	public int checkerHeight = 50;
	public int whiteCheckerOffset;
	public int blackCheckerOffset;

	public LayeredPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		lp = new JLayeredPane();
		lp.setPreferredSize(new Dimension(1236, 645));
		lp.setMinimumSize(new Dimension(1236, 645));
		lp.setMaximumSize(new Dimension(1236, 645));

		try {
			BOARD = ImageIO.read(getClass().getResource("board.png"));
			whiteChecker = ImageIO.read(getClass().getResource("WhiteChecker.png"));
			blackChecker = ImageIO.read(getClass().getResource("BlackChecker.png"));

			BOARD_LABEL = new JLabel(new ImageIcon(BOARD));
			BOARD_LABEL.setBounds(0, 0, 1236, 645);
			
			/*
			Creating the labels that will be the checkers 
			adding listeners to them for the mouse clicks that allow them to move
			their names are their identifiers, <white/black> + <number>
			assigning them a position on the bored
			*/

			for(int i = 0;i < white_Checker.length;i++) {
				white_Checker[i] = new Checker();
				white_Checker[i].label = new JLabel(new ImageIcon(whiteChecker));
//				white_Checker[i].label.addMouseListener(this);

				white_Checker[i].label.setName("white" + String.valueOf(i));

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
//				black_Checker[i].label.addMouseListener(this);

				black_Checker[i].label.setName("black" + String.valueOf(i));

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

			/*
			initialiseBoard() will set the checker labels to a X,Y on the board basedd off the positions assigned to them when they were created
			*/
			
			initialiseBoard();
			
			/*
			updateBoard() moves checkers once theyve been clicked on
			*/

			updateBoard();

		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0;i < numOfPips;i++) {
			lp.add(clearPips.label[i], (Integer) 1);
		}

		for(int i = 0;i < numOfCheckers;i++) {

			lp.add(white_Checker[i].label, (Integer) 2);
			lp.add(black_Checker[i].label, (Integer) 2);
		}

		lp.add(BOARD_LABEL, (Integer) 0 );

		add(lp);

	}

	public void initialiseBoard() {

		positions = new CheckerLayout();
		clearPips = new Pips();

		for(int i = 0;i < numOfPips;i++) {
			clearPips.label[i].addMouseListener(this);
		}
		
		/*
		sets the positions of the checkers on the Board using the coordinates stored in the CheckerLayout class;
		*/

		for(int i = 0;i < numOfCheckers;i++) {

			if(i < 5)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() - (i * checkerWidth), checkerWidth, checkerHeight);	
			else if(i >= 5 && i < 8)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() - ((i - 5) * checkerWidth), checkerWidth, checkerHeight);
			else if(i >= 8 && i < 13)
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + ((i - 8) * checkerWidth), checkerWidth, checkerHeight);
			else
				white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + ((i - 13) * checkerWidth), checkerWidth, checkerHeight);

			if(i < 2)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() - (i * checkerWidth), checkerWidth, checkerHeight);	
			else if(i >= 2 && i < 7)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() - ((i - 2) * checkerWidth), checkerWidth, checkerHeight);
			else if(i >= 7 && i < 10)
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + ((i - 7) * checkerWidth), checkerWidth, checkerHeight);
			else
				black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + ((i - 10) * checkerWidth), checkerWidth, checkerHeight);

			positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
			positions.pips.get(black_Checker[i].getPosition()).add(black_Checker[i]);
		}

	}
	public void updateBoard() {
		
		/*
		checks that a pip have been clicked on
		*/

		int newPosition = -1;
		int additionalOffset = 0;
		int numOfCheckersOnPip = 0;
		boolean pipSelected = false;

		for(int i = 0;i < numOfPips;i++) {
			if(clearPips.getSelected(i)) {
				newPosition = i;
				pipSelected = true;
			}
		}
		
		/*
		if a pip have been clicked on then it will check if a checker was clicked on
		if a checker has also be clicked it'll check which pip it was and sets the position of the selected checker to that of the selected pip
		*/
		
		if(pipSelected) {	
			for(int i = 0;i < numOfCheckers;i++) {
				if(white_Checker[i].getSelected()) {
					
					positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
					white_Checker[i].setPosition(newPosition);
					
					numOfCheckersOnPip = positions.pips.get(white_Checker[i].getPosition()).size();
					
					if(numOfCheckersOnPip < 5) {
						if(newPosition < 12) {
							additionalOffset = -1 * numOfCheckersOnPip * 50;
						}
						else {
							additionalOffset = numOfCheckersOnPip * 50;
						}
						lp.setLayer(white_Checker[i].label, (Integer) 2);
					}
					else {
						if(newPosition < 12)
							additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
						else
							additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
						lp.setLayer(white_Checker[i].label, (Integer) 3);
					}
					positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
					white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
					
				} else if(black_Checker[i].getSelected()) {
					
					positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
					black_Checker[i].setPosition(newPosition);
					
					numOfCheckersOnPip = positions.pips.get(black_Checker[i].getPosition()).size();
					
					if(numOfCheckersOnPip < 5) {
						if(newPosition < 12) {
							System.out.println("pipNum < 12");
							additionalOffset = -1 * numOfCheckersOnPip * 50;
						}
						else
							additionalOffset = numOfCheckersOnPip * 50;
						lp.setLayer(black_Checker[i].label, (Integer) 2);
					}
					else {
						if(newPosition < 12)
							additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
						else
							additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
						lp.setLayer(black_Checker[i].label, (Integer) 3);
					}
					positions.pips.get(black_Checker[i].getPosition()).add(black_Checker[i]);
					black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
				}
			}
		}

	}
	public void printSelected() {
		System.out.println("List of Selected values from Pips");
		for(int i = 0;i < numOfPips;i++) {
			System.out.println("\t" + clearPips.getSelected(i));
		}
		System.out.println("");
	}

	public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){} 
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

    	/*
		identifies the selected label based off the label name 
    	*/
    	
		JLabel temp = (JLabel)e.getSource();
		String labelName = temp.getName();
    	int t; 

    	if(temp.getName().length() == 6)
    		t = Integer.valueOf(temp.getName().substring(5, 6)); 
    	else if(temp.getName().length() == 7)
    		t = Integer.valueOf(temp.getName().substring(5, 7));
    	else
    		t = Integer.valueOf(temp.getName());

    	/*
		pips have a negative id 
		if a pip was clicked them it will identify which one and call update function
		if a checker is clicked it'll identify if it is black or white and then deselect any other checker and select the checker that was just clicked
    	*/
    	
    	if(t < 0) {

    		int parsedInt = -1 * (t + 1);

    		for(int i = 0;i < numOfPips;i++) {
    			if( clearPips.getSelected(i))
    				clearPips.setSelected(i, false); 	
    		}
    		clearPips.setSelected(parsedInt, true);
    		updateBoard();

    	} else {
    		if(labelName.substring(0, 5).equals("white")) {

    			for(Checker i : white_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	for(Checker i : black_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	white_Checker[t].setSelected(true);

    		} else if(labelName.substring(0, 5).equals("black")){

    			for(Checker i : black_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	for(Checker i : white_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	black_Checker[t].setSelected(true);

    		}
    	}
    }
}