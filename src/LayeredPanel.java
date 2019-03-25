/* 
*	Luke - 17426404
* 	Adam - 17364606 
*	Sean - 17469914
*/

import javax.swing.*;
//import javax.accessibility.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

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
	public int numOfPips = 28;
	public int checkerWidth = 50;
	public int checkerHeight = 50;
	public int whiteCheckerOffset;
	public int blackCheckerOffset;
	public JLabel[] pipNum;
	public JPanel numPanel;
	public int xOffset = 80;
	private int playerTurn;

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

				black_Checker[i].setType(0);
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

			updateChecker();

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
	
	public void updateHit() {
		
		for(int i = 0;i < numOfCheckers;i++) {
			positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
			positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
		}
		
		for(int i = 0;i < numOfCheckers;i++) {
			positions.pips.get(black_Checker[i].getPosition()).add(black_Checker[i]);
			positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
			
			black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY(), checkerWidth, checkerHeight);
			white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY(), checkerWidth, checkerHeight);
			
		}
		
	}
	
	public void updateBoard() {
		
		int additionalOffset = 0;
		int numOfCheckersOnPip = 0;
		
		for(int i = 0;i < numOfCheckers;i++) {
			positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
			positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
		}
		
		for(int i = 0;i < numOfCheckers;i++) {
			
			numOfCheckersOnPip = positions.pips.get(black_Checker[i].getPosition()).size();
			
			if(black_Checker[i].getPosition() == 24) {
				additionalOffset = numOfCheckersOnPip * 10;
				lp.setLayer(black_Checker[i].label, (Integer) (numOfCheckersOnPip + 2) );
			}
			else {
				if(numOfCheckersOnPip < 5) {
					if(black_Checker[i].getPosition() < 12 || black_Checker[i].getPosition() == 25 || black_Checker[i].getPosition() == 27) {
						additionalOffset = -1 * numOfCheckersOnPip * 50;
					}
					else {
						additionalOffset = numOfCheckersOnPip * 50;
					}
					lp.setLayer(black_Checker[i].label, (Integer) 2);
				}
				else {
					if(black_Checker[i].getPosition() < 12 || black_Checker[i].getPosition() == 25 || black_Checker[i].getPosition() == 27)
						additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
					else
						additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
					lp.setLayer(black_Checker[i].label, (Integer) 3);
				}
			}
			positions.pips.get(black_Checker[i].getPosition()).add(black_Checker[i]);
			black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
		}
		
		for(int i = 0;i < numOfCheckers;i++) {
			
			numOfCheckersOnPip = positions.pips.get(white_Checker[i].getPosition()).size();
			
			if(white_Checker[i].getPosition() == 25) {
				additionalOffset = -1 * numOfCheckersOnPip * 10;
				lp.setLayer(white_Checker[i].label, (Integer) (numOfCheckersOnPip + 2) );
			}
			else {
				if(numOfCheckersOnPip < 5) {
					if(white_Checker[i].getPosition() < 12 || white_Checker[i].getPosition() == 25 || white_Checker[i].getPosition() == 27) {
						additionalOffset = -1 * numOfCheckersOnPip * 50;
					}
					else {
						additionalOffset = numOfCheckersOnPip * 50;
					}
					lp.setLayer(white_Checker[i].label, (Integer) 2);
				}
				else {
					if(white_Checker[i].getPosition() < 12 || white_Checker[i].getPosition() == 25 || white_Checker[i].getPosition() == 27)
						additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
					else
						additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
					lp.setLayer(white_Checker[i].label, (Integer) 3);
				}
			}			
			positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
			white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
			
		}
	}
	
	public void cheatCommand()
	{
		for(int i = 0;i < numOfCheckers;i++) {
			positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
			positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
		}
		
		for(int i = 0;i < white_Checker.length;i++) {
	
			switch(i) {
				case 0:
				case 1:
					white_Checker[i].setPosition(0);
					break;
				case 2:
				case 3:
					white_Checker[i].setPosition(1);
					break;
				case 4:
				case 5:
					white_Checker[i].setPosition(2);
					break;
				case 6:
				case 7:
					white_Checker[i].setPosition(3);
					break;
				case 8:
				case 9:
					white_Checker[i].setPosition(4);
					break;
				case 10:
				case 11:
					white_Checker[i].setPosition(25);
					break;
				case 12:
				case 13:
				case 14:
					white_Checker[i].setPosition(26);
					break;
				default:
					System.out.println("LayeredPanel.java: LayeredPanel(): switch(): ERROR default case reached: white_Checker");
					break;
			}
			
		}
		for(int i = 0;i < black_Checker.length;i++) {
			switch(i) {
				case 0:
				case 1:
				case 2:
					black_Checker[i].setPosition(20);
					break;
				case 3:
				case 4:
				case 5:
					black_Checker[i].setPosition(21);
					break;
				case 6:				
				case 7:
				case 8:
					black_Checker[i].setPosition(23);
					break;
				case 9:		
				case 10:
				case 11:
					black_Checker[i].setPosition(24);
					break;
				case 12:
				case 13:
				case 14:
					black_Checker[i].setPosition(27);
					break;
				default:
					System.out.println("LayeredPanel.java: LayeredPanel(): switch(): ERROR default case reached: black_Checker: " + i);
					break;
			}	
		}
		
		updateBoard();
	}
	
	public void endGameCommand()
	{
		for(int i = 0;i < numOfCheckers;i++) {
			positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
			positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
		}
		
		for(int i = 0;i < white_Checker.length;i++) {
	
			switch(i) {
				case 0:
				case 1:
					white_Checker[i].setPosition(0);
					break;
				case 2:
				case 3:
					white_Checker[i].setPosition(1);
					break;
				case 4:
				case 5:
					white_Checker[i].setPosition(2);
					break;
				case 6:
				case 7:
					white_Checker[i].setPosition(3);
					break;
				case 8:
				case 9:
					white_Checker[i].setPosition(4);
					break;
				case 10:
				case 11:
					white_Checker[i].setPosition(25);
					break;
				case 12:
				case 13:
				case 14:
					white_Checker[i].setPosition(0);
					break;
				default:
					System.out.println("LayeredPanel.java: LayeredPanel(): switch(): ERROR default case reached: white_Checker");
					break;
			}
			
		}
		for(int i = 0;i < black_Checker.length;i++) {
			switch(i) {
				case 0:
				case 1:
				case 2:
					black_Checker[i].setPosition(20);
					break;
				case 3:
				case 4:
				case 5:
					black_Checker[i].setPosition(21);
					break;
				case 6:				
				case 7:
				case 8:
					black_Checker[i].setPosition(23);
					break;
				case 9:		
				case 10:
				case 11:
					black_Checker[i].setPosition(24);
					break;
				case 12:
				case 13:
				case 14:
					black_Checker[i].setPosition(20);
					break;
				default:
					System.out.println("LayeredPanel.java: LayeredPanel(): switch(): ERROR default case reached: black_Checker: " + i);
					break;
			}	
		}
		
		updateBoard();
	}
	
	public boolean enterCheckers(int selectedCheckerId) {
		
		boolean checkerOnBar = false;
		
		if(playerTurn == 1) {
			for(int i = 0;i < numOfCheckers;i++) {
				if(black_Checker[i].getPosition() == 27) {
					if(black_Checker[i].getId() == selectedCheckerId) {
						checkerOnBar = false;
						break;
					}
					else {	
//						System.out.println("Black Checker Id @ position 27: " + black_Checker[i].getId());
//						System.out.println("Selected Checker ID: " + selectedCheckerId);
						checkerOnBar = true;
					}
				}
			}
		}
		else if(playerTurn == 2) {
			for(int i = 0;i < numOfCheckers;i++) {
				if(white_Checker[i].getPosition() == 26) {
					if(white_Checker[i].getId() == selectedCheckerId) {
						checkerOnBar = false;
						break;
					}
					else {
//						System.out.println("White Checker Id @ position 26: " + white_Checker[i].getId());
//						System.out.println("Selected Checker ID: " + selectedCheckerId);
						checkerOnBar = true;
					}
				}
			}
		}
		return checkerOnBar;
	}
	
	public boolean legalToEnter(int diceValueOne, int diceValueTwo) {
		
		boolean legalToEnter = false;
		
		if(playerTurn == 1) {
			if(validPip(diceValueOne - 1, 0, true).getValid() || validPip(diceValueTwo - 1, 0, true).getValid()) {
				legalToEnter = true;
			}
			else if(validPip((diceValueOne + diceValueTwo - 1), 0, true).getValid()) {
				if((diceValueOne + diceValueTwo - 1) < 6)
					legalToEnter = true;
			}
		}
		else if(playerTurn == 2) {
			if( validPip(23 - (diceValueOne - 1), 1, true).getValid() || validPip(23 - (diceValueTwo - 1), 1, true).getValid()) {
				legalToEnter = true;
			}
			else if(validPip(23 - ((diceValueOne + diceValueTwo - 1)), 1, true).getValid()) {
					if((diceValueOne + diceValueTwo - 1) < 6)
						legalToEnter = true;
			}
		}
		
		return legalToEnter;
	}
	
	public ArrayList<legalTurn> listLegalMoves(int diceValueOne, int diceValueTwo) {
		
		Checker tempChecker = new Checker();
		Checker nestedTempChecker = new Checker();
		legalTurn tempLegalTurn = new legalTurn();
		validPip tempValidPip = new validPip();
		
		boolean checkersToEnter = false;
		
		ArrayList<legalTurn> list = new ArrayList<legalTurn>();
		
//		for(int i = 0;i < (numOfPips - 4);i++) {
		if(playerTurn == 1) {
			tempLegalTurn.setCheckerId(0);
			if(enterCheckers(-1)) {
				for(int i = 0;i < positions.pips.get(27).size();i++) {
					checkersToEnter = true;
					if(i == 0) {
						tempValidPip = validPip(diceValueOne, 0, true);
						if(tempValidPip.getValid()) {
							tempLegalTurn.setMove(0, new Move(27, diceValueOne));
							tempLegalTurn.setType(0, tempValidPip.getType());
							list.add(new legalTurn(tempLegalTurn));
						}
					}
					else if(i == 1) {
						tempValidPip = validPip(diceValueTwo, 0, true);
						if(tempValidPip.getValid()) {
							if(tempLegalTurn.getMove(0).getStart() == -1) {
								tempLegalTurn.setMove(0, new Move(27, diceValueTwo));
								tempLegalTurn.setType(0, tempValidPip.getType());
							}
							else {
								tempLegalTurn.setMove(1, new Move(27, diceValueTwo));
								tempLegalTurn.setType(1, tempValidPip.getType());
							}
							list.add(new legalTurn(tempLegalTurn));
						}
					}
				}
				if(enterCheckers(-1))
					checkersToEnter = true;
			}
			if(!checkersToEnter) {
				for(int i = 0;i < (numOfPips - 4);i++) {
					
					if(!positions.pips.get(i).isEmpty()) {
						tempChecker = positions.pips.get(i).get(positions.pips.get(i).size() - 1);
						if(tempChecker.getType() == 0) { // if pip contains checker that belongs to the current player
							
	//						System.out.println("This should print 3 times");
								
							tempValidPip = validPip(tempChecker.getPosition() + diceValueOne, 0, true);
							
							if(tempValidPip.getValid()) { // if the 
															
								tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() + diceValueOne));
								tempLegalTurn.setType(0, tempValidPip.getType());
								
								tempValidPip = validPip(tempChecker.getPosition() + diceValueOne + diceValueTwo, 0, true);
								if(tempValidPip.getValid()) {
									
									tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() + diceValueOne, tempChecker.getPosition() + diceValueOne + diceValueTwo));
									tempLegalTurn.setType(1, tempValidPip.getType());
									
									list.add(new legalTurn(tempLegalTurn));
								}
//								else {
									for(int j = (i + 1);j < (numOfPips - 4);j++) {
										if(!positions.pips.get(j).isEmpty()) {
											nestedTempChecker = positions.pips.get(j).get(positions.pips.get(j).size() - 1);
											if(nestedTempChecker.getType() == 0) {
												
												tempValidPip = validPip(nestedTempChecker.getPosition() + diceValueTwo, 0, true);
												if(tempValidPip.getValid()) {
													tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), nestedTempChecker.getPosition() + diceValueTwo));
													tempLegalTurn.setType(1, tempValidPip.getType());
													
													list.add(new legalTurn(tempLegalTurn));
												}
											}
										}
									}
//								}
							}
							// code for second dice value then the first 
							
							tempValidPip = validPip(tempChecker.getPosition() + diceValueTwo, 0, true);
							
							if(tempValidPip.getValid()) { // if the 
								
								tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() + diceValueTwo));
								tempLegalTurn.setType(0, tempValidPip.getType());
								
								tempValidPip = validPip(tempChecker.getPosition() + diceValueTwo + diceValueOne, 0, true);
								if(tempValidPip.getValid()) {
									
									tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() + diceValueTwo, tempChecker.getPosition() + diceValueTwo + diceValueOne));
									tempLegalTurn.setType(1, tempValidPip.getType());
									
									list.add(new legalTurn(tempLegalTurn));
								}
//								else {
									for(int j = (i + 1);j < (numOfPips - 4);j++) {
										if(!positions.pips.get(j).isEmpty()) {
											nestedTempChecker = positions.pips.get(j).get(positions.pips.get(j).size() - 1);
											if(nestedTempChecker.getType() == 0) {
												
												tempValidPip = validPip(nestedTempChecker.getPosition() + diceValueOne, 0, true);
												if(tempValidPip.getValid()) {
													tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), nestedTempChecker.getPosition() + diceValueOne));
													tempLegalTurn.setType(1, tempValidPip.getType());
													
													list.add(new legalTurn(tempLegalTurn));
												}
											}
										}
									}
//								}
							}
						}
					}
				}
			}
		}
			else if(playerTurn == 2) {
				tempLegalTurn.setCheckerId(1);
				if(enterCheckers(-1)) {
					for(int i = 0;i < positions.pips.get(26).size();i++) {
						checkersToEnter = true;
						if(i == 0) {
							tempValidPip = validPip(24 - diceValueOne, 1, true);
							if(tempValidPip.getValid()) {
								tempLegalTurn.setMove(0, new Move(26, 24- diceValueOne));
								tempLegalTurn.setType(0, tempValidPip.getType());
								list.add(new legalTurn(tempLegalTurn));
							}
						}
						else if(i == 1) {
							System.out.println("Test");
							tempValidPip = validPip(24 - diceValueTwo, 1, true);
							if(tempValidPip.getValid()) {
								System.out.println("Test");
								tempLegalTurn.setMove(1, new Move(26, 24 - diceValueTwo));
								tempLegalTurn.setType(1, tempValidPip.getType());
								list.add(new legalTurn(tempLegalTurn));
							}
						}
					}
					if(enterCheckers(-1))
						checkersToEnter = true;
				}
				if(!checkersToEnter) {
					for(int i = (numOfPips - 5);i >= 0;i--) {
						
						if(!positions.pips.get(i).isEmpty()) {
							tempChecker = positions.pips.get(i).get(positions.pips.get(i).size() - 1);
							if(tempChecker.getType() == 1) { // if pip contains checker that belongs to the current player
								
								//case for bearing off
								if(tempChecker.getPosition() - diceValueOne == -1) {
									tempValidPip = validPip(25, 1, true);
								}
								else {
									tempValidPip = validPip(tempChecker.getPosition() - diceValueOne, 1, true);
								}
	//							tempValidPip = validPip(tempChecker.getPosition() - diceValueOne, 1);
								
								if(tempValidPip.getValid()) { // if the 
														
									//case for bearing off
									if(tempChecker.getPosition() - diceValueOne == -1) {
										tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), 25));
									}
									else {
										tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueOne));
									}
	//								tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueOne));								
									
									tempLegalTurn.setType(0, tempValidPip.getType());
									
									//case for bearing off
									if(tempChecker.getPosition() - diceValueOne - diceValueTwo == -1) {
										tempValidPip = validPip(25, 1, true);
									}
									else {
										tempValidPip = validPip(tempChecker.getPosition() - diceValueOne - diceValueTwo, 1, true);
									}
	//								tempValidPip = validPip(tempChecker.getPosition() - diceValueOne - diceValueTwo, 1);
									
									if(tempValidPip.getValid()) {
										
										//case for bearing off
										if(tempChecker.getPosition() - diceValueOne - diceValueTwo == -1) {
											tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueOne, 25));
										}
										else {
											tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueOne, tempChecker.getPosition() - diceValueOne - diceValueTwo));
										}
	//									tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueOne, tempChecker.getPosition() - diceValueOne - diceValueTwo));
										
										tempLegalTurn.setType(1, tempValidPip.getType());
										
										list.add(new legalTurn(tempLegalTurn));
									}
	//								else {
										for(int j = (i - 1);j >= 0;j--) {
											if(!positions.pips.get(j).isEmpty()) {
												nestedTempChecker = positions.pips.get(j).get(positions.pips.get(j).size() - 1);
												if(nestedTempChecker.getType() == 1) {
													
													//case for bearing off
													if(nestedTempChecker.getPosition() - diceValueTwo == -1) {
														tempValidPip = validPip(25, 1, true);
													}
													else {
														tempValidPip = validPip(nestedTempChecker.getPosition() - diceValueTwo, 1, true);
													}
	//												tempValidPip = validPip(tempChecker.getPosition() - diceValueTwo, 1);
													
													if(tempValidPip.getValid()) {
														
														//case for bearing off
														if(nestedTempChecker.getPosition() - diceValueTwo == -1) {
															tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), 25));
														}
														else {
															tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), nestedTempChecker.getPosition() - diceValueTwo));
														}
	//													tempLegalTurn.setMove(1, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueTwo));
														
														tempLegalTurn.setType(1, tempValidPip.getType());
														
														list.add(new legalTurn(tempLegalTurn));
													}
												}
											}
										}
	//								}
								}
								// code for second dice value then the first 
								
								//case for bearing off
								if(tempChecker.getPosition() - diceValueTwo == -1) {
									tempValidPip = validPip(25, 1, true);
								}
								else {
									tempValidPip = validPip(tempChecker.getPosition() - diceValueTwo, 1, true);
								}
	//							tempValidPip = validPip(tempChecker.getPosition() - diceValueTwo, 1);
								
								if(tempValidPip.getValid()) { // if the 
									
									//case for bearing off
									if(tempChecker.getPosition() - diceValueTwo == -1) {
										tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueTwo));
									}
									else {
										tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueTwo));
									}
									
	//								tempLegalTurn.setMove(0, new Move(tempChecker.getPosition(), tempChecker.getPosition() - diceValueTwo));
									
									tempLegalTurn.setType(0, tempValidPip.getType());
									
									//case for bearing off
									if(tempChecker.getPosition() - diceValueTwo - diceValueOne == -1) {
										tempValidPip = validPip(25, 1, true);
									}
									else {
										tempValidPip = validPip(tempChecker.getPosition() - diceValueTwo - diceValueOne, 1, true);
									}
	//								tempValidPip = validPip(tempChecker.getPosition() - diceValueTwo - diceValueOne, 1);
									
									if(tempValidPip.getValid()) {
										
										//case for bearing off
										if(tempChecker.getPosition() - diceValueTwo - diceValueOne == -1) {
											tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueTwo, 25));
										}
										else {
											tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueTwo, tempChecker.getPosition() - diceValueTwo - diceValueOne));
										}
	//									tempLegalTurn.setMove(1, new Move(tempChecker.getPosition() - diceValueTwo, tempChecker.getPosition() - diceValueTwo - diceValueOne));
										
										tempLegalTurn.setType(1, tempValidPip.getType());
										
										list.add(new legalTurn(tempLegalTurn));
									}
	//								else {
										for(int j = (i - 1);j >= 0 ;j--) {
											if(!positions.pips.get(j).isEmpty()) {
												nestedTempChecker = positions.pips.get(j).get(positions.pips.get(j).size() - 1);
												if(nestedTempChecker.getType() == 1) {
													
													//case for bearing off
													if(nestedTempChecker.getPosition() - diceValueOne == -1) {
														tempValidPip = validPip(25, 1, true);
													}
													else {
														tempValidPip = validPip(nestedTempChecker.getPosition() - diceValueOne, 1, true);
													}
	//												tempValidPip = validPip(tempChecker.getPosition() - diceValueOne, 1);
													
													if(tempValidPip.getValid()) {
														
														//case for bearing off
														if(nestedTempChecker.getPosition() - diceValueOne == -1) {
															tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), 25));
														}
														else {
															tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), nestedTempChecker.getPosition() - diceValueOne));
														}
														tempLegalTurn.setMove(1, new Move(nestedTempChecker.getPosition(), nestedTempChecker.getPosition() - diceValueOne));
														tempLegalTurn.setType(1, tempValidPip.getType());
														
														list.add(new legalTurn(tempLegalTurn));
													}
												}
											}
										}
	//								}
								}
							}
						}
					}
			}
			}
		
		return removeDuplicates(list);
	}
	
		public ArrayList<legalTurn> removeDuplicates(ArrayList<legalTurn> list) {
		legalTurn temp;
		legalTurn nestedTemp;
		
		for(int i=0; i<(list.size()-1); i++) {
			temp = list.get(i);
			for(int j=i+1; j<(list.size()-1); j++) {
				nestedTemp = list.get(j);
				
				if(temp.getMove(0).getStart() == nestedTemp.getMove(0).getStart() && temp.getMove(1).getfinish() == nestedTemp.getMove(1).getfinish()) {
					list.remove(j);
				}
			}
		}
		
		return list;
	}
	
	public void addPipNums() {
		pipNum = new JLabel[24];
		
		for(int i = 0; i < 24; i++) {
			pipNum[i] = new JLabel(String.valueOf(i+1));
			lp.add(pipNum[i]);
			pipNum[i].setFont(new Font("Serif", Font.PLAIN, 20));
			pipNum[i].setForeground(Color.white);
			
			if(i < 6) {
				pipNum[i].setBounds(1035 - (i*xOffset), 600, 40, 40);
			}
			else if(i >= 6 && i < 12) {
				pipNum[i].setBounds(475 - ((i-6)*xOffset), 600, 40, 40);
			}
			else if(i >= 12 && i < 18) {
				pipNum[i].setBounds(70 + ((i-12)*xOffset), 5, 40, 40);
			}
			else {
				pipNum[i].setBounds(630 + ((i-18)*xOffset), 5, 40, 40);
			}
		}
	}
	
	public void changePipNums() {
		for(int i = 0; i < 24; i++) {
			if(playerTurn == 2)
				pipNum[i].setText(String.valueOf(i+1));
			else
				pipNum[i].setText(String.valueOf(24-i));
		}
	}
	
	// called when a pip is a legal moving position to highlight pip
	public void changePipColour(int pip) {
		pipNum[pip].setForeground(Color.red);
	}
	
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	public int getPlayerTurn() {
		return playerTurn;
	}

	public void initialiseBoard() {

		positions = new CheckerLayout();
		clearPips = new Pips();
		addPipNums();

		/*for(int i = 0;i < numOfPips;i++) {
			clearPips.label[i].addMouseListener(this);
		}*/
		
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
	public void updateChecker() {
		
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
		if a checker has also been clicked it'll check which pip it was and sets the position of the selected checker to that of the selected pip
		*/
		
		if(pipSelected) {	
			for(int i = 0;i < numOfCheckers;i++) {
				if(white_Checker[i].getSelected()) {
					
					positions.pips.get(white_Checker[i].getPosition()).remove(white_Checker[i]);
					white_Checker[i].setPosition(newPosition);
							
					numOfCheckersOnPip = positions.pips.get(white_Checker[i].getPosition()).size();
					
					if(white_Checker[i].getPosition() == 25) {
						additionalOffset = -1 * numOfCheckersOnPip * 10;
						lp.setLayer(white_Checker[i].label, (Integer) (numOfCheckersOnPip + 2) );
					}
					else {
						if(numOfCheckersOnPip < 5) {
							if(newPosition < 12 || newPosition == 25 || newPosition == 27) {
								additionalOffset = -1 * numOfCheckersOnPip * 50;
							}
							else {
								additionalOffset = numOfCheckersOnPip * 50;
							}
							lp.setLayer(white_Checker[i].label, (Integer) 2);
						}
						else {
							if(newPosition < 12 || newPosition == 25 || newPosition == 27)
								additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
							else
								additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
							lp.setLayer(white_Checker[i].label, (Integer) 3);
						}
					}
					positions.pips.get(white_Checker[i].getPosition()).add(white_Checker[i]);
					white_Checker[i].label.setBounds((int) positions.getInitialOffset()[white_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[white_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
					
				} else if(black_Checker[i].getSelected()) {
					
					positions.pips.get(black_Checker[i].getPosition()).remove(black_Checker[i]);
					black_Checker[i].setPosition(newPosition);
					
					numOfCheckersOnPip = positions.pips.get(black_Checker[i].getPosition()).size();
					
					if(black_Checker[i].getPosition() == 24) {
						additionalOffset = numOfCheckersOnPip * 10;
						lp.setLayer(black_Checker[i].label, (Integer) (numOfCheckersOnPip + 2) );
					}
					else {
						if(numOfCheckersOnPip < 5 || newPosition == 25 || newPosition == 27) {
							if(newPosition < 12)
								additionalOffset = -1 * numOfCheckersOnPip * 50;
							else
								additionalOffset = numOfCheckersOnPip * 50;
							lp.setLayer(black_Checker[i].label, (Integer) 2);
						}
						else {
							if(newPosition < 12 || newPosition == 25 || newPosition == 27)
								additionalOffset = -1 * ((numOfCheckersOnPip%5 * 50) + 25);
							else
								additionalOffset = (numOfCheckersOnPip%5 * 50) + 25;
							lp.setLayer(black_Checker[i].label, (Integer) 3);
						}
					}
					positions.pips.get(black_Checker[i].getPosition()).add(black_Checker[i]);
					black_Checker[i].label.setBounds((int) positions.getInitialOffset()[black_Checker[i].getPosition()].getX(), (int) positions.getInitialOffset()[black_Checker[i].getPosition()].getY() + additionalOffset, checkerWidth, checkerHeight);
				}
			}
		}
		
	}
	
	public void deSelectCheckers() {
		for(int i = 0;i < numOfCheckers;i++) {
			white_Checker[i].setSelected(false);
			black_Checker[i].setSelected(false);
		}
	}
	
	public validPip validPip(int pipPosition, int checkerType, boolean legalListMoves) {
		
		validPip temp = new validPip();
		
		if(pipPosition > 25 || pipPosition < 0) {
			temp.setValid(false);
			return temp;
		}
		else if(positions.pips.get(pipPosition).isEmpty()) {
			temp.setValid(true);
			temp.setType(0);
			return temp;
		}
		else if(positions.pips.get(pipPosition).get(0).getType() == checkerType) {
			temp.setValid(true);
			temp.setType(1);
			return temp;
		}
		else if(positions.pips.get(pipPosition).size() == 1 && positions.pips.get(pipPosition).get(0).getType() != checkerType) {
			if(!legalListMoves) {
				if(checkerType == 1)
					positions.pips.get(pipPosition).get(0).setPosition(27);
				else if(checkerType == 0)
					positions.pips.get(pipPosition).get(0).setPosition(26);
				positions.pips.get(pipPosition).remove(0);
				updateBoard();
			}
			temp.setValid(true);
			temp.setType(2);
			return temp;
		}
		else {
			temp.setValid(false);
			return temp;
		}
	}
	
	public int gameOver() {
		
		boolean allCheckersOnBearOff = true;
		
		for(int i = 0;i < numOfCheckers;i++) {
			if(black_Checker[i].getPosition() != 24)
				allCheckersOnBearOff = false;
		}
		
		if(allCheckersOnBearOff)
			return 1;
		
		allCheckersOnBearOff = true;
		
		for(int i = 0;i < numOfCheckers;i++) {
			if(white_Checker[i].getPosition() != 25)
				allCheckersOnBearOff = false;
		}
		
		if(allCheckersOnBearOff)
			return 1;
		
		return 0;
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
    		updateChecker();

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
