package clue;
import java.awt.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

//modify for timestamp


public class IntBoard {

	Map<BoardCell, LinkedList<BoardCell>> adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private LinkedList<BoardCell> adjList= new LinkedList<BoardCell>();
	private BoardCell[][] grid = new BoardCell[4][4];
	boolean clearList = true;

	
	
	public IntBoard()
	{
		for(int r=0; r<4; r++)
		{
			
			for(int c=0; c<4; c++)
			{
				BoardCell temp = new BoardCell(r,c);
		     	grid[r][c] = temp;
			}
						
		}
		
		for(int r=0; r<4; r++)
		{
			
			for(int c=0; c<4; c++)
			{
				adjMtx.put(getCell(r,c), calcAdjacencies(getCell(r,c)));
	
			}
						
		}
		
		
	}
	
	public LinkedList<BoardCell> calcAdjacencies(BoardCell aCell)
	{
				
		adjList = new LinkedList<BoardCell>();
		
		if(aCell.getRow() - 1 >= 0)
		{
			adjList.add(getCell(aCell.getRow()-1,aCell.getCol()));
		}
		
		if(aCell.getRow() + 1 <= 3)
		{
			adjList.add(getCell(aCell.getRow()+1,aCell.getCol()));
		}
		
		if(aCell.getCol() -1 >= 0)
		{
			adjList.add(getCell(aCell.getRow(),aCell.getCol()-1));
		}
		
		if(aCell.getCol() +1 <= 3)
		{
			adjList.add(getCell(aCell.getRow(),aCell.getCol() +1));
		}
				
		
		return adjList;
		
	}
	
	public void calcTargets(BoardCell aCell, int roll)
	{
		if(clearList)
		{
<<<<<<< HEAD
=======
			System.out.println("clearing list" + targets);
>>>>>>> e1cc50371e4522ae556fe33a6a1375051136146a
		visited.clear();
		targets.clear();
		}
		
		visited.add(aCell);
		findAllTargets(aCell,roll);
		
		
	}
	
	public void findAllTargets(BoardCell aCell, int roll)
	{
			
		LinkedList <BoardCell> temp = getAdjList(aCell);
		LinkedList<BoardCell> tempAdj = new LinkedList<BoardCell>();
<<<<<<< HEAD
	
		
		
=======
			
>>>>>>> e1cc50371e4522ae556fe33a6a1375051136146a
		for( BoardCell b : temp)
		{
			//System.out.println(temp);
			
			if(!visited.contains((b)))
			{
		
				tempAdj.add(b);
			}
		}
				
		//System.out.println("temp adj" + tempAdj);
		//System.out.println("visited is " + visited);
		
		
		for(BoardCell b : tempAdj)
		{
			visited.add(b);		
			
			if(roll == 1)
			{
				//System.out.println("added to target list");
				targets.add(b);
				clearList = true;
									
			}
						
			else
			{
				clearList = false;
<<<<<<< HEAD
		        calcTargets(b, roll-1);
=======
					calcTargets(b, roll-1);
>>>>>>> e1cc50371e4522ae556fe33a6a1375051136146a

			}
				
			visited.remove(b);
			
		}
				

		System.out.println("targets" + targets);
	}
	
	
<<<<<<< HEAD
	
	
=======
>>>>>>> e1cc50371e4522ae556fe33a6a1375051136146a
	public Set<BoardCell> getTargets()
	{
		
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell aCell)
	{
				
		return adjMtx.get(aCell);

	}
	
	public BoardCell getCell(int r, int c)
	{
	
		return grid[r][c];
			
	}
	
	
	public static void main(String[] args)
	{
		//System.out.println("this test");
		
		IntBoard a = new IntBoard();
		
		
		
		//a.calcTargets(a.getCell(0,0),1);	
	   a.calcTargets(a.getCell(0,0),4);
		//a.calcTargets(a.getCell(0,0),3);
<<<<<<< HEAD
=======
	   
	   a.calcTargets(a.getCell(0, 0),1);
>>>>>>> e1cc50371e4522ae556fe33a6a1375051136146a
		
		
	
		
	}
	
	
}
