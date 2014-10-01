package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	private Map<Character,String> rooms;
	private Board theBoard;
	private String layoutFile;
	private String configFile;
	
	public ClueGame(String layout,String legend)
	{
		rooms = new HashMap<Character,String>()
		layoutFile = layout;
		configFile = legend;
		theBoard = new Board(layout);
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		String line;
		String[] splitLine;
		Character tempKey;
		String tempValue;

		Scanner scan = new Scanner(new FileReader(configFile));

		while(scan.hasNextLine()) {
			line = scan.nextLine();
			splitLine = line.split(",");

			tempKey =  line.charAt(0);
			tempValue = splitLine[1];

			tempValue = tempValue.trim();

			rooms.put(tempKey,tempValue);				
		}

		scan.close();
	}

	
	
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException{
		loadRoomConfig();
		theBoard.setRooms(rooms);

		
		theBoard.loadBoardConfig();
	
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
