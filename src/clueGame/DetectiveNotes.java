package clueGame;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	public DetectiveNotes(ArrayList<Card> deck) {
		setTitle("Detective Notes");
		setSize((int) (.75 * ClueGame.WINDOW_SIZE), (int) (.75 * ClueGame.WINDOW_SIZE));
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
		JPanel panel = drawChoicesPanel(people, "People");
		add(panel);
		panel = drawGuessPanel(people, "Person Guess");
		add(panel);
		panel = drawChoicesPanel(rooms, "Rooms");
		add(panel);
		panel = drawGuessPanel(rooms, "Room Guess");
		add(panel);
		panel = drawChoicesPanel(weapons, "Weapons");
		add(panel);
		panel = drawGuessPanel(weapons, "Weapon Guess");
		add(panel);
	}
	
	public JPanel drawChoicesPanel(ArrayList<String> choices, String title)	{	
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout((int) (((choices.size() + 1) / 2) + .5), 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		for (String name : choices) {
			// Create the buttons
			JCheckBox box = new JCheckBox(name);
			
			// Set them to not be selected by default
			box.setSelected(false);
			
			// Add the buttons to the panel
			panel.add(box);
		}
		return panel;
	}

	public JPanel drawGuessPanel(ArrayList<String> guesses, String title)	{	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		panel.setLayout(new GridLayout(1, 2));
		JComboBox<String> guess = new JComboBox<String>();
		guess.setFont(new Font("TimesRoman", Font.BOLD, 20));
		guess.addItem("Unsure");
		
		for (String name : guesses) {
			guess.addItem(name);
		}
		
		panel.add(guess);
		return panel;
	}
}
