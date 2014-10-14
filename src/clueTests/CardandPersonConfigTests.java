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

}
