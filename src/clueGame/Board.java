package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//import clue.BoardCell;
//import clue.IntBoard;

public class Board {
	
	public static final int MAX_ROWS = 50;
	public static final int MAX_COLS = 50;
	
	private BoardCell[][] layout = new BoardCell[MAX_ROWS][MAX_COLS];
	Map<Character,String> rooms = new HashMap<Character,String>();
	
	int numRows;
	int numColumns;
	
	
	public Board()
	{
		
	}
	
	public void loadBoardConfig(){
		
			
		FileReader fr = null;
		Scanner sc = null;
		String line = "";
		String comma = ",";
		String[] splitStrings;
		
		try
		{
			fr = new FileReader("ClueLayout.csv");
			System.out.println("got file");
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
				
		sc = new Scanner(fr);
		
		while(sc.hasNextLine())
		{
			numColumns = 0;
			
			line = sc.nextLine();
			splitStrings = line.split(comma);
			
			for(int i = 0; i < splitStrings.length; i++)
			{
				
								
			   if(splitStrings[i] == "W")
				{
					BoardCell temp = new WalkwayCell(numRows, numColumns);
				//	System.out.println(temp.toString());
					layout[numRows][numColumns] = temp;
					
				}
				else
				{
					if(splitStrings[i] != "X")
					{
					BoardCell temp = new RoomCell(numRows,numColumns);
					//System.out.println(temp);
					layout[numRows][numColumns] = temp;
					}
								
				}
						
					numColumns ++;
			}
						
			numRows++;
						
		}
		
		System.out.println("rows : " + numRows);
		System.out.println("cols : " + numColumns);
				
		
		sc.close();
		
		
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
	
	public BoardCell getCellAt(int r, int c)
	{
		
		return layout[r][c];
	}
	
	public static void main(String[] args)
	{
		Board aBoard = new Board();
		aBoard.loadBoardConfig();
		
		//System.out.println(aBoard.getLayout());
		
		
	}

	
	
}
