
public class Move {
	
	int start;
	int finish;
	
	public Move() {
		start = -1;
		finish = -1;
	}
	public Move(int start, int finish) {
		this.start = start;
		this.finish = finish;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	public int getStart() {
		return this.start;
	}
	public int getfinish() {
		return this.finish;
	}
}