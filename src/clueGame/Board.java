package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import clue.BoardCell;
//import clue.IntBoard;

public class Board {
	
	public static final int MAX_ROWS = 50;
	public static final int MAX_COLS = 50;
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	
	private static String layoutFile;
	private BoardCell[][] layout;
	Map<Character,String> rooms;
	
	
	int numRows;
	int numColumns;
	
	
	public Board() {
		rooms = new HashMap<Character,String>();
	}
	
	public Board(String lF) {
		layoutFile = lF;
	}
		
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{
		
		ArrayList<ArrayList<String>> tempBoard = new ArrayList<ArrayList<String>>();
		Scanner boardRead = new Scanner( new FileReader(layoutFile));
		
		numRows = 0;
		while(boardRead.hasNextLine()) {
			
			tempBoard.add(new ArrayList<String>());
			String readLine = boardRead.nextLine();
			String[] letters = readLine.split(",");
			
			for(int i = 0; i < letters.length; i++) {
				tempBoard.get(numRows).add(letters[i]);
			}
			
			if(numColumns != 0 && numColumns != letters.length){
				boardRead.close();
				throw new BadConfigFormatException("Row lengths are inconsistant in layout config file.");
			} else {
				numColumns = letters.length;
			}
			
			numRows++;
		}
		boardRead.close();
		
		layout = new BoardCell[numRows][numColumns];
		
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if(i > tempBoard.size()) {
					throw new BadConfigFormatException("Temp board row count doesn't match config file");
				}
				if(j > tempBoard.get(0).size()) {
					throw new BadConfigFormatException("Temp board column count doesn't match config file");
				}
				if(!rooms.containsKey(tempBoard.get(i).get(j).charAt(0))) {
					throw new BadConfigFormatException("Found undefined key in layout file");
				}
				
				switch(tempBoard.get(i).get(j)) {
				case "W":
					layout[i][j] = new WalkwayCell(i, j);
					break;
				default:
					layout[i][j] = new RoomCell(i, j, tempBoard.get(i).get(j));
					break;
				}
			}
		}
		

	}

	
	public void setRooms(Map<Character,String> inRooms) throws BadConfigFormatException {
		if(inRooms == null){
			throw new BadConfigFormatException();
		} else {
			rooms = inRooms;
		}
	}
	
	public BoardCell[][] getLayout() {
		return layout;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public RoomCell getRoomCellAt(int r, int c) {
		return (RoomCell) layout[r][c];
	}
	
	public BoardCell getCellAt(int r, int c) {
		return layout[r][c];
	}
	
	public void calcAdjacencies() { 
		
	}
	
	public void calcTargets(int i, int j, int steps) {

	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return null;
	}
	
	
}
