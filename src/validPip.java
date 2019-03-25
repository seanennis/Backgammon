public class validPip {
	
	boolean valid;
	int type;
	
	public validPip() {
		this.valid = false;
		this.type = -1;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean getValid() {
		return this.valid;
	}
	public int getType() {
		return this.type;
	}
}