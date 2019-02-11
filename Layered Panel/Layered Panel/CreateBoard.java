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
	private JScrollPane Pane1;

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
		//Lbl2 = new JLabel("Information Entered:");
		Area1 = new JTextArea(47, 20);
		Pane1 = new JScrollPane(Area1);
		Area1.setEditable(false);
	    
	}
				
	private void createInfoPanel()
	{
		panel_2.setBackground(Color.black);
	    //panel_2.add(Lbl2);
	    panel_2.add(Area1);
	    panel_2.add(Pane1);
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