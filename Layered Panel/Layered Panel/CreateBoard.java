import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateBoard extends JFrame {
	
	private static int WIDTH = 1500;
	private static int HEIGHT = 700;

	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();

	private JLabel Lbl1;
	private JTextField Fld1;
	private JButton button1;

	private JTextArea Area1;
	//private JScrollPane Pane1;
	private JLabel Lbl2;
	

	Players p1 = new Players();
	Players p2 = new Players();

	public CreateBoard (){
		setSize(WIDTH,HEIGHT);
		createComponents();
	}

	public void createComponents() {

		setLayout(new BorderLayout());

		LayeredPanel p = new LayeredPanel();

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
		Area1 = new JTextArea("Please enter player names:\n",37,14);
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

	private class TextListener implements ActionListener 
	{
		int c = 0; 
		int n = 1; 
		int p = 0; 
		
        public void actionPerformed(ActionEvent event)
        {
        	if(c<2)
        	{
        		String nameString = Fld1.getText();
        		if(p == 0)
        		{
        			p1.name = nameString;
        			if(nameString.equals("Quit") || nameString.equals("QUIT")|| nameString.equals("quit"))
            	 	{
         				System.exit(0);
            	 	}
        			else
        			{
        				Area1.append("Player " + n + ":\n");
        				Area1.append(nameString + "\n");
       	 				Fld1.setText("");
        			}
        			p++;
        		}
        	
        		else if(p == 1)
        		{
        			 p2.name = nameString;
        			
        			if(nameString.equals("Quit") || nameString.equals("QUIT")|| nameString.equals("quit"))
           	 		{
        				System.exit(0);
           	 		}
        			else
        			{	
           	 			Area1.append("Player " + n + ":\n");
           	 			Area1.append(nameString + "\n");
           	 			Fld1.setText("");
        			}
        		}
    			c++;
    			n++;
    			
        }
        	
        	
        	else if(c >= 2)
        	{
        		String inputString = Fld1.getText();
        		if(inputString.equals("Quit") || inputString.equals("QUIT")|| inputString.equals("quit"))
        		{
        			System.exit(0);
        		}
        		else
        		{
        			Area1.append(inputString + "\n");
        			Fld1.setText("");
        		}
        	}	
		}
	}

}