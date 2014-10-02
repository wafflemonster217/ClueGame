package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.ClueGame;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.RoomCell;

public class OurInitializationTest {
	
	public static Board board;
	public static ClueGame game;
	
	public static final int ROOM_SUM = 11;
	public static final int ROW_SUM = 23;
	public static final int COLUMN_SUM = 21;

	@BeforeClass
	public static void setUp() {
		game = new ClueGame("ourBoardLayout.csv","ourLegend.csv");
		board = new Board();
	}
	
	
	@Test 
	public void roomLegendTests() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(ROOM_SUM, rooms.size());

		assertEquals("Bedroom", rooms.get('R'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Garage", rooms.get('G'));
	}

	
	@Test
	public void boardSizeTests() {
		assertEquals(board.getNumRows(), ROW_SUM);
		assertEquals(board.getNumColumns(), COLUMN_SUM);
	}
	
	
	@Test
	public void doorDirectionTests() {
		RoomCell room = board.getRoomCellAt(4,1);
		assertTrue(room.isDoorway());
		assertEquals(room.doorDirection,RoomCell.DoorDirection.DOWN);
		room = board.getRoomCellAt(16,11);
		assertTrue(room.isDoorway());
		assertEquals(room.doorDirection,RoomCell.DoorDirection.UP);
		room = board.getRoomCellAt(15,16);
		assertTrue(room.isDoorway());
		assertEquals(room.doorDirection,RoomCell.DoorDirection.LEFT);
		room = board.getRoomCellAt(10,5);
		assertTrue(room.isDoorway());
		assertEquals(room.doorDirection,RoomCell.DoorDirection.RIGHT);
		room = board.getRoomCellAt(3,9);
		assertTrue(room.isDoorway());
		assertEquals(room.doorDirection,RoomCell.DoorDirection.NONE);
	}
	
	
	@Test
	public void fourRoomInitialsTests() {
		RoomCell room = board.getRoomCellAt(2,2);
		assertEquals(room.getInitial(),'G');
		room = board.getRoomCellAt(1,10);
		assertEquals(room.getInitial(),'S');
		room = board.getRoomCellAt(3,17);
		assertEquals(room.getInitial(),'C');
		room = board.getRoomCellAt(20,3);
		assertEquals(room.getInitial(),'L');
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void BadConfigLayoutTest() throws BadConfigFormatException, FileNotFoundException {
		ClueGame badgame = new ClueGame("ourBadBoardLayout.csv","ourLegend.csv");
		badgame.loadRoomConfig();
		badgame.getBoard().loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void BadConfigLegendTest() throws BadConfigFormatException, FileNotFoundException {
		ClueGame badgame = new ClueGame("ourBadBoardLayout.csv","ourBadLegend.csv");
		badgame.loadRoomConfig();
	}

}
