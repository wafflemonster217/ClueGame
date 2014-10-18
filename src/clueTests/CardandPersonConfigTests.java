package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class CardandPersonConfigTests {

		private static ClueGame game;
		private static Board board;
		private LinkedList<BoardCell> adjList;

		@BeforeClass
		public static void setup() {
			game = new ClueGame("ourBoardLayout.csv", "ourLegend.csv");
			game.loadConfigFiles();
			board = game.getBoard();
			board.calcAdjacencies();
			game.deal();
		}
		
		@Test 
		public void humanPlayerTest() {
			Player human = game.getPlayers().get(0);
			
			//Check that the human player's name is correct
			assertEquals(human.getName(), "Ted");
			
			//Check the human player's location is correct
			assertEquals(human.getRow(), 20);
			assertEquals(human.getCol(), 8);
			
			//Check the human player's color is correct
			assertEquals(human.getColor(), Color.black);
		}
		
		//Check the same info as human player for computer player 1
		@Test 
		public void computerPlayerTest1() {
			Player human = game.getPlayers().get(1);
			assertEquals(human.getName(), "Lily");
			assertEquals(human.getRow(), 1);
			assertEquals(human.getCol(), 7);
			assertEquals(human.getColor(), Color.red);
		}
		
		//Check the same info for computer player 2
		@Test 
		public void computerPlayerTest2() {
			Player human = game.getPlayers().get(5);
			assertEquals(human.getName(), "Carl");
			assertEquals(human.getRow(), 1);
			assertEquals(human.getCol(), 20);
			assertEquals(human.getColor(), Color.yellow);
		}
		
		@Test 
		public void deckCountTest() {
			ArrayList<Card> deck = game.getDeck();
			
			//Check that the deck has the correct number of cards
			assertEquals(deck.size(), 21);
			int weapon = 0;
			int room = 0;
			int person = 0;
			
			//Check that the deck has the correct number of each type of card
			for (Card card : deck) {
				if (card.type == CardType.PERSON) {
					person++;
				} else if (card.type == CardType.ROOM) {
					room++;
				} else if (card.type == CardType.WEAPON) {
					weapon++;
				}
			}
			assertEquals(weapon, 6);
			assertEquals(room, 9);
			assertEquals(person, 6);
		}
		
		@Test 
		public void deckContentsTest() {
			//Check that the deck does actually contain the correct cards by using three examples
			ArrayList<Card> deck = game.getDeck();
			assertTrue(deck.contains(new Card("Ted", CardType.PERSON)));
			assertTrue(deck.contains(new Card("Lounge", CardType.ROOM)));
			assertTrue(deck.contains(new Card("Knife", CardType.WEAPON)));
		}
		
		@Test 
		public void allCardsDealtTest() {
			ArrayList<Player> players = game.getPlayers();
			HashSet<Card> allCards = new HashSet<Card>();
			ArrayList<Card> deck = game.getDeck();
			int numDealt = 0;
			for(Player player : players) {
				numDealt += player.getHand().size();
				for(Card card : player.getHand()) {
					allCards.add(card);
				}
			}
			
			//Checks that every card was dealt
			for(Card card : deck) {
				assertTrue(allCards.contains(card));
			}
			
			//check the entire deck was dealt
			assertEquals(numDealt, 21);
		}
		
		@Test 
		public void noDuplicatesTest() {
			ArrayList<Player> players = game.getPlayers();
			HashSet<Card> allCards = new HashSet<Card>();
			for(Player player : players) {
				for(Card card : player.getHand()) {
					allCards.add(card);
				}
			}
			//The set can't contain duplicates so if the size is still 21, there are no duplicates
			assertEquals(allCards.size(), 21);
		}
		
		@Test 
		public void playersHaveSameNumCardsTest() {
			ArrayList<Player> players = game.getPlayers();
			ArrayList<Card> deck = game.getDeck();
			double amountCards = deck.size() / players.size();
			for(Player player : players) {
				// shows the number of cards in the hand is equal to average num cards plus or minus 1
				assertEquals(amountCards, player.getHand().size(), 1);
			}
		}
		
		@Test 
		public void accusationTest() {
			game.setSolution(new Solution("Ted", "Knife", "Lounge"));
			
			//Check that the correct accusation returns true
			assertTrue(game.checkAccusation(new Solution("Ted", "Knife", "Lounge")));
			
			//Check that various wrong accusations return false
			assertFalse(game.checkAccusation(new Solution("Robin", "Knife", "Lounge")));
			assertFalse(game.checkAccusation(new Solution("Ted", "Rope", "Lounge")));
			assertFalse(game.checkAccusation(new Solution("Ted", "Knife", "Bedroom")));
			assertFalse(game.checkAccusation(new Solution("Barney", "Wrench", "Garage")));
		}
		
		@Test 
		public void targetLocationNoDoorTest() {
			ComputerPlayer cp = new ComputerPlayer("Lily", "red", 2, 7);
			board.calcTargets(5, 6 , 2);
			
			//This room has two doors so check both of them for selections
			int loc_3_6 = 0;
			int loc_7_6 = 0;
			int loc_5_8 = 0;
			int loc_5_4 = 0;
			int loc_4_5 = 0;
			int loc_4_7 = 0;
			int loc_6_7 = 0;
			int loc_6_5 = 0;
			
			Set<BoardCell> targets = board.getTargets();
			for (int i = 0; i < 100; i++) {
				BoardCell target = cp.pickLocation(targets);
				if(target == board.getCellAt(3, 6)) {
					loc_3_6++;
				} else if (target == board.getCellAt(7, 6)) {
					loc_7_6++;
				} else if (target == board.getCellAt(5, 8)) {
					loc_5_8++;
				} else if (target == board.getCellAt(5, 4)) {
					loc_5_4++;
				} else if (target == board.getCellAt(4, 5)) {
					loc_4_5++;
				} else if (target == board.getCellAt(4, 7)) {
					loc_4_7++;
				} else if (target == board.getCellAt(6, 7)) {
					loc_6_7++;
				} else if (target == board.getCellAt(6, 5)) {
					loc_6_5++;
				}
			}
			
			//Make sure total count is 100
			assertEquals(100, loc_3_6 + loc_7_6 + loc_5_8 + loc_5_4 + loc_4_5 + loc_4_7 + loc_6_7 + loc_6_5);
			
			//Make sure each spot is picked at least 5 times
			assertTrue(5 < loc_3_6);
			assertTrue(5 < loc_7_6);
			assertTrue(5 < loc_5_8);
			assertTrue(5 < loc_5_4);
			assertTrue(5 < loc_4_5);
			assertTrue(5 < loc_4_7);
			assertTrue(5 < loc_6_7);
			assertTrue(5 < loc_6_5);
		}
		
		@Test 
		public void targetLocationIsDoorTest() {
			ComputerPlayer cp = new ComputerPlayer("Lily", "red", 2, 7);
			cp.setLastRoomVisited('K');
			board.calcTargets(11, 6 , 2);
			
			//This room has two doors so check both of them for selections
			int top_door_105 = 0;
			int bot_door_115 = 0;
			Set<BoardCell> targets = board.getTargets();
			for (int i = 0; i < 100; i++) {
				BoardCell target = cp.pickLocation(targets);
				if(target == board.getCellAt(10, 5)) {
					top_door_105++;
				} else if (target == board.getCellAt(11, 5)) {
					bot_door_115++;
				}
			}
			
			//Since both doors are an option, make sure their sum is 100
			assertEquals(100, bot_door_115 + top_door_105);
		}
		
		@Test 
		public void targetLocationIsDoorLastUsedTest() {
			ComputerPlayer cp = new ComputerPlayer("Lily", "red", 2, 7);
			board.calcTargets(11, 6 , 2);
			
			//This room has two doors so check both of them for selections
			int loc_9_6 = 0;
			int loc_13_6 = 0;
			int loc_12_7 = 0;
			int loc_10_7 = 0;
			
			Set<BoardCell> targets = board.getTargets();
			for (int i = 0; i < 100; i++) {
				BoardCell target = cp.pickLocation(targets);
				if(target == board.getCellAt(9, 6)) {
					loc_9_6++;
				} else if (target == board.getCellAt(13, 6)) {
					loc_13_6++;
				} else if (target == board.getCellAt(12, 7)) {
					loc_12_7++;
				} else if (target == board.getCellAt(10, 7)) {
					loc_10_7++;
				}
			}
			
			//Make sure total count is 100
			assertEquals(100, loc_9_6 + loc_13_6 + loc_12_7 + loc_10_7);
			
			//Make sure each spot is picked at least 10 times
			assertTrue(10 < loc_9_6);
			assertTrue(10 < loc_13_6);
			assertTrue(10 < loc_12_7);
			assertTrue(10 < loc_10_7);
		}

}
