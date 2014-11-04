package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class BoardCell {
	protected int column;
	protected int row;

	public BoardCell(int r, int c) {
		row = r;
		column = c;
	}
	
	public Boolean isWalkway() {
		return false;	
	}
	
	public Boolean isRoom() {
		return false;
	}
	
	public Boolean isDoorway() {
		return false;
	}

	@Override
	public String toString() {
		return "BoardCell [column=" + column + ", row=" + row + "]";
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(ClueGame.CELL_SIZE * column, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}
	
	public abstract void draw(Graphics g, Board board);
	public abstract void drawAsTarget(Graphics g, Board board);
}
