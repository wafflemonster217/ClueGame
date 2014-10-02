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
		rooms = new HashMap<Character,String>();
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
			if(splitLine.length != 2) {
				scan.close();
				throw new BadConfigFormatException("Bad legend line formatting.");
			}
			if(splitLine[0].length() > 1) {
				scan.close();
				throw new BadConfigFormatException("Room Cymbol is too long.");
			}

			rooms.put(tempKey,tempValue);				
		}

		scan.close();
		theBoard.setRooms(rooms);
	}

	
	
	public void loadConfigFiles() {
	
		try {
			loadRoomConfig();
			theBoard.loadBoardConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			
			//
		}
		
	
	}

	public Board getBoard() {
		return theBoard;
	}	
}
