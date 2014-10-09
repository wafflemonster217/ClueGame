package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class CardandPersonConfigTests {

		private static Board board;
		private LinkedList<BoardCell> adjList;

		@BeforeClass
		public static void setup() {
			ClueGame game = new ClueGame("ourBoardLayout.csv", "ourLegend.csv");
			game.loadConfigFiles();
			board = game.getBoard();
			board.calcAdjacencies();
		}
		
		@Test 
		public void humanPlayerTest() {
			
		}
		
		// Test that an exception is thrown for a bad config file
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
		}

}
