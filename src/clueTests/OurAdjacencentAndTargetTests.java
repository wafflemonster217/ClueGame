package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class OurAdjacencentAndTargetTests {
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
	public void testWalkwaysAdjacent() {
		adjList = board.getAdjList(15,8);
		assertTrue(adjList.contains(board.getCellAt(16, 8))); 
		assertTrue(adjList.contains(board.getCellAt(14, 8))); 
		assertTrue(adjList.contains(board.getCellAt(15, 9))); 
		assertTrue(adjList.contains(board.getCellAt(15, 7)));
	}
	

	
	@Test
	public void testEdgeCases3_20() {
		adjList = board.getAdjList(3,20);
		assertTrue(adjList.contains(board.getCellAt(2, 20)));
		assertTrue(adjList.contains(board.getCellAt(4, 20)));
	}
	
	@Test
	public void testEdgeCases6_20() {
		adjList = board.getAdjList(6,20);
		assertTrue(adjList.contains(board.getCellAt(6,19)));
		assertTrue(adjList.contains(board.getCellAt(5,20)));
	}
	
	@Test
	public void testEdgeCases22_8() {
		adjList = board.getAdjList(22,8);
		assertTrue(adjList.contains(board.getCellAt(22, 9)));
		assertTrue(adjList.contains(board.getCellAt(22, 7)));
		assertTrue(adjList.contains(board.getCellAt(21, 8)));
	}
	
	@Test
	public void testEdgeCases6_0() {
		adjList = board.getAdjList(6,0);
		assertTrue(adjList.contains(board.getCellAt(5, 0)));
		assertTrue(adjList.contains(board.getCellAt(6, 1)));
	}
	
	@Test
	public void testNextToRoom15_2() {
		adjList = board.getAdjList(15,2);
		assertTrue(adjList.contains(board.getCellAt(15, 1)));
		assertTrue(adjList.contains(board.getCellAt(15, 3)));
		assertTrue(adjList.contains(board.getCellAt(16, 2))); 
		assertTrue(!adjList.contains(board.getCellAt(14, 2)));
		
	}
	
	@Test
	public void testNextToRoom6_17() {
		adjList = board.getAdjList(6,17);
		assertTrue(adjList.contains(board.getCellAt(6, 16)));
		assertTrue(adjList.contains(board.getCellAt(6, 18)));
		assertTrue(!adjList.contains(board.getCellAt(7, 17))); 
		assertTrue(!adjList.contains(board.getCellAt(5, 17))); 
	}
	
	@Test
	public void testNextToRoom22_9() {
		adjList = board.getAdjList(22,9);
		assertTrue(adjList.contains(board.getCellAt(22,8)));
		assertTrue(adjList.contains(board.getCellAt(21,9)));
		assertTrue(!adjList.contains(board.getCellAt(22,10))); 	
	}
	

	@Test
	public void testNextToDoorway_right() {
		adjList = board.getAdjList(10,6);
		assertTrue(adjList.contains(board.getCellAt(10, 5)));
		assertTrue(adjList.contains(board.getCellAt(10, 7)));
		assertTrue(adjList.contains(board.getCellAt(9, 6)));
		assertTrue(adjList.contains(board.getCellAt(11, 6)));
		
	}
	
	@Test
	public void testNextToDoorway_left() {
		adjList = board.getAdjList(0,15);
		assertTrue(adjList.contains(board.getCellAt(0, 16)));
		assertTrue(adjList.contains(board.getCellAt(0, 14)));
		assertTrue(adjList.contains(board.getCellAt(1, 15)));
	}
	
	@Test
	public void testNextToDoorway_down() {
		adjList = board.getAdjList(4,12);
		assertTrue(adjList.contains(board.getCellAt(3, 12))); 
		assertTrue(adjList.contains(board.getCellAt(5, 12))); 
		assertTrue(adjList.contains(board.getCellAt(4, 11)));
		assertTrue(adjList.contains(board.getCellAt(4, 13)));
		
	}
	
	@Test
	public void testNextToDoorway_up() {
		adjList = board.getAdjList(17,17);
		assertTrue(adjList.contains(board.getCellAt(18, 17)));
		assertTrue(adjList.contains(board.getCellAt(17, 16))); 
		assertTrue(adjList.contains(board.getCellAt(17, 18)));
		assertTrue(!adjList.contains(board.getCellAt(16, 17)));
		
	}
	
	@Test
	public void testWalkwayNextToDoorWay_wrongDirection() {
		adjList = board.getAdjList(14,16);
		assertTrue(adjList.contains(board.getCellAt(14,15)));
		assertTrue(adjList.contains(board.getCellAt(13,16))); 
		assertTrue(!adjList.contains(board.getCellAt(14,17)));
		assertTrue(!adjList.contains(board.getCellAt(15,16))); 
	}
	
	@Test
	public void testCellInsideDoorWay3_12() {
		adjList = board.getAdjList(3,12);
		assertTrue(adjList.contains(board.getCellAt(4,12))); 
		assertTrue(!adjList.contains(board.getCellAt(2, 12))); 
		assertTrue(!adjList.contains(board.getCellAt(3, 13)));
		assertTrue(!adjList.contains(board.getCellAt(3, 11))); 
	}
	
	@Test
	public void testCellInsideDoorWay10_16() {
		adjList = board.getAdjList(10,16);
		assertTrue(adjList.contains(board.getCellAt(10,15)));
		assertTrue(!adjList.contains(board.getCellAt(10,17))); 
		assertTrue(!adjList.contains(board.getCellAt(11,16))); 
		assertTrue(!adjList.contains(board.getCellAt(9,16))); 
	}
	
	@Test
	public void testTargetWalkways() {
		board.calcTargets(17,20,2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(17,18)));
		
		board.calcTargets(22,14,3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(19,14)));
		assertTrue(targets.contains(board.getCellAt(20,15)));
		assertTrue(targets.contains(board.getCellAt(21,14)));
		assertTrue(targets.contains(board.getCellAt(22,15)));
		
		board.calcTargets(16,0,3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(15,0)));
		assertTrue(targets.contains(board.getCellAt(15,2)));
		assertTrue(targets.contains(board.getCellAt(16,1)));
		assertTrue(targets.contains(board.getCellAt(16,3)));

		
		board.calcTargets(6,8,5);
		targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCellAt(2,7)));
		assertTrue(targets.contains(board.getCellAt(6,13)));
		
	}
	
	@Test
	public void testTargetEnterRoom() {
		board.calcTargets(8,6,3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCellAt(10,5)));
		
		board.calcTargets(19,15,1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(19,16)));
	}
	
	@Test
	public void testTargetEnterRoomShortPath() {
		board.calcTargets(0,20,2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(0,19)));

		
		board.calcTargets(3,5,3);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(2,4)));
	}
	
	@Test
	public void testTargetExitRoom() {
		board.calcTargets(17,6,2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(17,8)));
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		
		board.calcTargets(19,16,1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(19,15)));
	}

}
