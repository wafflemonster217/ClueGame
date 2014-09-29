package clue;

public class BoardCell {

	private int row;
	private int column;
	
	public BoardCell(int r, int c)
	{
		row = r;
		column = c;
	}
	
	public BoardCell()
	{
		
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	}


