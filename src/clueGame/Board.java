package clueGame;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

//import clue.BoardCell;
//import clue.IntBoard;

public class Board extends JPanel {

	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private ArrayList<BoardCell> visited;
	private BoardCell current;
	private ArrayList<Player> players;
	private String layoutFile;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	
	private int numRows;
	private int numColumns;

	public Board(String layoutFile) {
		this.layoutFile = layoutFile;
		targets = new HashSet<BoardCell>();
	}
		
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		
		ArrayList<ArrayList<String>> tempBoard = new ArrayList<ArrayList<String>>();
		Scanner boardRead = new Scanner(new FileReader(layoutFile));
		
		numRows = 0;
		while (boardRead.hasNextLine()) {
			
			tempBoard.add(new ArrayList<String>());
			String readLine = boardRead.nextLine();
			String[] letters = readLine.split(",");
			
			for (int i = 0; i < letters.length; i++) {
				tempBoard.get(numRows).add(letters[i]);
			}
		
			if (numColumns != 0 && numColumns != letters.length) {
				boardRead.close();
				throw new BadConfigFormatException("Row lengths are inconsistant in layout config file.");
			} else {
				numColumns = letters.length;
			}
			numRows++;
		}
		boardRead.close();
		
		board = new BoardCell[numRows][numColumns];
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (i > tempBoard.size())
					throw new BadConfigFormatException("Temp board row count doesn't match config file");
				
				if (j > tempBoard.get(0).size())
					throw new BadConfigFormatException("Temp board column count doesn't match config file");
				
				if (!rooms.containsKey(tempBoard.get(i).get(j).charAt(0)))
					throw new BadConfigFormatException("Found undefined key in layout file");
				
				switch (tempBoard.get(i).get(j)) {
				case "W":
					board[i][j] = new WalkwayCell(i, j);
					break;
				default:
					board[i][j] = new RoomCell(i, j, tempBoard.get(i).get(j));
					break;
				}
			}
		}
		

	}

	
	public void setRooms(Map<Character, String> inRooms) throws BadConfigFormatException {
		rooms = new HashMap<Character, String>();
		if (inRooms == null)
			throw new BadConfigFormatException();
		else
			rooms = inRooms;
	}
	
	public BoardCell[][] getBoard() {
		return board;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	// Dev only
	public int getNumRows() {
		return numRows;
	}

	// Dev only
	public int getNumColumns() {
		return numColumns;
	}
	
	// Dev only
	public RoomCell getRoomCellAt(int r, int c) {
		return (RoomCell) board[r][c];
	}
	
	// Dev only
	public BoardCell getCellAt(int r, int c) {
		return board[r][c];
	}
	
	// Dev only
	public WalkwayCell getWalkwayCellAt(int r, int c) {
		return (WalkwayCell) board[r][c];
	}

	public void calcAdjacencies() {
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				if (board[row][column].isDoorway()) {
					switch (((RoomCell) board[row][column]).getDoorDirection()) {
					case UP:
						adjList.add(board[row - 1][column]);
						break;
					case DOWN:
						adjList.add(board[row + 1][column]);
						break;
					case LEFT:
						adjList.add(board[row][column - 1]);
						break;
					case RIGHT:
						adjList.add(board[row][column + 1]);
						break;
					case NONE:
						break;
					}
				} else if (board[row][column].isWalkway()) {
					if (row > 0 && board[row - 1][column].isWalkway()) {
						adjList.add(board[row - 1][column]);
					} else if (row > 0 && board[row - 1][column].isDoorway()) {
						if (((RoomCell) board[row - 1][column]).getDoorDirection() == RoomCell.DoorDirection.DOWN)
							adjList.add(board[row - 1][column]);
					}
					if (row < numRows - 1 && board[row + 1][column].isWalkway()) {
						adjList.add(board[row + 1][column]);
					} else if (row < numRows - 1 && board[row + 1][column].isDoorway()) {
						if (((RoomCell) board[row + 1][column]).getDoorDirection() == RoomCell.DoorDirection.UP)
							adjList.add(board[row + 1][column]);
					}
					if (column > 0 && board[row][column - 1].isWalkway()) {
						adjList.add(board[row][column - 1]);
					} else if (column > 0 && board[row][column - 1].isDoorway()) {
						if (((RoomCell) board[row][column - 1]).getDoorDirection() == RoomCell.DoorDirection.RIGHT)
							adjList.add(board[row][column - 1]);
					}
					if (column < numColumns - 1 && board[row][column + 1].isWalkway()) {
						adjList.add(board[row][column + 1]);
					} else if (column < numColumns - 1 && board[row][column + 1].isDoorway()) {
						if (((RoomCell) board[row][column + 1]).getDoorDirection() == RoomCell.DoorDirection.LEFT) {
							adjList.add(board[row][column + 1]);
						}
					}
				} else {
					
				}
				adjMtx.put(board[row][column], adjList);
			}
		}		
	}
	
	public void calcTargets(int row, int col, int steps) {
		targets.clear();
		current = board[row][col];
		visited = new ArrayList<BoardCell>();
		if (steps > 0)
			targetHelper(current.getRow(), current.getColumn(), steps);
	}
	
	public void targetHelper(int row, int column, int steps) {
		if (steps == 0) {
			if (!targets.contains(board[row][column]))
				targets.add(board[row][column]);
		} else if (board[row][column].isDoorway() && board[row][column]!=current) {
			if (!targets.contains(board[row][column]))
				targets.add(board[row][column]);
		} else { 
			LinkedList<BoardCell> nextList = adjMtx.get(board[row][column]);
			for (BoardCell cell : nextList) {
				if (!visited.contains(cell) && !cell.equals(current)) {
					visited.add(cell);
					targetHelper(cell.getRow(), cell.getColumn(), steps - 1);
				}
				visited.remove(cell);
			}
		}
	}
	
	public Set<BoardCell> getTargets() {
		targets.remove(current);
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return adjMtx.get(board[i][j]);
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				board[row][col].draw(g, this);
			}
		}
		
		for (Player player : players) {
			player.draw(g);
		}
	}	
}
