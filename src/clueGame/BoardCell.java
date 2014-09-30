package clueGame;

public abstract class BoardCell {
	 int column;
	 int row;
	
	public BoardCell(int r, int c)
	{
		row = r;
		column = c;
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

	@Override
	public String toString() {
		return "BoardCell [column=" + column + ", row=" + row + "]";
	}

	
	
}
