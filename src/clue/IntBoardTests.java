//package clue;
//
//import static org.junit.Assert.*;
//
//import java.util.LinkedList;
//import java.util.Set;
//
//import junit.framework.Assert;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import clue.BoardCell;
//
//
//
//public class IntBoardTests {
//
//	static IntBoard board;
//	static BoardCell boardcell;
//	
//	@BeforeClass
//	public static void BoardCell()
//	{
//		board = new IntBoard();
//		boardcell = new BoardCell();
//	}
//	
//	
//	@Test
//	public void testAdjacency0() {
//		
//		BoardCell cell = board.getCell(0,0);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
//		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
//		Assert.assertEquals(2, testList.size());
//		
//		//fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testAdjacencyTopLeft(){
//		BoardCell cell = board.getCell(0,0);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
//		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
//		Assert.assertEquals(2, testList.size());
//
//	}
//	@Test
//	public void testAdjacencyTopRight(){
//		BoardCell cell = board.getCell(0,3);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
//		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
//		Assert.assertEquals(2, testList.size());
//
//	}
//
//	@Test
//	public void testAdjacencyBottomLeft(){
//		BoardCell cell = board.getCell(3,0);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
//		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
//		Assert.assertEquals(2, testList.size());
//
//	}
//
//	@Test
//	public void testAdjacencyBottomRight(){
//		BoardCell cell = board.getCell(3,3);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
//		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
//		Assert.assertEquals(2, testList.size());
//
//	}
//
//	@Test
//	public void testAdjacencyRightEdge(){
//		BoardCell cell = board.getCell(1,3);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
//		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
//		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
//		Assert.assertEquals(3, testList.size());
//
//	}
//	@Test
//	public void testAdjacencyLeftEdge(){
//		BoardCell cell = board.getCell(1,0);
//		LinkedList<BoardCell> testList = board.getAdjList(cell);
//		Assert.assertTrue(testList.contains(board.getCell(0, 0)));
//		Assert.assertTrue(testList.contains(board.getCell(1, 1)));
//		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
//		Assert.assertEquals(3, testList.size());
//
//	}
//
//	@Test
//	public void testCalcTarget(){
//		
//	}
//	
//	@Test
//	public void testTargets0_3()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 3);
//		Set targets = board.getTargets();
//	//	Assert.assertEquals(6, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
//	}
//	@Test
//	public void testTargets0_2()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 2);
//		Set targets = board.getTargets();
//		Assert.assertEquals(3, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
//	}
//	@Test
//	public void testTargets0_1()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 1);
//		Set targets = board.getTargets();
//		Assert.assertEquals(2, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
//	}
//	@Test
//	public void testTargets0_4()
//	{
//		BoardCell cell = board.getCell(0, 0);
//		board.calcTargets(cell, 4);
//		Set targets = board.getTargets();
//		Assert.assertEquals(6, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
//	}
//	@Test
//	public void testTargets1_2()
//	{
//		BoardCell cell = board.getCell(1, 1);
//		board.calcTargets(cell, 2);
//		Set targets = board.getTargets();
//		Assert.assertEquals(6, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
//		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
//	}
//	@Test
//	public void testTargets1_1()
//	{
//		BoardCell cell = board.getCell(1, 1);
//		board.calcTargets(cell, 1);
//		Set targets = board.getTargets();
//		Assert.assertEquals(4, targets.size());
//		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
//		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
//		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
//	}
//
//
//
//}
