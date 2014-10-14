package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	private Map<Character,String> rooms;
	private Board theBoard;
	private String layoutFile;
	private String configFile;
	private String playerConfigFile;
	private String deckConfigFile;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	
	public ClueGame()
	{
		rooms = new HashMap<Character,String>();
		layoutFile = "ClueLayout.csv";
		configFile = "ClueLegend.txt";
		playerConfigFile = "CluePlayers.txt";
		deckConfigFile = "ClueDeck.txt";
		theBoard = new Board(layoutFile);
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		
	}
	
	public ClueGame(String layout, String legend)
	{
		rooms = new HashMap<Character, String>();
		layoutFile = layout;
		configFile = legend;
		playerConfigFile = "CluePlayers.txt";
		deckConfigFile = "ClueDeck.txt";
		theBoard = new Board(layout);
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
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

			
			if(splitLine.length != 2) {
				scan.close();
				throw new BadConfigFormatException("Bad legend line formatting.");
			}
			if(splitLine[0].length() > 1) {
				scan.close();
				throw new BadConfigFormatException("Room Symbol is too long.");
			}
			tempKey =  line.charAt(0);
			tempValue = splitLine[1];

			tempValue = tempValue.trim();
			rooms.put(tempKey,tempValue);				
		}

		scan.close();
		theBoard.setRooms(rooms);
	}
	
	public void loadDeckConfig() throws FileNotFoundException, BadConfigFormatException {
		for(int i = 0; i < 21; i++) {
			Card card = new Card("Hi", CardType.PERSON);
			deck.add(card);
		}
	}
	
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		String line;
		String[] splitLine;
		Character tempKey;
		String tempValue;

		Scanner scan = new Scanner(new FileReader(playerConfigFile));

		while(scan.hasNextLine()) {
			line = scan.nextLine();
			splitLine = line.split(",");
	
			if(splitLine.length != 4) {
				scan.close();
				throw new BadConfigFormatException("Bad player line formatting.");
			}
			
			players.add(new Player(splitLine[0], splitLine[1], Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3])));
		}

		scan.close();
		theBoard.setRooms(rooms);
	}

	
	
	public void loadConfigFiles() {
	
		try {
			loadRoomConfig();
			theBoard.loadBoardConfig();
			loadPlayerConfig();
			loadDeckConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			
			//
		}
		
	
	}
	
	public void deal(){
		
	}

	public boolean checkAccusation(Solution solution) {
		return true;
	}
	
	public Board getBoard() {
		return theBoard;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}

}
