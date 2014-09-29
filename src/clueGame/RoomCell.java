package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE;
	
	DoorDirection doorDirection;
	char roomInitial;
	
	public Boolean isDoorway()
	{
		return false;
	}
	
	
	public Boolean isRoom()
	{
		return true;
	}
	
	public char getInitial()
	{
		return roomInitial;
	}
	};
}
