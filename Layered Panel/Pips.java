public class Pips {
	
	private JLabel[] clearPip;
	private int numOfPips = 24;

	public Pips() {

		for(int i = 0;i < numOfPips;i++) {
			clearPip = new JLabel();
		}
	}
}