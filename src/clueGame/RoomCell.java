package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE};
	
	public final DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(int r, int c, String status) {
		super(r, c);
		roomInitial = status.charAt(0);
		if(status.length() == 1)
		{
			doorDirection = DoorDirection.NONE;
		}
		else
		{	
			switch(status.charAt(1)) {
			case 'U': doorDirection = DoorDirection.UP;
				break;
			case 'D': doorDirection = DoorDirection.DOWN;
				break;
			case 'R': doorDirection = DoorDirection.RIGHT;
				break;
			case 'L': doorDirection = DoorDirection.LEFT;
				break;
			default: doorDirection = DoorDirection.NONE;
				break;
			}
		}
	}

	@Override
	public Boolean isDoorway() {
		return !(doorDirection == DoorDirection.NONE);	
	}
	
	@Override
	public Boolean isRoom()
	{
		return true;
	}
	
	public char getInitial()
	{
		return roomInitial;
	}
	
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
	}

	@Override
	public String toString() {
		return "RoomCell [doorDirection=" + doorDirection + ", roomInitial="
				+ roomInitial + "]";
	}
	
	
	
	
}

