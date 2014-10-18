package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	private ArrayList<Card> seen;
	private Solution solution;
	
	public ClueGame()
	{
		rooms = new HashMap<Character,String>();
		layoutFile = "ClueLayout.csv";
		configFile = "ClueLegend.txt";
		playerConfigFile = "CluePlayers.txt";
		deckConfigFile = "Deck.txt";
		theBoard = new Board(layoutFile);
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}
	
	public ClueGame(String layout, String legend)
	{
		rooms = new HashMap<Character, String>();
		layoutFile = layout;
		configFile = legend;
		playerConfigFile = "CluePlayers.txt";
		deckConfigFile = "Deck.txt";
		theBoard = new Board(layout);
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		seen = new ArrayList<Card>();
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
		String line;

		Scanner scan = new Scanner(new FileReader(deckConfigFile));

		//read room cards
		for(int i = 0; i < 9; i++) {
			line = scan.nextLine();
			deck.add(new Card(line, CardType.ROOM));
		}
		//read person cards
		for(int i = 0; i < 6; i++) {
			line = scan.nextLine();
			deck.add(new Card(line, CardType.PERSON));
		}
		//read weapon cards
		for(int i = 0; i < 6; i++) {
			line = scan.nextLine();
			deck.add(new Card(line, CardType.WEAPON));
		}

		scan.close();
	}
	
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		String line;
		String[] splitLine;

		Scanner scan = new Scanner(new FileReader(playerConfigFile));

		boolean isFirstRound = true;
		while(scan.hasNextLine()) {
			line = scan.nextLine();
			splitLine = line.split(",");
	
			if(splitLine.length != 4) {
				scan.close();
				throw new BadConfigFormatException("Bad player line formatting.");
			}
			
			if (isFirstRound) {
				players.add(new HumanPlayer(splitLine[0], splitLine[1], Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3])));
			} else {
				players.add(new ComputerPlayer(splitLine[0], splitLine[1], Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3])));
			}
			
			isFirstRound = false;
		}

		scan.close();
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
		HashSet<Integer> dealt = new HashSet<Integer>();
		int random = (int)(Math.random()*21);
		for(int i = 0; i < deck.size(); i++) {
			while (dealt.contains(random)) {
				random = (int)(Math.random()*21);
			}
			dealt.add(random);
			players.get(i % players.size()).dealCard(deck.get(random));
		}
	}
	
	public Card handleSuggestion(Player suggestee, String person, String room, String weapon) {
		for(int i = players.indexOf(suggestee) + 1; i < players.indexOf(suggestee) + players.size(); i++) {
			Card disprove = players.get(i % players.size()).disproveSuggestion(person, room, weapon);
			if (disprove != null) {
				return disprove;
			}
		}
		
		//No one can disprove so return null
		return null;
		
	}
	
	public void seenCard(Card card) {
		seen.add(card);
	}

	public boolean checkAccusation(Solution accusation) {
		return accusation.equals(solution);
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
	
	public ArrayList<Card> getSeen() {
		return seen;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

}
