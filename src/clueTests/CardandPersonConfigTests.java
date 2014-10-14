package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import clueGame.Player;

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
		}
		
		@Test 
		public void humanPlayerTest() {
			Player human = game.getPlayers().get(0);
			assertEquals(human.getName(), "Ted");
			assertEquals(human.getRow(), 20);
			assertEquals(human.getCol(), 8);
			assertEquals(human.getColor(), Color.black);
		}
		
		@Test 
		public void computerPlayerTest1() {
			Player human = game.getPlayers().get(1);
			assertEquals(human.getName(), "Lily");
			assertEquals(human.getRow(), 1);
			assertEquals(human.getCol(), 7);
			assertEquals(human.getColor(), Color.red);
		}
		
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
			assertEquals(deck.size(), 21);
			int weapon = 0;
			int room = 0;
			int person = 0;
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
			ArrayList<Card> deck = game.getDeck();
			assertTrue(deck.contains("Ted"));
			assertTrue(deck.contains("Lounge"));
			assertTrue(deck.contains("Knife"));
		}
		
		/*// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Game ctor takes config file names
			ClueGame game = new ClueGame("ClueLayoutBadColumns.csv", "ClueLegend.txt");
			// You may change these calls if needed to match your function names
			// My loadConfigFiles has a try/catch, so I can't call it directly to
			// see test throwing the BadConfigFormatException
			game.loadRoomConfig();
			game.getBoard().loadBoardConfig();
		}
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file name
			ClueGame game = new ClueGame("ClueLayoutBadRoom.csv", "ClueLegend.txt");
			game.loadRoomConfig();
			game.getBoard().loadBoardConfig();
		}
		// Test that an exception is thrown for a bad config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file name
			ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegendBadFormat.txt");
			game.loadRoomConfig();
			game.getBoard().loadBoardConfig();
		}*/

}
