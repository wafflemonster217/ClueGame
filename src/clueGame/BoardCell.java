package clueGame;

public abstract class BoardCell {
	int column;
	int row;
	
	public Boolean isWalkway()
	{
		return false;	
	}
	
	public Boolean isRoom()
	{
		return false;
	}
	
	public Boolean isDoorway()
	{
		return false;
	}
	
	
}
