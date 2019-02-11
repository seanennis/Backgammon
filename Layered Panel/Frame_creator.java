package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame_creator extends JFrame
{
	JFrame window = new JFrame("Backgammon");
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	
	private JLabel Lbl1;
	private JTextField Fld1;
	private JButton button1;

	//private JLabel Lbl2;
	private JTextArea Area1;
	private JScrollPane Pane1;
	
	public void createFrame()
	{
		window.add(panel_1,BorderLayout.SOUTH);
		window.add(panel_2, BorderLayout.EAST);
		window.setSize(1000, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	public Frame_creator()
	{
			createTextField();
	    	createButton();
	    	createCommandPanel();
		    createTextArea();
		    createInfoPanel();
		    createFrame();
		    
	}
	
	private void createTextArea()
	{
		//Lbl2 = new JLabel("Information Entered:");
		Area1 = new JTextArea(47, 15);
		Pane1 = new JScrollPane(Area1);
		Area1.setEditable(false);
	    
	}
				
	private void createInfoPanel()
	{
		panel_2.setBackground(Color.black);
	    panel_2.add(Lbl2);
	    panel_2.add(Area1);
	    panel_2.add(Pane1);
	    add(panel_2);
	}
	private void createTextField()
	{
		Lbl1 = new JLabel("Enter Commands:");

	    Fld1 = new JTextField(20);
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
