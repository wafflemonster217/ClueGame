package clueGame;

public class HumanPlayer extends Player {
	ClueGame game;
	
	public HumanPlayer(ClueGame game, String name, String color, int row, int col) {
		super(name, color, row, col);
		this.game = game;
	}
	
	public void guess() {
		GuessDialog gD = new GuessDialog(game.getDeck(), game);
		gD.setVisible(true);
	}
}
