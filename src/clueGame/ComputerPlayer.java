package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		// TODO Auto-generated constructor stub
	}

	private char lastRoomVisited;

	public BoardCell pickLocation (Set<BoardCell> targets) {
		ArrayList<BoardCell> roomCells = new ArrayList<BoardCell>();
		for (BoardCell cell : targets) {
			if (cell.isDoorway()) {
				if (((RoomCell)cell).getInitial() != this.lastRoomVisited) {
					//If room is not last room visited, it must be the target so return it
					this.lastRoomVisited = ((RoomCell)cell).getInitial();
					return cell;
				} else {
					//If the room is last room visited, it cannot be a target so add to removal list
					roomCells.add(cell);
				}
			}
		}
		
		//remove any doors that shouldn't be targets
		for (BoardCell cell : roomCells) {
			targets.remove(cell);
		}
		int random = (int)(Math.random() * targets.size());
		
		for (BoardCell cell : targets) {
			if (random == 0) {
				return cell;
			}
			random--;
		}
		return null;
	}

	public void createSuggestion () {

	}

	public void updateSeen (Card seen) {

	}

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
