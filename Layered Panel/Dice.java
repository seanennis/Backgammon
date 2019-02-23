import javax.swing.*;
	
public class Dice {

	private int type;
	private int position;
	private int id;
	public JLabel label;

	public Dice(int type, int position, int id) {
		this.type = type;     // normal or double dice
		this.id = id;    // either 0 or 1 (two dice are rolled)
		this.label = null;
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
	public int getType() {
		return type;
	}
	public int getPosition() {
		return position;
	}
	public int getId() {
		return id;
	}
}
