package clueTests;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clue.BoardCell;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.RoomCell;

public class RoomLegendTests {
	
	static Board board;
	static RoomCell roomcell;

	@Before
	public void Board()
	{
		board = new Board();
		roomcell = new RoomCell();
	}
	
	
	@Test
	public void numRooms()
	{
		int actual = 9;
		Assert.assertTrue(board.getRooms().size() == actual);
				
	}
	
	@Test
	public void characterMappingTest()
	{
		Assert.assertEquals(board.getRooms(), "CONSERVATORY");
		
		
	}
	
	@Test
	public void boardSize()
	{
		int actualRows = 23;
		int actualCols = 	14;	
		
		Assert.assertEquals(board.getNumRows(), 23);
		Assert.assertEquals(board.getNumColumns(), 14);		
				
	}
	
	@Test
	public void doorDirection()
	{
		
		Assert.assertTrue(roomcell.isDoorway());
		
	}
	
	@Test
	public void roomInitials()
	{
		roomcell = board.getBoardCell(0, 0);
		
		Assert.assetEquals(roomcell.getInitial(),'G');
		//this error makes no sense we have a getInitial method
		
	}
	
	
	@Test (expected = BadConfigFormatException.class)
	public void BadConfigFormatExceptionTest() throws BadConfigFormatException 
	{
		
	}

}
