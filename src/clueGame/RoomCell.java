package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE};
	
	public final DoorDirection doorDirection;
	char roomInitial;
	
	public RoomCell(int r, int c, String status) {
		super(r, c);
		
		if(status.length() == 1)
		{
			roomInitial = status.charAt(0);
			doorDirection = DoorDirection.NONE;
		}
		else
		{
			roomInitial = status.charAt(0);
			
			char temp = status.charAt(1);
			
			switch(temp)
			{
			case 'U': doorDirection = DoorDirection.UP;
			break;
			
			case 'D': doorDirection = DoorDirection.DOWN;
			break;
			
			case 'R': doorDirection = DoorDirection.RIGHT;
			break;
			
			case 'L': doorDirection = DoorDirection.LEFT;
						
			}
			
			
		}
		
		//System.out.println(roomInitial);
	}

		
	
	public Boolean isDoorway()
	{
		if(doorDirection == DoorDirection.NONE || doorDirection == null)
		{
		return false;
		}
		
		
			return true;
			
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

