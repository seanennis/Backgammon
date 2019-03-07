/* 
*	Luke - 17426404
* 	Adam - 17364606 
*	Sean - 17469914
*/

public class Players
{
	String name;
	int checkerID;
	
	public Players() {
		this.name = null;
		this.checkerID = 0;
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
	
}
