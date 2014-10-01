package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	Map<Character,String> rooms = new HashMap<Character,String>();
	private Board theBoard;
	private String layoutFile;
	private String configFile;
	
	public ClueGame(String layout,String legend)
	{
		layoutFile = layout;
		configFile = legend;
		theBoard = new Board();
	}
	
	public void loadRoomConfig()
		{
			FileReader fr = null;
			Scanner sc = null;
			String line = " ";
			String[] splitLine;
			Character tempKey = ' ';
			String tempValue = " ";
			
			//System.out.println("room config");
			try
			{
				fr = new FileReader(configFile);
				
			}
			catch(FileNotFoundException e)
			{
				System.out.println(e);
			}
			
			sc = new Scanner(fr);
			
			while(sc.hasNextLine())
			{
				
				line = sc.nextLine();
				splitLine = line.split(",");
					
				tempKey =  line.charAt(0);
				tempValue = splitLine[1];

				
				//tempValue = tempValue.toUpperCase();
				tempValue = tempValue.trim();
				
				rooms.put(tempKey,tempValue);
									
			}
						
			sc.close();
			
		//System.out.println(rooms);

		}
	
	public void loadConfigFiles(){

		//loadRoomConfig();
		loadRoomConfig();
		theBoard.rooms = rooms;
	//	System.out.println("room config loaded : " + rooms);
		
		theBoard.loadBoardConfig();
	
	}
	
	// need to get rid of main
	public static void main(String[] args)
	{
		
		ClueGame game = new ClueGame("ClueLayout.csv","ClueLegend.txt");
		
		game.loadConfigFiles();
		
		
		System.out.println(game.getBoard().getNumRows());
		System.out.println(game.getBoard().getCellAt(5,5));
		System.out.println(game.getBoard().getRooms());
		System.out.println(game.getBoard().getCellAt(0, 0).isRoom());
		
		
		/*
		for(int r = 0; r < game.getBoard().getNumRows(); r++)
		{
			
			for(int c = 0; c < game.getBoard().getNumColumns(); c++)
			{
				
				System.out.println(game.getBoard().getCellAt(r,c) +  " is it a doorway? " + game.getBoard().getCellAt(r,c).isDoorway());
				
				
				
			}
			
			
			
		}
		*/
	}

	public Board getBoard() {
		return theBoard;
	}

	
	//I dont think we need to get the filenames, we know them upon creation
	public String getLayoutFile() {
		return layoutFile;
	}

	public String getConfigFile() {
		return configFile;
	}
	
	
	
	
}
