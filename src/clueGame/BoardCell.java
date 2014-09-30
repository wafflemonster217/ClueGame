package clueGame;

public abstract class BoardCell {
	private int column;
	private int row;
	
	public BoardCell(int r, int c)
	{
		//super(r,c);
	}
	
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
