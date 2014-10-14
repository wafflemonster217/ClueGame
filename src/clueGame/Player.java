package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> hand;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.row = row;
		this.col = col;
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(color.trim());     
			this.color = (Color)field.get(null); } 
		catch (Exception e) {  
			this.color = null; // Not defined } 
			System.out.println("Error setting player color for: " + name);
		}
		hand = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		//TODO remove this
		return new Card("card", CardType.PERSON);
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
	
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	
}
