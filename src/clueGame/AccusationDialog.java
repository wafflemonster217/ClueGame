package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AccusationDialog extends JDialog {
	public AccusationDialog(ArrayList<Card> deck, final ClueGame game) {
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

		final JComboBox<String> roomsDD = new JComboBox<String>();
		for (String s : rooms)
			roomsDD.addItem(s);
		add(roomsDD);
		
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
				if (game.checkAccusation(new Solution((String) peopleDD.getSelectedItem(), (String) weaponsDD.getSelectedItem(), (String) roomsDD.getSelectedItem()))) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "You won the game!", "Congratulations!!!", JOptionPane.OK_OPTION);
				} else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Your accusation was incorrect", "Oops", JOptionPane.ERROR_MESSAGE);
				}
				setVisible(false);
				game.getBoard().removeTargets();
				game.getBoard().repaint();
				ClueGame.isTurnOver = true;
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
