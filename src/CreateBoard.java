
import javax.swing.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class CreateBoard extends JFrame implements MouseListener,KeyListener {
	
	private static final long serialVersionUID = 1L;
	private static int WIDTH = 1500;
	private static int HEIGHT = 790;

	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	
	private LayeredPanel p;
	private Dice[] dice = new Dice[2];
	private JLabel Lbl1;
	private JTextField Fld1;
	private JButton button1;

	private JTextArea Area1;
	private JScrollPane Pane1;
	private JLabel Lbl2;

	public Players[] player = new Players[2];
	public int playerTurn = 1;
	public ArrayList<legalTurn> list = new ArrayList<legalTurn>();
	public boolean noLegalMovesAllowed = false;
	public boolean optionalNewMatch = false;
	public boolean startNextGame = false;
	public boolean matchOver = false;
	public int pointGoal = 0;
	public int doubleDiceValue = 1;

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
		Area1 = new JTextArea("Please enter player names:\n",37,21);
		Pane1 = new JScrollPane(Area1);
		//Pane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//Pane1.setPreferredSize(new Dimension(450, 110));
		Area1.setEditable(false);
		Area1.setLineWrap(true);
		Area1.setWrapStyleWord(true);
	    
	}
				
	private void createInfoPanel()
	{
		panel_2.setBackground(Color.black);
	    panel_2.add(Lbl2,BorderLayout.NORTH);
	    panel_2.add(Pane1);
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
		
		/*dice[0].setLastRoll(6);
		dice[1].setLastRoll(3);*/
		
		for(int i = 0;i < 2;i++)
			Area1.append(player[i].getName() + " : " + dice[i].getLastRoll() + "\n");
		
		if(dice[0].getLastRoll() > dice[1].getLastRoll()) {
			p.changePipNums();
			p.setPlayerTurn(1);
			Area1.append(player[0].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[0].getName()+" : Black : " );
		}
		else {
			p.setPlayerTurn(2);
			Area1.append(player[1].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[1].getName()+" : White : ");
		}
		p.updateDice(dice[0].getLastRoll(),dice[1].getLastRoll());
		Area1.append(" " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		
		list = p.listLegalMoves(dice[0].getLastRoll(), dice[1].getLastRoll());
		
		/*for(int i = 0;i < list.size();i++) {
			Area1.append(list.get(i).toString()); */
		
		int a = 65;
		char c =(char)a;
		for(int i = 0;i < list.size();i++) 
		{
			Area1.append(c+" " +list.get(i).toString());
			c++;
		}
		
		p.setScoreboard(pointGoal, player[0], player[1], 0);
		
//		p.listLegalMoves(dice[0].getLastRoll(), dice[1].getLastRoll());
	}
	
	public void roll(){
		
		if(noLegalMovesAllowed) {
			noLegalMovesAllowed = false;
			Area1.setText("");
			Area1.append("No legal move was able to be played so player turn was automatically changed\n\n");
		}
			
		p.changePipNums();
		
		for(int i = 0;i < 2;i++)
		{
			dice[i].roll();
		}
		p.updateDice(dice[0].getLastRoll(),dice[1].getLastRoll());
			
		// for testing 
		/*dice[0].setLastRoll(1);
		dice[1].setLastRoll(2);*/
		//end of code for testing 
		
		if(player[p.getPlayerTurn()-1].getcheckerID() == 0)
		{
			if(dice[0].getLastRoll() == dice[1].getLastRoll())
				Area1.append(player[p.getPlayerTurn() - 1].getName() + " : Black : "+ dice[0].getLastRoll() + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll() + "\n");
			else
				Area1.append(player[p.getPlayerTurn()- 1].getName() + " : Black : " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		}
		else if(player[p.getPlayerTurn()-1].getcheckerID() == 1)
		{
			if(dice[0].getLastRoll() == dice[1].getLastRoll())
				Area1.append(player[p.getPlayerTurn() - 1].getName() + " : White : "+ dice[0].getLastRoll() + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll() + "\n");
			else
				Area1.append(player[p.getPlayerTurn()- 1].getName() + " : White : " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		}
		
		if(p.enterCheckers(-1)) {
			if(!p.legalToEnter(dice[0].getLastRoll(), dice[1].getLastRoll())) {
				Area1.append("Unable to enter checker!\nplease enter next\n");
			}
		}
		
		if(dice[0].getLastRoll() == dice[1].getLastRoll()) {  // when dice values equal double the dice
			for(int i=0; i<2; i++)
				dice[i].setEqualDice();
		}
		
		//testing listLegalMoves
		
		list = p.listLegalMoves(dice[0].getLastRoll(), dice[1].getLastRoll());
		
		if(list.isEmpty()) {
			//Thread.sleep(3000);
			noLegalMovesAllowed = true;
			Area1.append("hi");
			
			roll();
		}
		int a = 65;
		char c =(char)a;
		for(int i = 0;i < list.size();i++) {
			Area1.append(c+" "+list.get(i).toString());
			c++;
		} 
/*		for(int i = 0;i < list.size();i++) {
			Area1.append(list.get(i).toString());
		} */
	}
	
	public boolean BackGammonConditionBlack()
	{
		
		boolean BackGammonBlack = false;
		
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			
			if(playerTurn == 1 && p.black_Checker[i].getPosition()== 24 && p.white_Checker[i].getPosition()<18) //BackGammon black
			{
				BackGammonBlack = true;
			}
			
		}
		return BackGammonBlack;
	}
	
	public boolean BackGammonConditionWhite()
	{
		boolean BackGammonWhite = false;
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			if(playerTurn == 2 && p.white_Checker[i].getPosition()== 25 && p.black_Checker[i].getPosition()>5) // Backgammon white
			{
				BackGammonWhite = true;
			}
		}
		return BackGammonWhite;
	}
	
	public boolean GammonConditionBlack()
	{
		boolean GammonBlack = false;
		
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			if(playerTurn == 1 && p.black_Checker[i].getPosition() == 24 && p.white_Checker[i].getPosition() != 25) // Gammon black
			{
				GammonBlack = true;
			}
			
		}
		return GammonBlack;
	}
	
	public boolean GammonConditionWhite()
	{
		boolean GammonWhite = false;
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			if(playerTurn == 1 && p.white_Checker[i].getPosition() == 25 && p.black_Checker[i].getPosition() != 24) // Gammon white
			{
				GammonWhite = true;
			}
		}
		return GammonWhite;
	}
	
	public boolean WinConditionBlack()
	{
		boolean WinBlack = false;
		
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			if(p.black_Checker[i].getPosition() == 24 )
			{
				WinBlack = true;
			}
			
		}
		return WinBlack;
	}
	
	public boolean WinConditionWhite()
	{
		boolean WinWhite = false;
		for(int i=0;i<=p.numOfCheckers;i++)
		{
			if(p.white_Checker[i].getPosition() == 25)
			{
				WinWhite = true;
			}
		}
		return WinWhite;
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
		boolean doubleEntered=false;
		
        public void actionPerformed(ActionEvent event)
        {
        	if(matchOver)
    			return;

        	inputString = Fld1.getText();

        	if(optionalNewMatch) {
    			if(inputString.toLowerCase().equals("yeah")) {
    				pN = 0;
    				for(int i = 0;i < 2;i++)
            			player[i].setPoints(0);
    				Area1.setText("Please enter player names: ");
    			}
    			else if(inputString.toLowerCase().equals("nay")) {
    				matchOver = true;
    			}
    			else {
    				Area1.append("Invalid option, try again");
    			}
    		}
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
    			Area1.append("How many points would you like to play to?\n");
    			
    		}
    		else if(pN++ == 2) {
    			pointGoal = Integer.parseInt(inputString);
    			Fld1.setText("");
    			initialRoll();
    		}
    		else {
    			if(inputString.length() <= 2 && inputString !="no") {
    				
    				if(inputString.length() == 1) {
    					int firstValue = Character.getNumericValue(inputString.charAt(0)) - 9;
    					
    					p.selectLegalList(list.get(firstValue - 1));
    					Fld1.setText("");
    				}
    				else {
    					int firstValue = Character.getNumericValue(inputString.charAt(0)) - 9;
        				int secondValue = Character.getNumericValue(inputString.charAt(1)) - 9;
        				
        				p.selectLegalList(list.get(firstValue * 26 + (secondValue - 1)));
        				Fld1.setText("");
    				}
    			}
    			else {
		    		if(inputString.toLowerCase().equals("quit"))
		    			System.exit(0);
		    		else if(inputString.toLowerCase().equals("next")) { 
		    			//Area1.setText("");
		    			
		    			p.setPlayerTurn(-1 * p.getPlayerTurn() + 3);
		    			Area1.append(DateUtils.time("[HH:mm] "));
						Fld1.setText("");
						roll();
						p.updateDice(dice[0].getLastRoll(),dice[1].getLastRoll());
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
		    		
		    		else if(inputString.toLowerCase().equals("double"))
		    		{
		    			doubleEntered =true;
		    		}
		    		
		    		else
		    		{
		    			Area1.append(DateUtils.time("\n[HH:mm]")+" Not a valid command\n\n");
		    			Fld1.setText("");
		    		}
		    		
		    		
    			}
    		}
    		if(doubleEntered == true)
    		{
    			int c = 0;
    			Fld1.setText("");
    			inputString = Fld1.getText();
    			Area1.append("\nAccept: Yes/No\n");
    			if(inputString.toLowerCase().equals("yes"))
    			{
    				
    				if(c%2 == 0 && p.getPlayerTurn() == 1)
    				{
    					player[0].doublePoints();
    					player[1].doublePoints();
    					c++;
    				}
    				else if(c%2 == 1 && p.getPlayerTurn() == 2)
    				{
    					player[0].doublePoints();
    					player[1].doublePoints();
    					c++;
    				}
    			}
    			else if(inputString.toLowerCase().equals("no"))
    			{
    				if(c%2 == 0)
    				{
    					int tmp = player[1].points;
    					player[1].points -= tmp;
    					if(p.gameOver()==true)
    					{
    						tmp = 0;
    					}
    					c++;
    				}
    				else if(c%2 == 1)
    				{

    					int tmp = player[0].points;
    					player[0].points -= tmp;
    					if(p.gameOver()==true)
    					{
    						tmp = 0;
    					}
    					c++;
    				}
    	    		if(pointGoal <= player[0].points)
    	    		{
    	    			Area1.setText("");
        				Area1.setText("Congratulations " + player[0].getName() + " you won!!!!!");
    	    		}
    	    		else if(pointGoal <= player[1].points)
    	    		{

    	    			Area1.setText("");
        				Area1.setText("Congratulations " + player[1].getName() + " you won!!!!!");
    	    		}
    	    		else
    	    		{
    	    		Area1.append("\nPress any key to start next game\n");
    	    		startNextGame = true;
    	    		}
    	    		
    			}
    		}
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if(matchOver)
			return;

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
    		
    		//System.out.println("Dice 1: " + dice[0].getLastRoll() + ", Dice 2: " + dice[1].getLastRoll());

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
					//System.out.println("dice 1: " + dice[0].getLastRoll() + ", dice 2: " + dice[1].getLastRoll());
					Area1.append("\nUnable to enter checker! Please enter next\n");
				}
				else {
					if(p.getPlayerTurn() == 1) {
	    				if(parsedInt <= 5)
	    					p.clearPips.setSelected(parsedInt, true);
	    				else
	    					Area1.append("Player must select a pip in the opposing home board\n");
	    			}
	    			else if(p.getPlayerTurn() == 2) {
	    				if(parsedInt <= 23 && parsedInt >= 18)
	    					p.clearPips.setSelected(parsedInt, true);
	    				else
	    					Area1.append("Player must select a pip in the opposing home board\n");
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
			validPip tempValidPip = p.validPip(parsedInt, p.getPlayerTurn() - 1, false);

    		if(tempValidPip.getValid()) {
    			for(m = 0;m < p.numOfCheckers;m++) {
        			if(p.white_Checker[m].getSelected() && p.getPlayerTurn() == 2) {
        				if(p.enterCheckers(-1))
        					moveAmount = 24 - parsedInt;
        				else if(allCheckersInHomeBoard) {
        					if(parsedInt == 25)
        						moveAmount = p.white_Checker[m].getPosition() + 1;
        					else
        						moveAmount = p.white_Checker[m].getPosition() - parsedInt;
        					
        				}
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
        			//if(tempValidPip.getType() == 2)
        			//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
        			p.updateChecker();
        		} 
        		else if(moveAmount == dice[1].getLastRoll()) {
        			dice[1].setLastRoll(0);
        			//if(tempValidPip.getType() == 2)
        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
        			p.updateChecker();
        		}
        		else if(moveAmount == (dice[0].getLastRoll() + dice[1].getLastRoll()) ){
        			dice[0].setLastRoll(0);
        			dice[1].setLastRoll(0);
        			//if(tempValidPip.getType() == 2)
        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
        			p.updateChecker();
        		}
        		else if(moveAmount < dice[0].getLastRoll() && moveAmount > 0) {
//        			System.out.println("Bear off dice conditions met");
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
        							//if(tempValidPip.getType() == 2)
        		        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
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
        							//if(tempValidPip.getType() == 2)
        		        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
        							p.updateChecker();
        							break;
        						}
        					}
        				}
        			}
        		}
        		else if(moveAmount < dice[1].getLastRoll() && moveAmount > 0) {
        			//System.out.println("Bear off dice conditions met");
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
        							//if(tempValidPip.getType() == 2)
        		        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
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
        							//if(tempValidPip.getType() == 2)
        		        				//player[p.getPlayerTurn()].updatePoints(doubleDiceValue);
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
        				Area1.append("Unable to enter checker! Please enter next\n");
        			}
        		}
    		}
    		if(BackGammonConditionBlack() == true)
    		{
    			player[0].updatePoints(3);
    			System.out.println("Test1"+ player[0].points+player[1].points);
    		}
    		else if(BackGammonConditionWhite() == true)
    		{
    			player[1].updatePoints(3);
    			System.out.println("Test2"+ player[0].points+player[1].points);
    		}
    		
    		if(GammonConditionBlack() == true)
    		{
    			player[0].updatePoints(2);
    			System.out.println("Test3"+ player[0].points+player[1].points);
    		}
    		else if(GammonConditionWhite() == true)
    		{
    			player[1].updatePoints(2);
    			System.out.println("Test4"+ player[0].points+player[1].points);
    		}
    		
    		if(WinConditionBlack() == true)
    		{
    			player[0].updatePoints(1);
    			System.out.println("Test5"+ player[0].points+player[1].points);
    		}
    		else if(WinConditionWhite() == true)
    		{
    			player[1].updatePoints(1);
    			System.out.println("Test6"+ player[0].points+player[1].points);
    		}
    		int winningPlayer = 0;
    		if( player[p.getPlayerTurn()].getPoints() >= pointGoal) {
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
    			Area1.append("\nWould you like to start a new  Match?\n");
    			optionalNewMatch = true;
    		}
    		else if(p.gameOver() == true) {
    			//code to update match score
    			/*matchScore[0] += player[0].getPoints();
    			matchScore[1] += player[1].getPoints();*/    			
    			Area1.append("\nPress any key to start next game\n");
    			startNextGame = true;
    		}
     	} else {
    		
    		// code for if a checker is clicked
    		if(labelName.substring(0, 5).equals("white")) {
    			
    			p.deSelectCheckers();
    			if(p.enterCheckers(label_Id))
    				Area1.append("\nPlayer has checkers on the bar that they must enter\n");
    			else
    				p.white_Checker[label_Id].setSelected(true);
    				
    			
    		} else if(labelName.substring(0, 5).equals("black")){
    			
    			p.deSelectCheckers();
    			if(p.enterCheckers(label_Id))
    				Area1.append("\nPlayer has checkers on the bar that they must enter\n");
    			else
    				p.black_Checker[label_Id].setSelected(true);
    		}
    	}
		
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(startNextGame) {
			p.initialiseBoard();
		}
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}