package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuessDialog extends JDialog {
	public GuessDialog(ArrayList<Card> deck, final ClueGame game) {
		setTitle("Make an Accusation");
		setSize((int) (ClueGame.WINDOW_SIZE * .5), (int) (ClueGame.WINDOW_SIZE * .5));
		setLayout(new GridLayout(4, 2));
		
		ArrayList<String> people = new ArrayList<String>();
		ArrayList<String> weapons = new ArrayList<String>();
		ArrayList<String> rooms = new ArrayList<String>();
		
		for (Card card : deck) {
			switch (card.type) {
				case PERSON:
					people.add(card.name);
					break;
				case WEAPON:
					weapons.add(card.name);
					break;
				case ROOM:
					rooms.add(card.name);
					break;
					default:
						break;
			}
		}
		
		add(new JLabel("Room"));
		JPanel currentRoomPanel = new JPanel();
		final JLabel currentRoomLabel = new JLabel(game.getLegend().get(game.getPlayers().get(0).lastRoomVisited));
		currentRoomPanel.add(currentRoomLabel);
		currentRoomPanel.setBackground(Color.decode("#C1C1C1"));
		add(currentRoomPanel);
		
		add(new JLabel("Person"));
		final JComboBox peopleDD = new JComboBox<String>();
		for (String s : people)
			peopleDD.addItem(s);
		add(peopleDD);
		
		add(new JLabel("Weapon"));
		final JComboBox<String> weaponsDD = new JComboBox<String>();
		for (String s : weapons)
			weaponsDD.addItem(s);
		add(weaponsDD);
			
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.checkAccusation(new Solution((String) peopleDD.getSelectedItem(), (String) weaponsDD.getSelectedItem(), currentRoomLabel.getText()));
			}
		});
		add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(cancelButton);
		
	}
}
