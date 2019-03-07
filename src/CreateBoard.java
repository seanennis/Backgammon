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
		Area1 = new JTextArea("Please enter player names:\n",37,24);
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
			playerTurn = 1;
			Area1.append(player[0].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[0].getName()+"'s turn:");
		}
		else {
			playerTurn = 2;
			Area1.append(player[1].getName() + " goes first" + "\n\n"+DateUtils.time("[HH:mm] ")+player[1].getName()+"'s turn:");
		}
		Area1.append(" " + dice[0].getLastRoll() + ", " + dice[1].getLastRoll() + "\n");
	}
	
	public void roll() {
		for(int i = 0;i < 2;i++)
			dice[i].roll();
		
		Area1.append(dice[0].getLastRoll() + ", " + dice[1].getLastRoll()+"\n");
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
		int p = 0; 
		String inputString;
		
        public void actionPerformed(ActionEvent event)
        {
    		inputString = Fld1.getText();
    		if(p == 0)
    		{
    			player[p] = new Players(inputString, p);
    			if(inputString.toLowerCase().equals("quit"))
     				System.exit(0);
    			else
    			{
    				Area1.append(DateUtils.time("[HH:mm]")+" Player " + (p + 1) + ": " + inputString +" : Black Checkers\n");
   	 				Fld1.setText("");
    			}
    			p++;
    		}
    		else if(p == 1)
    		{
    			player[p] = new Players(inputString, p);
    			if(inputString.toLowerCase().equals("quit"))
    				System.exit(0);
    			else
    			{	
       	 			Area1.append(DateUtils.time("[HH:mm]")+ " Player " + (p + 1) + ": " + inputString + " : White Checkers\n\n");
       	 			Fld1.setText("");
    			}
    			p++;
    			initialRoll();
    		}
    		else {
	    		if(inputString.toLowerCase().equals("quit"))
	    			System.exit(0);
	    		else if(inputString.toLowerCase().equals("next")) { 
	    			Area1.append("");
	    			playerTurn = -1 * playerTurn + 3;
	    			Area1.append(DateUtils.time("[HH:mm]")+" "+player[playerTurn - 1].getName() + "'s turn: ");
					Fld1.setText("");
					roll();
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
    	int t; 

    	if(temp.getName().length() == 6)
    		t = Integer.valueOf(temp.getName().substring(5, 6)); 
    	else if(temp.getName().length() == 7)
    		t = Integer.valueOf(temp.getName().substring(5, 7));
    	else
    		t = Integer.valueOf(temp.getName());

    	if(t < 0) {

    		int parsedInt = -1 * (t + 1);

    		for(int i = 0;i < p.numOfPips;i++) {
    			if(p.clearPips.getSelected(i))
    				p.clearPips.setSelected(i, false); 	
    		}
    		p.clearPips.setSelected(parsedInt, true);
    		p.updateBoard();

    	} else {
    		if(labelName.substring(0, 5).equals("white")) {

    			for(Checker i : p.white_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	for(Checker i : p.black_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	p.white_Checker[t].setSelected(true);

    		} else if(labelName.substring(0, 5).equals("black")){

    			for(Checker i : p.black_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	for(Checker i : p.white_Checker) {
		    		if(i.getSelected())
		    		i.setSelected(false);
		    	}
		    	p.black_Checker[t].setSelected(true);

    		}
    	}
		
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent arg0) {}

}