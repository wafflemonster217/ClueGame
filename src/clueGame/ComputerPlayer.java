package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
		// TODO Auto-generated constructor stub
	}

	private char lastRoomVisited;
	
	public BoardCell pickLocation (Set<BoardCell> targets) {
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
