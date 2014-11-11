package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame {
	private Map<Character, String> rooms;
	private Board theBoard;
	private String layoutFile;
	private String configFile;
	private String playerConfigFile;
	private String deckConfigFile;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private ArrayList<Card> seen;
	private Solution solution;
	public static final int CELL_SIZE = 26;
	public static final int DECK_SIZE = 21;
	public static final int WINDOW_SIZE = 800;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_PEOPLE = 6;
	private DetectiveNotes dN;
	private AccusationDialog aD;
	private GuessDialog gD;
	static boolean isTurnOver;
	private JTextField dieField;
	private JTextField guessField;
	private JTextField turnField;
	private JTextField guessResultField;
	private int currentPlayer = 0;
	private static final int die = 5;
	
	private JTextArea humanPlayerCard;
	private JTextArea humanWeaponCard;
	private JTextArea humanRoomCard;
	private boolean accusationFlag = false;
	private Solution accusation = null;
	
	public ClueGame() {
		layoutFile = "ClueLayout.csv";
		configFile = "ClueLegend.txt";
		commonCtor();
	}
	
	public ClueGame(String layout, String legend) {
		layoutFile = layout;
		configFile = legend;
		commonCtor();
	}
	
	private void commonCtor() {
		playerConfigFile = "CluePlayers.txt";
		deckConfigFile = "Deck.txt";
		theBoard = new Board(layoutFile, gD);
		
		rooms = new HashMap<Character, String>();
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();
		seen = new ArrayList<Card>();
		isTurnOver = true;
		
		setLayout(new BorderLayout());
		setSize(WINDOW_SIZE, WINDOW_SIZE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
		JPanel boardAndCards = new JPanel();
		JPanel theBoardWrapper = new JPanel();
		theBoardWrapper.setLayout(new BorderLayout());
		theBoardWrapper.add(theBoard, BorderLayout.CENTER);
		theBoardWrapper.setPreferredSize(new Dimension(575, 600));
		boardAndCards.add(theBoardWrapper);
		boardAndCards.add(createMyCardPanel());
		add(boardAndCards);
		add(createControlPanel(), BorderLayout.SOUTH);
		
//		gD = new GuessDialog(deck, this);
		
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		
		String line;
		String[] splitLine;
		Character tempKey;
		String tempValue;

		Scanner scan = new Scanner(new FileReader(configFile));

		while (scan.hasNextLine()) {
			line = scan.nextLine();
			splitLine = line.split(",");

			
			if (splitLine.length != 2) {
				scan.close();
				throw new BadConfigFormatException("Bad legend line formatting.");
			}
			if (splitLine[0].length() > 1) {
				scan.close();
				throw new BadConfigFormatException("Room Symbol is too long.");
			}
			tempKey =  line.charAt(0);
			tempValue = splitLine[1];

			tempValue = tempValue.trim();
			rooms.put(tempKey, tempValue);
		}

		scan.close();
		theBoard.setRooms(rooms);
	}
	
	public void loadDeckConfig() throws FileNotFoundException, BadConfigFormatException {
		
		String line;

		Scanner scan = new Scanner(new FileReader(deckConfigFile));

		//read room cards
		for (int i = 0; i < NUM_ROOMS; i++) {
			line = scan.nextLine();
			deck.add(new Card(line, CardType.ROOM));
		}
		//read person cards
		for (int i = 0; i < NUM_PEOPLE; i++) {
			line = scan.nextLine();
			deck.add(new Card(line, CardType.PERSON));
		}
		//read weapon cards
		for (int i = 0; i < NUM_WEAPONS; i++) {
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
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			splitLine = line.split(",");
	
			if (splitLine.length != 4) {
				scan.close();
				throw new BadConfigFormatException("Bad player line formatting.");
			}
			
			if (isFirstRound)
				players.add(new HumanPlayer(splitLine[0], splitLine[1], Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3])));
			else
				players.add(new ComputerPlayer(splitLine[0], splitLine[1], Integer.valueOf(splitLine[2]), Integer.valueOf(splitLine[3])));
			
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
			System.out.println(e.getMessage());
		}
	}
	
	public void deal() {
		solution = new Solution();
		// temporary
		String player = "",
				weapon = "",
				room = "";
		
		HashSet<Integer> dealt = new HashSet<Integer>();
		int random = (int) (Math.random() * deck.size());
		int currentPlayer = 0;
		for (int i = 0; i < deck.size(); i++) {
			while (dealt.contains(random)) {
				random = (int) (Math.random() * deck.size());
			}
			
			switch (deck.get(random).type) {
			case PERSON:
				if (solution.person == null) {
					solution.person = deck.get(random).name;
					dealt.add(random);
				}
				break;
			case WEAPON:
				if (solution.weapon == null) {
					solution.weapon = deck.get(random).name;
					dealt.add(random);
				}
				break;
			case ROOM:
				if (solution.room == null) {
					solution.room = deck.get(random).name;
					dealt.add(random);
				}
				break;
				default:
					break;
			}
			if (!dealt.contains(random)) {
				dealt.add(random);
				if (currentPlayer % players.size() == 0) {
					switch (deck.get(random).type) {
					case PERSON:
						player += deck.get(random).name + "\n";
						break;
					case WEAPON:
						weapon += deck.get(random).name + "\n";
						break;
					case ROOM:
						room += deck.get(random).name + "\n";
						break;
					default:
						break;
					}
				}
				players.get(currentPlayer % players.size()).dealCard(deck.get(random));
				currentPlayer++;
			}
			
			humanPlayerCard.setText(player);
			humanWeaponCard.setText(weapon);
			humanRoomCard.setText(room);
		}
	}
	
	public Card handleSuggestion(Player suggestee, String person, String room, String weapon) {
		guessField.setText(person + ", " + room + ", " + weapon);
		for (Player player : players) {
			if (player.getName().equals(person)) {
				player.setRow(suggestee.getRow());
				player.setCol(suggestee.getCol());
				player.setLastRoomVisited(suggestee.getLastRoomVisited());
			}
		}
		theBoard.repaint();
		for (int i = players.indexOf(suggestee) + 1; i < players.indexOf(suggestee) + players.size(); i++) {
			Card disprove = players.get(i % players.size()).disproveSuggestion(person, room, weapon);
			if (disprove != null) {
				seen.add(disprove);
				guessResultField.setText(disprove.name);
				theBoard.repaint();
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
	
	// Dev only
	public ArrayList<Card> getSeen() {
		return seen;
	}
	
	// Dev only
	public Map<Character, String> getLegend() {
		return rooms;
	}

	// Dev only
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	public DetectiveNotes getNotes() {
		return dN;
	}
	
	public void setNotes(DetectiveNotes dN) {
		this.dN = dN;
	}
	
	public void setAccusation(AccusationDialog aD) {
		this.aD = aD;
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(createDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Show Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dN.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JPanel createControlPanel() {
		JPanel controlPanel = new JPanel();
		
		
		controlPanel.setLayout(new BorderLayout(0, 5));
		JPanel panel = createInfoPanel();
		controlPanel.add(createTopPanel(), BorderLayout.NORTH);
		controlPanel.add(panel, BorderLayout.SOUTH);
		
		
		return controlPanel;
	}

	//Creates the panel to the right where player cards are displayed
	private JPanel createMyCardPanel() {
		JPanel panel = new JPanel();
		
		panel.add(new JLabel("My Cards"));
		panel.setLayout(new GridLayout(4, 1));
		panel.setPreferredSize(new Dimension(100, 300));
		
		humanPlayerCard = new JTextArea();
		humanRoomCard = new JTextArea();
		humanWeaponCard = new JTextArea();
		
		
		panel.add(createMyCardPanel("Players", humanPlayerCard));
		panel.add(createMyCardPanel("Rooms", humanRoomCard));
		panel.add(createMyCardPanel("Weapons", humanWeaponCard));
		return panel;
	}
	
	private JPanel createMyCardPanel(String title, JTextArea textField) {
		JPanel panel = new JPanel();
		
		textField.setEditable(false);
		
		textField.setBackground(null);
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		panel.setPreferredSize(new Dimension(150, 50));
		panel.add(textField);
		return panel;
	}
	
	//Creates the bottom panel with the three information areas
	private JPanel createInfoPanel() {
		JPanel panel = new JPanel();
		
		JPanel smallPanel = createDiePanel();
		panel.add(smallPanel, BorderLayout.WEST);
		
		smallPanel = createGuessPanel();
		panel.add(smallPanel, BorderLayout.CENTER);
		
		smallPanel = createResultPanel();
		panel.add(smallPanel, BorderLayout.EAST);

		return panel;
	}
	
	//Creates top panel with turn panel and two buttons
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		
		JPanel smallPanel = createTurnPanel();
		panel.add(smallPanel);
		
		JButton next = new JButton("Next Player");
		next.setForeground(Color.GRAY);
		next.setFont(new Font("TimesRoman", Font.BOLD, 20));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isTurnOver) {
					nextTurn();
				} else {
					//TODO display error message
				}
			} 
		});
		
		

		JButton accuse = new JButton("Make an Accusation");
		accuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isTurnOver) {
					aD.setVisible(true);
				}
			}
		});
		accuse.setForeground(Color.GRAY);
		accuse.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		panel.add(next);
		panel.add(accuse);
		
		return panel;
	}
	

	//Created Die panel
	private JPanel createDiePanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension (150, 50));
		JLabel nameLabel = new JLabel("Roll");
		dieField = new JTextField(20);
		dieField.setEditable(false);
		panel.setLayout(new GridLayout(1, 2));
		panel.add(nameLabel);
		panel.add(dieField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	//Creates Guess Panel
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Guess");
		guessField = new JTextField(20);
		guessField.setEditable(false);
		panel.setLayout(new GridLayout(2, 1));
		panel.add(nameLabel);
		panel.add(guessField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	//Creates Guess Result panel
	private JPanel createResultPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension (300, 50));
		JLabel nameLabel = new JLabel("Response");
		guessResultField = new JTextField(30);
		guessResultField.setEditable(false);
		panel.setLayout(new GridLayout(1, 2));
		panel.add(nameLabel);
		panel.add(guessResultField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "GuessResult"));
		return panel;
	}
	
	//Creates whose turn panel
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.setBorder(new EtchedBorder());
		JLabel nameLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
		turnField = new JTextField(20);
		turnField.setEditable(false);
		panel.add(nameLabel);
		panel.add(turnField);
		return panel;
	}
	
	private void nextTurn() {
		isTurnOver = false;
		turnField.setText(players.get(currentPlayer).getName());
		if (currentPlayer == 0) {
			startHumanTurn();
		} else {
			computerTurn();
		}
		currentPlayer = ++currentPlayer % players.size();
	}
	
	private void startHumanTurn() {
		HumanPlayer player = (HumanPlayer) players.get(currentPlayer);
		theBoard.calcTargets(player.getRow(), player.getCol(), roll());
		
		//Remove cells corresponding to the last room visited
		ArrayList<BoardCell> remove = new ArrayList<BoardCell>();
		for (BoardCell cell : theBoard.getTargets()) {
			if (cell.isRoom()) {
				if (((RoomCell)cell).getInitial() == player.getLastRoomVisited()) {
					remove.add(cell);
				}
			}
		}
		for (BoardCell cell : remove) {
			theBoard.getTargets().remove(cell);
		}
		theBoard.drawTargets();
		theBoard.repaint();
	}
	
	private void computerTurn() {
		ComputerPlayer player = (ComputerPlayer) players.get(currentPlayer);
		
		if (accusationFlag) {
			accusationFlag = false;
			if (checkAccusation(this.accusation)) {
				//TODO set player as winner
			} else {
				isTurnOver = true;
				return;
			}
		}
		
		theBoard.calcTargets(player.getRow(), player.getCol(), roll());
		BoardCell cell = player.pickLocation(theBoard.getTargets());
		player.setRow(cell.getRow());
		player.setCol(cell.getColumn());
		theBoard.repaint();
		
		if (cell.isRoom()) {
			player.setLastRoomVisited(((RoomCell)cell).getInitial());
			Solution suggestion = player.createSuggestion(seen, deck, rooms);
			if (handleSuggestion(player, suggestion.person, suggestion.room, suggestion.weapon) == null) {
				accusationFlag = true;
				accusation = suggestion;
			}
		}
		
		isTurnOver = true;
	}
	
	private int roll() {
		int roll = (int) (Math.random() * die + 1);
		dieField.setText("" + roll);
		return roll;
	}
	
	//DEV only
	public Solution getSolution() {
		return solution;
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame("ourBoardLayout.csv", "ourLegend.csv");
		game.loadConfigFiles();
		Board board = game.getBoard();
		board.calcAdjacencies();
		game.deal();
		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		

		board.setPlayers(game.getPlayers());
		board.repaint();
		
		game.setNotes(new DetectiveNotes(game.getDeck()));
		game.setAccusation(new AccusationDialog(game.getDeck(), game));
		game.getNotes().setVisible(false);
		
		JOptionPane.showMessageDialog(game, "You are " + game.getPlayers().get(0).getName() + ", press Next Player to begin play ^_^", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
