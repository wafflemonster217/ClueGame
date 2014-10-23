package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(ClueGame.CELL_SIZE * column, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
		if(isDoorway()) {
			g.setColor(Color.BLUE);
			
			switch (doorDirection) {
				case NONE:
					break;
				case UP:  g.fillRect(ClueGame.CELL_SIZE * column, (int)(ClueGame.CELL_SIZE * (row)), ClueGame.CELL_SIZE, 7);
					break;
				case DOWN: g.fillRect(ClueGame.CELL_SIZE * column, (int)(ClueGame.CELL_SIZE * (row + 1)) - 7, ClueGame.CELL_SIZE, 7);
					break;
				case LEFT:  g.fillRect((int)(ClueGame.CELL_SIZE * (column)), ClueGame.CELL_SIZE * row, 7, ClueGame.CELL_SIZE);
					break;
				case RIGHT:  g.fillRect((int)(ClueGame.CELL_SIZE * (column + 1)) - 7, ClueGame.CELL_SIZE * row, 7, ClueGame.CELL_SIZE);
					break;
			}
		}
	}
	
	
	
}

