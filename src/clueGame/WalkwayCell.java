package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int r, int c) {
		super(r, c);
	}

	@Override
	public Boolean isWalkway() {
		return true;
	}

	@Override
	public void draw(Graphics g, Board board) {
		g.setColor(Color.CYAN);
		g.fillRect(ClueGame.CELL_SIZE * column, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(ClueGame.CELL_SIZE * column, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
	}
	
	
}
