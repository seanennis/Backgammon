import java.awt.*;
import javax.swing.*;

public class Checker{
	
	private int type;
	private int position;
	private int id;
	private JLabel image;

	public Checker(int type, int position, int id) {
		this.type = type;
		this.position = position;
		this.id = id;
	}

	public void setType(int type) {
		this.type = type;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setImage(JLabel image) {
		this.image = image;
	}

	public int getType() {
		return type;
	}
	public int getPosition() {
		return position;
	}
	public int getId() {
		return id;
	}
	public JLabel getImage() {
		return image;
	}


}