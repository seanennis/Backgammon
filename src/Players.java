/* 
*	Luke - 17426404
* 	Adam - 17364606 
*	Sean - 17469914
*/

public class Players
{
	String name;
	int checkerID;
	int points;
	boolean hasDoubleDice;
	
	public Players() {
		this.name = null;
		this.checkerID = 0;
		this.points = 0;
	}
	
	public Players(String name) {
		this.name = name;
	}
	
	public Players(String name, int checkerID) {
		this.name = name;
		this.checkerID = checkerID;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setcheckerID(int checkerID)
	{
		this.checkerID = checkerID;
	}
	
	public int getcheckerID()
	{
		return checkerID;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	public void updatePoints(int points) {
		this.points += points;
	}
	public void doublePoints()
	{
		this.points *= 2;
	}
	public int getPoints() {
		return this.points;
	}
	public void setDoubleDice() {
		this.hasDoubleDice = true;
	}
	public boolean getDoubleDice() {
		return this.hasDoubleDice;
	}
}
