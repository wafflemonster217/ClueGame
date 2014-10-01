package clueGame;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int r, int c) {
		super(r, c);
	}

	@Override
	public Boolean isWalkway()
	{
		return true;
	}
	
	
}
