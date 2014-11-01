package clueGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ControlPanel extends JPanel {
	private JTextField dieField;
	private JTextField guessField;
	private JTextField turnField;
	private JTextField guessResultField;

	public ControlPanel() {
		//setSize(1000, 250);
		setLayout(new BorderLayout());
		JPanel panel = createInfoPanel();
		add(panel, BorderLayout.CENTER);
		panel = createTopPanel();
		add(panel, BorderLayout.NORTH);
		
		
	}

	//Creates the bottom panel with the three information areas
	private JPanel createInfoPanel() {
		JPanel panel = new JPanel();

		//panel.setLayout(new GridLayout(1, 3));
		//panel.setPreferredSize(new Dimension (1000, 60));
		
		JPanel smallPanel = createDiePanel();
		panel.add(smallPanel, BorderLayout.WEST);
		
		smallPanel = createGuessPanel();
		panel.add(smallPanel, BorderLayout.CENTER);
		
		smallPanel = createResultPanel();
		panel.add(smallPanel, BorderLayout.EAST);

		return panel;
	}
	
	//Creates top panel with turn panel and two buttons
	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension (1000, 120));
		panel.setLayout(new GridLayout(1, 3));
		
		JPanel smallPanel = createTurnPanel();
		panel.add(smallPanel);
		
		JButton next = new JButton("Next Player");
		next.setForeground(Color.GRAY);
		next.setFont(new Font("TimesRoman", Font.BOLD, 20));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("performed");
			} 
		});

		JButton accuse = new JButton("Make an Accusation");
		accuse.setForeground(Color.GRAY);
		accuse.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		panel.add(next);
		panel.add(accuse);
		
		return panel;
	}

	//Created Die panel
	private JPanel createDiePanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension (150, 50));
		JLabel nameLabel = new JLabel("Roll");
		dieField = new JTextField(20);
		dieField.setEditable(false);
		panel.setLayout(new GridLayout(1, 2));
		panel.add(nameLabel);
		panel.add(dieField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		return panel;
	}
	
	//Creates Guess Panel
	private JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension (450, 70));
		JLabel nameLabel = new JLabel("Guess");
		guessField = new JTextField(20);
		guessField.setEditable(false);
		panel.setLayout(new GridLayout(2, 1));
		panel.add(nameLabel);
		panel.add(guessField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		return panel;
	}
	
	//Creates Guess Result panel
	private JPanel createResultPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension (300, 50));
		JLabel nameLabel = new JLabel("Response");
		guessResultField = new JTextField(30);
		guessResultField.setEditable(false);
		panel.setLayout(new GridLayout(1, 2));
		panel.add(nameLabel);
		panel.add(guessResultField);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "GuessResult"));
		return panel;
	}
	
	//Creates whose turn panel
	private JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
		nameLabel.setPreferredSize(new Dimension (100, 10));
		turnField = new JTextField(20);
		turnField.setEditable(false);
		panel.add(nameLabel);
		panel.add(turnField);
		return panel;
	}
}
