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

			rooms.put(tempKey,tempValue);
								
		}
					
		sc.close();

	}
	
	public void loadConfigFiles(){

		//loadRoomConfig();
		theBoard.loadRoomConfig(configFile);
		
	//	System.out.println("room config loaded : " + rooms);
		
		theBoard.loadBoardConfig();
	
	}
	
	public static void main(String[] args)
	{
		
		ClueGame game = new ClueGame("ClueLayout.csv","ClueLegend.txt");
		
		game.loadConfigFiles();
		
	//	System.out.println(game.getBoard().getNumRows());
	//	System.out.println(game.getBoard().getCellAt(5,5));
		System.out.println(game.getBoard().getRooms());
		
		
	}


	public Board getBoard() {
		return theBoard;
	}

	public String getLayoutFile() {
		return layoutFile;
	}

	public String getConfigFile() {
		return configFile;
	}
	
	
	
	
}
