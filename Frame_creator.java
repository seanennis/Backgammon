package frame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Frame_creator extends JFrame
{
	JFrame window = new JFrame("Backgammon");
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	
	private JLabel Lbl1;
	private JTextField Fld1;
	private JButton button1;

	private JLabel Lbl2;
	private JTextArea Area1;
	private JScrollPane Pane1;
	
	//private JLabel background;
	
	public void combinePanels()
	{
		
		window.setSize(1920, 1080);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.add(panel_1,BorderLayout.SOUTH);
		window.add(panel_2,BorderLayout.EAST);
	}
	
	public Frame_creator() 
	{
			createTextField();
	    	createButton();
	    	createCommandPanel();
		    createTextArea();
		    createInfoPanel();
		    combinePanels();	    
	}
	
	private void createTextArea()
	{
		Lbl2 = new JLabel("Information Entered:");
		Lbl2.setForeground(Color.white);
		Area1 = new JTextArea(47, 15);
		Pane1 = new JScrollPane(Area1);
		Area1.setEditable(false);    
	}
				
	private void createInfoPanel()
	{
		//background = new JLabel(new ImageIcon("C:\\Users\\User\\Documents\\Computer Science-Year 2\\Software Engineering Project II - COMP20050"));
		panel_2.setBackground(Color.black);
		//add(background);
	    panel_2.add(Lbl2);
	    panel_2.add(Area1);
	    panel_2.add(Pane1);
	    panel_2.setPreferredSize(new Dimension(200, 980));
	    add(panel_2);
	}
	
	private void createTextField()
	{
		Lbl1 = new JLabel("Enter Commands:");
		Lbl1.setForeground(Color.white);

	    Fld1 = new JTextField("",20);	    
	}
	
	private void createButton()
	{
		button1 = new JButton("Click to Continue"); 
		button1.setText("Enter");
		button1.addActionListener((ActionListener) new TextListener());
	      
	} 
	
	private void createCommandPanel()
	{
		//BufferedImage image = ImageIO.read(new File( "C:\\Users\\User\\Documents\\Computer Science-Year 2\\Software Engineering Project II - COMP20050/marble.jpg"));
		//background = new JLabel(new ImageIcon(image));
		//panel_1.add(background);
		panel_1.setBackground(Color.black);
	    panel_1.add(Lbl1);
	    panel_1.add(Fld1);
	    panel_1.add(button1);  
	    panel_1.setPreferredSize(new Dimension(1720, 100));
	    add(panel_1);
	}
	   
	private class TextListener implements ActionListener 
	{
        public void actionPerformed(ActionEvent event)
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
