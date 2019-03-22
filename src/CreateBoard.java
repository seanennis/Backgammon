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
		
		for(int i = 0;i < p.numOfPips;i++)
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
		
		//p.getPlayerTurn(playerTurn);
		p.changePipNums();
	}
	
	public void roll() {
		for(int i = 0;i < 2;i++)
			dice[i].roll();
		
		if(dice[0].getLastRoll() == dice[1].getLastRoll())
			Area1.append(player[p.getPlayerTurn() - 1].getName() + " rolled: " + dice[0].getLastRoll() + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll()  + ", " + dice[0].getLastRoll() + "\n");
		else
			Area1.append(player[p.getPlayerTurn()- 1].getName() + " rolled: " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
		
		//p.getPlayerTurn(playerTurn);
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
		
		System.out.println("CLicked");

		JLabel temp = (JLabel)e.getSource();
		String labelName = temp.getName();
    	int t;
    	int moveAmount = 0;

    	if(temp.getName().length() == 6)
    		t = Integer.valueOf(temp.getName().substring(5, 6)); 
    	else if(temp.getName().length() == 7)
    		t = Integer.valueOf(temp.getName().substring(5, 7));
    	else
    		t = Integer.valueOf(temp.getName());

    	if(t < 0) {

    		int parsedInt = -1 * (t + 1);

    		for(int i = 0;i < p.numOfPips;i++) {
    			if(p.clearPips.getSelected(i)) {
    				p.clearPips.setSelected(i, false); 	
    				break;
    			}
    		}
    		p.clearPips.setSelected(parsedInt, true);

    		int m;
    		
    		System.out.println("PlayerTurn: " + p.getPlayerTurn() );
    		
    		for(m = 0;m < p.numOfCheckers;m++) {
    			if(p.white_Checker[m].getSelected() && p.getPlayerTurn() == 2) {
    				System.out.println("white checker clicked");
    				moveAmount = Math.abs(p.white_Checker[m].getPosition() - parsedInt); 
//    				System.out.println("Checker position: " + p.white_Checker[m].getPosition());
//    				System.out.println(parsedInt);
    			}
    			else if(p.black_Checker[m].getSelected() && p.getPlayerTurn() == 1) {
    				System.out.println("white checker clicked");
    				moveAmount = Math.abs(p.black_Checker[m].getPosition() - parsedInt);
//    				System.out.println("Checker position: " + p.black_Checker[m].getPosition());
//    				System.out.println(parsedInt);
    			}
    		}
    		
    		if(moveAmount == dice[0].getLastRoll()) {
    			System.out.println("Dice 0 Last Roll: " + dice[0].getLastRoll());
    			dice[0].setLastRoll(0);
    			p.updateBoard(false);
    		} 
    		else if(moveAmount == dice[1].getLastRoll()) {
    			System.out.println("Dice 1 Last Roll: " + dice[1].getLastRoll());
    			dice[1].setLastRoll(0);
    			p.updateBoard(false);
    		}
    		else if(moveAmount == (dice[0].getLastRoll() + dice[1].getLastRoll()) ){
    			dice[0].setLastRoll(0);
    			dice[1].setLastRoll(0);
    			p.updateBoard(false);
    		}

    	} else {
    		if(labelName.substring(0, 5).equals("white")) {

    			p.deSelectCheckers();
		    	p.white_Checker[t].setSelected(true);

    		} else if(labelName.substring(0, 5).equals("black")){
    			
    			p.deSelectCheckers();
		    	p.black_Checker[t].setSelected(true);

    		}
    	}
		
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}

}
