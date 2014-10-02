package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import clue.BoardCell;
//import clue.IntBoard;

public class Board {

	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private ArrayList<BoardCell> visited;
	private BoardCell current;
	
	private static String layoutFile;
	private BoardCell[][] layout;
	Map<Character,String> rooms;
	
	
	int numRows;
	int numColumns;
	
	

	public Board(String lF) {
		layoutFile = lF;
		targets = new HashSet<BoardCell>();
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
		rooms = new HashMap<Character,String>();
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
	

	public WalkwayCell getWalkwayCellAt(int r, int c) {
		return (WalkwayCell) layout[r][c];
	}

	public void calcAdjacencies() {
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		for(int i=0;i<numRows;i++) {
			for(int j=0;j<numColumns;j++) {
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				if(layout[i][j].isDoorway()){
					switch(((RoomCell) layout[i][j]).getDoorDirection()) {
					case UP:
						adjList.add(layout[i-1][j]);
						break;
					case DOWN:
						adjList.add(layout[i+1][j]);
						break;
					case LEFT:
						adjList.add(layout[i][j-1]);
						break;
					case RIGHT:
						adjList.add(layout[i][j+1]);
						break;
					case NONE:
						break;
					}
				} else if (layout[i][j].isWalkway()) {
					if(i>0 && layout[i-1][j].isWalkway()){
						adjList.add(layout[i-1][j]);
					} else if (i>0 && layout[i-1][j].isDoorway()){
						if( ((RoomCell) layout[i-1][j]).getDoorDirection()==RoomCell.DoorDirection.DOWN) {
							adjList.add(layout[i-1][j]);
						}
					}
					if(i<numRows-1 && layout[i+1][j].isWalkway()){
						adjList.add(layout[i+1][j]);
					} else if (i<numRows-1 && layout[i+1][j].isDoorway()){
						if( ((RoomCell) layout[i+1][j]).getDoorDirection()==RoomCell.DoorDirection.UP) {
							adjList.add(layout[i+1][j]);
						}
					}
					if(j>0 && layout[i][j-1].isWalkway()){
						adjList.add(layout[i][j-1]);
					} else if (j>0 && layout[i][j-1].isDoorway()){
						if( ((RoomCell) layout[i][j-1]).getDoorDirection()==RoomCell.DoorDirection.RIGHT) {
							adjList.add(layout[i][j-1]);
						}
					}
					if(j<numColumns-1 && layout[i][j+1].isWalkway()){
						adjList.add(layout[i][j+1]);
					} else if (j<numColumns-1 && layout[i][j+1].isDoorway()){
						if( ((RoomCell) layout[i][j+1]).getDoorDirection()==RoomCell.DoorDirection.LEFT) {
							adjList.add(layout[i][j+1]);
						}
					}
				} else {
					
				}
				adjMtx.put(layout[i][j], adjList);
			}
		}
				
	}
	
	public void calcTargets(int i, int j, int steps) {
		targets.clear();
		current = layout[i][j];
		visited = new ArrayList<BoardCell>();
		if(steps > 0 ) {
			targetHelper(current.getRow(), current.getColumn(), steps);
		}

	}
	
	public void targetHelper(int i, int j, int steps) {
		if(steps == 0) {
			if (!targets.contains(layout[i][j])) {
				targets.add(layout[i][j]);
			}
		} else if(layout[i][j].isDoorway() && layout[i][j]!=current) {
			if (!targets.contains(layout[i][j])) {
				targets.add(layout[i][j]);
			}
		} else { 
			LinkedList<BoardCell> nextList = adjMtx.get(layout[i][j]);
			for(BoardCell e : nextList) {
				if(!visited.contains(e) && !e.equals(current)){
					visited.add(e);
					targetHelper(e.getRow(),e.getColumn(),steps-1);
				}
				visited.remove(e);
			}
		}
		
		
	}
	
	public Set<BoardCell> getTargets() {
		targets.remove(current);
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return adjMtx.get(layout[i][j]);
	}
	

	
}
