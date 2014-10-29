package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	public DetectiveNotes(ArrayList<Card> deck) {
		setTitle("Detective Notes");
		setSize(600, 800);
		setLayout(new GridLayout(3, 2));
		
		ArrayList<String> people = new ArrayList<String>();
		ArrayList<String> rooms = new ArrayList<String>();
		ArrayList<String> weapons = new ArrayList<String>();
		
		for (Card card : deck) {
			CardType type = card.type;
			switch (type) {
				case WEAPON: weapons.add(card.name);
					break;
				case PERSON:  people.add(card.name);
					break;
				case ROOM: rooms.add(card.name);
					break;
			}
		}
		JPanel panel = drawPeoplePanel(people);
		add(panel);
		panel = drawPersonGuess(people);
		add(panel);
		panel = drawRoomsPanel(rooms);
		add(panel);
		panel = drawRoomGuess(rooms);
		add(panel);
		panel = drawWeaponsPanel(weapons);
		add(panel);
		panel = drawWeaponGuess(weapons);
		add(panel);
	}
	
	public JPanel drawPeoplePanel(ArrayList<String> people)	{	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		for (String name : people) {

			// Create the buttons
			JCheckBox box = new JCheckBox(name);
			
			// Set them to not be selected by default
			box.setSelected(false);
			
			// Add the buttons to the panel
			panel.add(box);
		}
		return panel;

	}
	
	public JPanel drawRoomsPanel(ArrayList<String> rooms)	{	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		for (String name : rooms) {

			// Create the buttons
			JCheckBox box = new JCheckBox(name);
			
			// Set them to not be selected by default
			box.setSelected(false);
			
			// Add the buttons to the panel
			panel.add(box);
		}
		return panel;

	}
	
	public JPanel drawWeaponsPanel(ArrayList<String> weapons)	{	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		for (String name : weapons) {

			// Create the buttons
			JCheckBox box = new JCheckBox(name);
			
			// Set them to not be selected by default
			box.setSelected(false);
			
			// Add the buttons to the panel
			panel.add(box);
		}
		return panel;

	}

	public JPanel drawPersonGuess(ArrayList<String> people)	{	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		panel.setLayout(new GridLayout(1, 2));
		JComboBox<String> guess = new JComboBox<String>();
		//guess.setPreferredSize(new Dimension(150, 180));
		guess.setFont(new Font("TimesRoman", Font.BOLD, 32));
		guess.addItem("Unsure");
		
		for (String name : people)
			guess.addItem(name);
		
		panel.add(guess);
		return panel;
	}
	
	public JPanel drawRoomGuess(ArrayList<String> rooms)	{	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		panel.setLayout(new GridLayout(1, 2));
		JComboBox<String> guess = new JComboBox<String>();
		//guess.setPreferredSize(new Dimension(150, 180));
		guess.setFont(new Font("TimesRoman", Font.BOLD, 32));
		guess.addItem("Unsure");
		
		for (String name : rooms)
			guess.addItem(name);
		
		panel.add(guess);
		return panel;
	}
	
	public JPanel drawWeaponGuess(ArrayList<String> weapons)	{	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		panel.setLayout(new GridLayout(1, 2));
		JComboBox<String> guess = new JComboBox<String>();
		//guess.setPreferredSize(new Dimension(150, 180));
		guess.setFont(new Font("TimesRoman", Font.BOLD, 32));
		guess.addItem("Unsure");
		
		for (String name : weapons)
			guess.addItem(name);
			
		panel.add(guess);
		return panel;
	}

}
