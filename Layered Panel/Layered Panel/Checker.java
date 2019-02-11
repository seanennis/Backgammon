import java.awt.*;
import javax.swing.*;

public class Checker{
	
	private int type;
	private int position;
	private int id;
	private boolean selected;
	public JLabel label;

	public Checker() {
		this.type = type;
		this.position = position;
		this.id = id;
		this.selected = false;
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
	public void setSelected(boolean selected) {
		this.selected = selected;
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
	public boolean getSelected() {
		return selected;
	}
}