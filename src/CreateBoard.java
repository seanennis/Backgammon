import javax.swing.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class CreateBoard extends JFrame implements MouseListener {
	
	private static int WIDTH = 1500;
	private static int HEIGHT = 700;

	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	
	private LayeredPanel p;
	private Dice[] dice = new Dice[2];
	private JLabel Lbl1;
	private JTextField Fld1;
	private JButton button1;

	private JTextArea Area1;
	//private JScrollPane Pane1;
	private JLabel Lbl2;

	public Players[] player = new Players[2];
	public int playerTurn = 1;

	public CreateBoard (){
		setSize(WIDTH,HEIGHT);
		createComponents();
	}

	public void createComponents() {

		setLayout(new BorderLayout());

		p = new LayeredPanel();
		
// Test of mouse click listeners
		
		for(int i = 0;i < p.numOfCheckers;i++) {
			p.white_Checker[i].label.addMouseListener(this);
			p.black_Checker[i].label.addMouseListener(this);
		}
		
		for(int i = 0;i < (p.numOfPips - 2);i++)
			p.clearPips.label[i].addMouseListener(this);

		createTextField();
    	createButton();
    	createCommandPanel();
	    createTextArea();
	    createInfoPanel();

		panel.add(p);

		add(panel, BorderLayout.LINE_START);
		add(panel_1, BorderLayout.PAGE_END);
		add(panel_2, BorderLayout.CENTER);
	}

	private void createTextArea()
	{
		Font f = new Font("TimesRoman",Font.BOLD,14);
		Lbl2 = new JLabel("Welcome to BackGammon:");
		Lbl2.setFont(f);
		Lbl2.setForeground(Color.WHITE);
		Area1 = new JTextArea("Please enter player names:\n",37,18);
		//Pane1 = new JScrollPane(Area1);
		//Pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//Pane1.setPreferredSize(new Dimension(450, 110));
		Area1.setEditable(false);
	    
	}
				
	private void createInfoPanel()
	{
		panel_2.setBackground(Color.black);
	    panel_2.add(Lbl2,BorderLayout.NORTH);
	    panel_2.add(Area1);
	    //panel_2.add(Pane1,BorderLayout.CENTER);
	}
	private void createTextField()
	{
		Lbl1 = new JLabel("Enter Commands:");

	    Fld1 = new JTextField(40);
	    Fld1.setText("");
	 
	}
	
	private void createButton()
	{
		
		button1 = new JButton("Click to Continue"); 
		
	    button1.addActionListener((ActionListener) new TextListener());
	}
	private void createCommandPanel()
	{
		panel_1.setBackground(Color.black);
	    panel_1.add(Lbl1);
	    panel_1.add(Fld1);
	    panel_1.add(button1);    
	}
	private void initialRoll() {
		Area1.append("Players will roll to see who goes first:\n");
		
		for(int i = 0;i < 2;i++) {
			dice[i] = new Dice();
		}
		
		while(dice[0].getLastRoll() == dice[1].getLastRoll()) {
			for(int i = 0;i < 2;i++)
				dice[i].roll();
		}
		
		for(int i = 0;i < 2;i++)
			Area1.append(player[i].getName() + " : " + dice[i].getLastRoll() + "\n");
		
		if(dice[0].getLastRoll() > dice[1].getLastRoll()) {
			p.setPlayerTurn(1);
			Area1.append(player[0].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[0].getName()+"'s turn:");
		}
		else {
			p.setPlayerTurn(2);
			Area1.append(player[1].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[1].getName()+"'s turn:");
		}
		Area1.append(" " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		
//		p.listLegalMoves(dice[0].getLastRoll(), dice[1].getLastRoll());
		p.changePipNums();
	}
	
	public void roll() {
		
		Area1.setText("");
		
		for(int i = 0;i < 2;i++)
			dice[i].roll();
		
		if(dice[0].getLastRoll() == dice[1].getLastRoll())
			Area1.append(player[p.getPlayerTurn() - 1].getName() + " rolled: " + dice[0].getLastRoll() + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll() + "\n");
		else
			Area1.append(player[p.getPlayerTurn()- 1].getName() + " rolled: " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		
//		p.listLegalMoves(dice[0].getLastRoll(), dice[1].getLastRoll());
		
		if(p.enterCheckers(-1)) {
			if(!p.legalToEnter(dice[0].getLastRoll(), dice[1].getLastRoll())) {
				Area1.append("Unable to enter checker!\nplease enter next\n");
			}
		}
		
		p.changePipNums();
	}
	
	private static class DateUtils
	{
		public static String time(String dateFormat)
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateForm = new SimpleDateFormat(dateFormat);
			return dateForm.format(cal.getTime());
		}
	} 
	
	private class TextListener implements ActionListener 
	{
		int pN = 0; 
		String inputString;
		
        public void actionPerformed(ActionEvent event)
        {
    		inputString = Fld1.getText();
    		if(pN == 0)
    		{
    			player[pN] = new Players(inputString, pN);
    			if(inputString.toLowerCase().equals("quit"))
     				System.exit(0);
    			else
    			{
    				Area1.append(DateUtils.time("[HH:mm]")+" Player " + (pN + 1) + ": " + inputString +" : Black Checkers\n");
   	 				Fld1.setText("");
    			}
    			pN++;
    		}
    		
    		else if(pN == 1)
    		{
    			player[pN] = new Players(inputString, pN);
    			if(inputString.toLowerCase().equals("quit"))
    				System.exit(0);
    			else
    			{	
       	 			Area1.append(DateUtils.time("[HH:mm]")+ " Player " + (pN + 1) + ": " + inputString + " : White Checkers\n\n");
       	 			Fld1.setText("");
    			}
    			pN++;
    			initialRoll();
    		}
    		else {
	    		if(inputString.toLowerCase().equals("quit"))
	    			System.exit(0);
	    		else if(inputString.toLowerCase().equals("next")) { 
	    			Area1.append("");
	    			p.setPlayerTurn(-1 * p.getPlayerTurn() + 3);
	    			Area1.append(DateUtils.time("[HH:mm]")+" "+player[p.getPlayerTurn() - 1].getName() + "'s turn: ");
					Fld1.setText("");
					roll();
	    		}
	    		else if(inputString.toLowerCase().equals("cheat"))
	    		{
	    			p.cheatCommand();
	    			Fld1.setText("");
	    		}
	    		else if(inputString.toLowerCase().equals("end game"))
	    		{
	    			p.endGameCommand();
	    			Fld1.setText("");
	    		}
	    		else
	    		{
	    			Area1.append(DateUtils.time("\n[HH:mm]")+" Not a valid command\n\n");
	    			Fld1.setText("");
	    		}
    		}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		JLabel temp = (JLabel)e.getSource();
		String labelName = temp.getName();
    	int label_Id,m;
    	int moveAmount = 0;
    	boolean allCheckersInHomeBoard = true;

    	if(temp.getName().length() == 6)
    		label_Id = Integer.valueOf(temp.getName().substring(5, 6)); 
    	else if(temp.getName().length() == 7)
    		label_Id = Integer.valueOf(temp.getName().substring(5, 7));
    	else
    		label_Id = Integer.valueOf(temp.getName());

    	if(label_Id < 0) {

    		int parsedInt = -1 * (label_Id + 1);

    		for(int i = 0;i < p.numOfPips;i++) {
    			if(p.clearPips.getSelected(i)) {
    				p.clearPips.setSelected(i, false); 	
    				break;
    			}
    		}
    		
    		// checks if player has to enter checkers from bar
			if(p.enterCheckers(-1)) {
				if(!p.legalToEnter(dice[0].getLastRoll(), dice[1].getLastRoll())) {
					System.out.println("dice 1: " + dice[0].getLastRoll() + ", dice 2: " + dice[1].getLastRoll());
					Area1.append("\nUnable to enter checker!\nplease enter next\n");
				}
				else {
					if(p.getPlayerTurn() == 1) {
	    				if(parsedInt <= 5)
	    					p.clearPips.setSelected(parsedInt, true);
	    				else
	    					Area1.append("Player must select a pip\nin the oppsing home board\n");
	    			}
	    			else if(p.getPlayerTurn() == 2) {
	    				if(parsedInt <= 23 && parsedInt >= 18)
	    					p.clearPips.setSelected(parsedInt, true);
	    				else
	    					Area1.append("Player must select a pip\nin the oppsing home board\n");
	    			}
				}
    		}
			// code to check if all the players checkers are in the home board before being able to select the bear off label 
			else if((parsedInt == 24 && p.getPlayerTurn() == 1) || (parsedInt == 25 && p.getPlayerTurn() == 2)) {
    			if(p.getPlayerTurn() == 1) {
    				for(int i = 0;i < p.numOfCheckers;i++) {
    					if((p.black_Checker[i].getPosition() <= 17 || p.black_Checker[i].getPosition() >= 24) && p.black_Checker[i].getPosition() != 24) {
    						allCheckersInHomeBoard = false;
    					}
    				}
    			}
    			else if(p.getPlayerTurn() == 2) {
    				for(int i = 0;i < p.numOfCheckers;i++) {
    					if(p.white_Checker[i].getPosition() >= 6 && p.white_Checker[i].getPosition() != 25) {
    						allCheckersInHomeBoard = false;
    					}
    				}
    			}
    			if(allCheckersInHomeBoard)
    				p.clearPips.setSelected(parsedInt, true);
    			else
    				Area1.append("\n All player checkers not in home board \n");
    		}
    		else
    			p.clearPips.setSelected(parsedInt, true);

			//if the above code allows a pip to be selected this code will check if the selected checker and pip make a valid move
    		if(p.validPip(parsedInt, p.getPlayerTurn() - 1)) {
    			for(m = 0;m < p.numOfCheckers;m++) {
        			if(p.white_Checker[m].getSelected() && p.getPlayerTurn() == 2) {
        				if(p.enterCheckers(-1))
        					moveAmount = 24 - parsedInt;
        				else if(allCheckersInHomeBoard)
        					moveAmount = p.white_Checker[m].getPosition() + 1;
        				else
        					moveAmount = p.white_Checker[m].getPosition() - parsedInt;
        				
        				if(moveAmount < 0)
        					moveAmount = -1;
        			}
        			else if(p.black_Checker[m].getSelected() && p.getPlayerTurn() == 1) {
        				if(p.enterCheckers(-1))
        					moveAmount = parsedInt - (-1);
        				else
        					moveAmount = parsedInt - p.black_Checker[m].getPosition();
        				
        				if(moveAmount < 0)
        						moveAmount = -1;
        			}
        		}
    			
				if(moveAmount == dice[0].getLastRoll()) {
        			dice[0].setLastRoll(0);
        			p.updateChecker();
        		} 
        		else if(moveAmount == dice[1].getLastRoll()) {
        			dice[1].setLastRoll(0);
        			p.updateChecker();
        		}
        		else if(moveAmount == (dice[0].getLastRoll() + dice[1].getLastRoll()) ){
        			dice[0].setLastRoll(0);
        			dice[1].setLastRoll(0);
        			p.updateChecker();
        		}
        		else if(moveAmount < dice[0].getLastRoll() && moveAmount > 0) {
        			System.out.println("bear off dice conditions met");
        			if(allCheckersInHomeBoard) {
        				if(p.getPlayerTurn() == 1) {
        					int lowestPosition = 24;
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.black_Checker[i].getPosition() < lowestPosition) {
        							lowestPosition = p.black_Checker[i].getPosition();
        						}
        					}
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.black_Checker[i].getPosition() == lowestPosition && p.black_Checker[i].getSelected()) {
        							dice[0].setLastRoll(0);
        							p.updateChecker();
        							break;
        						}
        					}
        				}
        				else if(p.getPlayerTurn() == 2) {
        					int highestPosition = 0;
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.white_Checker[i].getPosition() > highestPosition && p.white_Checker[i].getPosition() <= 5) {
        							highestPosition = p.white_Checker[i].getPosition();
        						}
        					}
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.white_Checker[i].getPosition() == highestPosition && p.white_Checker[i].getSelected()) {
        							dice[0].setLastRoll(0);
        							p.updateChecker();
        							break;
        						}
        					}
        				}
        			}
        		}
        		else if(moveAmount < dice[1].getLastRoll() && moveAmount > 0) {
        			System.out.println("bear off dice conditions met");
        			if(allCheckersInHomeBoard) {
        				if(p.getPlayerTurn() == 1) {
        					int lowestPosition = 24;
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.black_Checker[i].getPosition() < lowestPosition) {
        							lowestPosition = p.black_Checker[i].getPosition();
        						}
        					}
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.black_Checker[i].getPosition() == lowestPosition && p.black_Checker[i].getSelected()) {
        							dice[1].setLastRoll(0);
        							p.updateChecker();
        							break;
        						}
        					}
        				}
        				else if(p.getPlayerTurn() == 2) {
        					int highestPosition = 0;
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.white_Checker[i].getPosition() > highestPosition && p.white_Checker[i].getPosition() <= 5) {
        							highestPosition = p.white_Checker[i].getPosition();
        						}
        					}
        					for(int i = 0;i < p.numOfCheckers;i++) {
        						if(p.white_Checker[i].getPosition() == highestPosition && p.white_Checker[i].getSelected()) {
        							dice[1].setLastRoll(0);
        							p.updateChecker();
        							break;
        						}
        					}
        				}
        			}
        		}
        		
        		//re check if the player can enter a second checker after a first has been entered
        		if(p.enterCheckers(-1)) {
        			if(!p.legalToEnter(dice[0].getLastRoll(), dice[1].getLastRoll())) {
        				Area1.append("Unable to enter checker!\nplease enter next\n");
        			}
        		}
    		}
    		int winningPlayer = 0;
    		if((winningPlayer = p.gameOver()) != 0) {
    			switch(winningPlayer) {
    			case 1:
    				Area1.setText("");
    				Area1.setText("Congratulations " + player[winningPlayer - 1].getName() + " you won!!!!!");
    				break;
    			case 2:
    				Area1.setText("");
    				Area1.setText("Congratulations " + player[winningPlayer - 1].getName() + " you won!!!!!");
    				break;
    			default:
    				System.out.println("ERROR default case reached in end of game switch statement");
    				break;
    			}
    		}
    	} else {
    		
    		// code for if a checker is clicked
    		if(labelName.substring(0, 5).equals("white")) {
    			
    			p.deSelectCheckers();
    			if(p.enterCheckers(label_Id))
    				Area1.append("\nPlayer has checkers on the bar that\nthey must enter\n");
    			else
    				p.white_Checker[label_Id].setSelected(true);
    				
    			
    		} else if(labelName.substring(0, 5).equals("black")){
    			
    			p.deSelectCheckers();
    			if(p.enterCheckers(label_Id))
    				Area1.append("\nPlayer has checkers on the bar that\nthey must enter\n");
    			else
    				p.black_Checker[label_Id].setSelected(true);
    		}
    	}
		
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}

}
