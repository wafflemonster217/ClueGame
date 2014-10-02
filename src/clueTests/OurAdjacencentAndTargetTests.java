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
		//1/1
		adjList = board.getAdjList(18,16);
		assertTrue(adjList.contains(board.getCellAt(18, 15))); 
		assertTrue(adjList.contains(board.getCellAt(18, 17))); 
		assertTrue(adjList.contains(board.getCellAt(17, 16))); 
		assertTrue(adjList.contains(board.getCellAt(19, 16)));
	}
	

	
	@Test
	public void testEdgeCases14_22() {
		adjList = board.getAdjList(14,22);
		assertTrue(adjList.contains(board.getCellAt(14, 21)));
		assertTrue(adjList.contains(board.getCellAt(15, 22)));
	}
	
	@Test
	public void testEdgeCases0_11() {
		adjList = board.getAdjList(0,11);
		assertTrue(adjList.contains(board.getCellAt(1, 11)));
	}
	
	@Test
	public void testEdgeCases21_16() {
		adjList = board.getAdjList(21,16);
		assertTrue(adjList.contains(board.getCellAt(21, 15)));
		assertTrue(adjList.contains(board.getCellAt(21, 17)));
		assertTrue(adjList.contains(board.getCellAt(20, 16)));
	}
	
	@Test
	public void testEdgeCases6_22() {
		adjList = board.getAdjList(6,22);
		assertTrue(adjList.contains(board.getCellAt(6, 21)));
	}
	
	@Test
	public void testNextToRoom5_2() {
		adjList = board.getAdjList(5,2);
		assertTrue(adjList.contains(board.getCellAt(5, 1)));
		assertTrue(adjList.contains(board.getCellAt(5, 3)));
		assertTrue(adjList.contains(board.getCellAt(6, 2))); 
		assertTrue(!adjList.contains(board.getCellAt(4, 2))); // doesn't contain (4,2)
		
	}
	
	@Test
	public void testNextToRoom8_0() {
		adjList = board.getAdjList(8,0);
		assertTrue(adjList.contains(board.getCellAt(7, 0)));
		assertTrue(!adjList.contains(board.getCellAt(9, 0))); // doesn't contain (9,0)
		assertTrue(!adjList.contains(board.getCellAt(8, 1))); // doesn't contain (8,1)		
	}
	
	@Test
	public void testNextToRoom0_18() {
		adjList = board.getAdjList(0,18);
		assertTrue(adjList.contains(board.getCellAt(0, 17)));
		assertTrue(adjList.contains(board.getCellAt(1, 18)));
		assertTrue(!adjList.contains(board.getCellAt(0, 19))); // doesn't contain (0,19)		
	}
	

	@Test
	public void testNextToDoorway_right() {
		adjList = board.getAdjList(18,6);
		assertTrue(adjList.contains(board.getCellAt(18, 5)));
		assertTrue(adjList.contains(board.getCellAt(18, 7)));
		assertTrue(adjList.contains(board.getCellAt(17, 6)));
		assertTrue(adjList.contains(board.getCellAt(19, 6)));
		
	}
	
	@Test
	public void testNextToDoorway_down() {
		adjList = board.getAdjList(5,9);
		assertTrue(adjList.contains(board.getCellAt(4, 9)));
		assertTrue(adjList.contains(board.getCellAt(6, 9)));
		assertTrue(adjList.contains(board.getCellAt(5, 8)));
		assertTrue(adjList.contains(board.getCellAt(5, 10)));
		
	}
	
	@Test
	public void testNextToDoorway_left() {
		adjList = board.getAdjList(3,18);
		assertTrue(adjList.contains(board.getCellAt(3, 17))); 
		assertTrue(adjList.contains(board.getCellAt(3, 19))); 
		assertTrue(adjList.contains(board.getCellAt(4, 18)));
		assertTrue(adjList.contains(board.getCellAt(2, 18)));
		
	}
	
	@Test
	public void testNextToDoorway_down2() {
		adjList = board.getAdjList(5,3);
		assertTrue(adjList.contains(board.getCellAt(4, 3)));
		assertTrue(adjList.contains(board.getCellAt(5, 2))); 
		assertTrue(adjList.contains(board.getCellAt(5, 4)));
		assertTrue(adjList.contains(board.getCellAt(6, 3)));
		
	}
	
	@Test
	public void testWalkwayNextToDoorWay_wrongDirection() {
		adjList = board.getAdjList(15,17);
		assertTrue(adjList.contains(board.getCellAt(15, 16)));
		assertTrue(adjList.contains(board.getCellAt(15, 18))); 
		assertTrue(adjList.contains(board.getCellAt(14, 17)));
		assertTrue(!adjList.contains(board.getCellAt(16, 16))); 
	}
	
	@Test
	public void testWalkwayNextToDoorWay_wrongDirection2() {
		adjList = board.getAdjList(17,17);
		assertTrue(adjList.contains(board.getCellAt(17, 16))); 
		assertTrue(adjList.contains(board.getCellAt(18, 17))); 
		assertTrue(!adjList.contains(board.getCellAt(16, 16))); 
		assertTrue(!adjList.contains(board.getCellAt(17, 17))); 
	}
	
	@Test
	public void testCellInsideDoorWay4_3() {
		adjList = board.getAdjList(4,3);
		assertTrue(!adjList.contains(board.getCellAt(4, 2))); 
		assertTrue(!adjList.contains(board.getCellAt(4, 4))); 
		assertTrue(adjList.contains(board.getCellAt(5, 3)));
		assertTrue(!adjList.contains(board.getCellAt(3, 3))); 
	}
	
	@Test
	public void testCellInsideDoorWay15_11() {
		adjList = board.getAdjList(15,11);
		assertTrue(adjList.contains(board.getCellAt(14,11)));
		assertTrue(!adjList.contains(board.getCellAt(16,11))); 
		assertTrue(!adjList.contains(board.getCellAt(15,10))); 
		assertTrue(!adjList.contains(board.getCellAt(15,12))); 
	}
	
	@Test
	public void testTargetWalkways() {
		board.calcTargets(18,16,1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17,16)));
		assertTrue(targets.contains(board.getCellAt(19,16)));
		assertTrue(targets.contains(board.getCellAt(18,15)));
		assertTrue(targets.contains(board.getCellAt(18,17)));
		
		board.calcTargets(12,0,5);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(13,0)));
		assertTrue(targets.contains(board.getCellAt(15,0)));
		assertTrue(targets.contains(board.getCellAt(15,2)));
		assertTrue(targets.contains(board.getCellAt(16,1)));
		
		board.calcTargets(6,11,3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(3,11)));
		assertTrue(targets.contains(board.getCellAt(5,11)));
		assertTrue(targets.contains(board.getCellAt(5,9)));
		assertTrue(targets.contains(board.getCellAt(5,13)));
		assertTrue(targets.contains(board.getCellAt(6,8)));
		assertTrue(targets.contains(board.getCellAt(6,10)));
		assertTrue(targets.contains(board.getCellAt(6,12)));
		assertTrue(targets.contains(board.getCellAt(6,14)));
		
		board.calcTargets(15,22,6);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(15,16)));
		assertTrue(targets.contains(board.getCellAt(15,18)));
		assertTrue(targets.contains(board.getCellAt(15,20)));
		assertTrue(targets.contains(board.getCellAt(14,17)));
		assertTrue(targets.contains(board.getCellAt(14,19)));
		assertTrue(targets.contains(board.getCellAt(14,21)));
		
	}
	
	@Test
	public void testTargetEnterRoom() {
		board.calcTargets(1, 11, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(2,12)));
		
		board.calcTargets(15,3,1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14,3)));
	}
	
	@Test
	public void testTargetEnterRoomShortPath() {
		board.calcTargets(10,7,2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(9,6)));
		assertTrue(targets.contains(board.getCellAt(10,6)));
		
		board.calcTargets(5,4,3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(4,3)));
	}
	
	@Test
	public void testTargetExitRoom() {
		board.calcTargets(5,15,2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 14)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		
		board.calcTargets(3,19,3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(1,18)));
		assertTrue(targets.contains(board.getCellAt(2,17)));
		assertTrue(targets.contains(board.getCellAt(3,18)));
		assertTrue(targets.contains(board.getCellAt(4,17)));
		assertTrue(targets.contains(board.getCellAt(5,18)));
	}

}
