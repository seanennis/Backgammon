
public class legalTurn {

	Move[] move;
	int[] typeOfMove;
	int checkerId;
	
	
	public legalTurn() {
		this.move = new Move[4];
		this.typeOfMove = new int[4];
		
		for(int i = 0;i < 4;i++)
			move[i] = new Move();
	}
	public legalTurn(legalTurn copy) {
		
		this.move = new Move[4];
		this.typeOfMove = new int[4];
		this.checkerId = copy.getCheckerId();
		
		for(int i = 0;i < 4;i++)
			move[i] = new Move();
		
		for(int i = 0;i < 2;i++) {
			this.move[i] = new Move(copy.getMove(i).getStart(), copy.getMove(i).getfinish());
		}
		for(int i = 0;i < 4;i++) {
			this.typeOfMove[i] = copy.getType(i);
		}
	}
	
	public void setMove(int arrayIndex, Move move) {
		this.move[arrayIndex] = move;
	}
	public void setType(int arrayIndex, int type) {
		this.typeOfMove[arrayIndex] = type;
	}
	public void setCheckerId(int checkerId) {
		this.checkerId = checkerId;
	}
	
	public Move getMove(int arrayIndex) {
		return this.move[arrayIndex];
	}
	public int getType(int arrayIndex) {
		return this.typeOfMove[arrayIndex];
	}
	public int getCheckerId() {
		return this.checkerId;
	}
	
	@Override
	public String toString() {
		
		int offset = 0;
		
		String buffer = "";
		
		if(checkerId == 0) {
			offset = 24;
			for(int i = 0;i < 2;i++) {
				
				if(i == 1) {				
					if(move[i].getStart() != -1) {
						if(move[i].getStart() == 27) {
							buffer += "bar";
						}
						else {
							buffer += offset - move[i].getStart();
						}
						
						buffer += "-";
						
						if(move[i].getfinish() == 24) {
							buffer += "off\n";
						}
						else {
							buffer += (offset - move[i].getfinish());
							if(typeOfMove[i] == 2)
								buffer += "*\n";
							else
								buffer += "\n";
						}
					}
				}
				else {
					if(move[i].getStart() != -1) {
						if(move[i].getStart() == 27) {
							buffer += "bar";
						}
						else {
							buffer += offset - move[i].getStart();
						}
						
						buffer += "-";
						
						if(move[i].getfinish() == 24) {
							buffer += "off\n";
						}
						else {
							buffer += (offset - move[i].getfinish());
							if(typeOfMove[i] == 2)
								buffer += "* ";
							else
								buffer += " ";
						}
					}
				}			
			}
		}
		else if(checkerId == 1) {
			offset = 1;
			for(int i = 0;i < 2;i++) {
				
				if(i == 1) {
					if(move[i].getStart() != -1) {
						if(move[i].getStart() == 26) {
							buffer += "bar";
						}
						else {
							buffer += (offset + move[i].getStart());
						}
						
						buffer += "-";
						
						if(move[i].getfinish() == 25) {
							buffer += "off\n";
						}
						else {
							buffer += (offset + move[i].getfinish());
							if(typeOfMove[i] == 2)
								buffer += "*\n";
							else
								buffer += "\n";
						}
					}
				}
				else {
					if(move[i].getStart() != -1) {
						if(move[i].getStart() == 26) {
							buffer += "bar";
						}
						else {
							buffer += (offset + move[i].getStart());
						}
						
						buffer += "-";
						
						if(move[i].getfinish() == 25) {
							buffer += "off\n";
						}
						else {
							buffer += (offset + move[i].getfinish());
							if(typeOfMove[i] == 2)
								buffer += "* ";
							else
								buffer += " ";
						}
					}
				}			
			}
		}
		return buffer;
	}
}