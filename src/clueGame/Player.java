package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	protected ArrayList<Card> hand;
	protected char lastRoomVisited;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.row = row;
		this.col = col;
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(color.trim());     
			this.color = (Color) field.get(null);
		} catch (Exception e) {  
			this.color = null; // Not defined
			System.out.println("Error setting player color for: " + name);
		}
		hand = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		int random = (int) (Math.random() * (hand.size() + 1));
		//Go through hand randomly to ensure random result
		for (int i = random; i < random + hand.size(); i++) {
			if (hand.get(i % hand.size()).name.equals(person) ||
				hand.get(i % hand.size()).name.equals(room) ||
				hand.get(i % hand.size()).name.equals(weapon)) {
				
					return hand.get(i % hand.size());
			}
		}
		
		//If no match found return null
		return null;
	}
	
	public void dealCard(Card card) {
		hand.add(card);
	}
	
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Player)) {
			return false;	
		}
		if (obj == this) {
			return true;
		}
		return this.name.equals(((Player) obj).name);
	}
	
	public void draw(Graphics g, ArrayList<Player> players) {
		g.setColor(color);
		Graphics2D g2d = (Graphics2D) g;
		
		//Check if in the same location as another player
		ArrayList<Player> sameSpot = new ArrayList<Player>();
		for (Player player : players) {
			if (this.row == player.getRow() && this.col == player.getCol()) {
				sameSpot.add(player);
			}
		}
		
		if (sameSpot.size() == 1) {
			// Assume x, y, and diameter are instance variables.
			Ellipse2D.Double circle = new Ellipse2D.Double(ClueGame.CELL_SIZE * col, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
			g2d.fill(circle);
			g.setColor(Color.BLACK);
			g.drawOval(ClueGame.CELL_SIZE * col, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE);
		} else {
			int startAngle = 0;
			for (Player player : sameSpot) {
				player.draw(g, sameSpot.size(), startAngle);
				startAngle += 360 / sameSpot.size();
			}
		}
	}
	
	public void draw(Graphics g, int numSameSpot, int startAngle) {
		g.setColor(color);
		Graphics2D g2d = (Graphics2D) g;
		Arc2D arc = new Arc2D.Double(ClueGame.CELL_SIZE * col, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE, startAngle, 360 / numSameSpot, Arc2D.PIE);
		g2d.fill(arc);
		g.setColor(Color.BLACK);
		g.drawArc(ClueGame.CELL_SIZE * col, ClueGame.CELL_SIZE * row, ClueGame.CELL_SIZE, ClueGame.CELL_SIZE, startAngle, 360 / numSameSpot);
	}
	
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
