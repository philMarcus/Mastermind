package baseGame;

import java.util.ArrayList;

//This class represents a single game of Mastermind. 

public class Game {
	//The game consists of an arraylist of Turns
	private ArrayList<Turn> turns = new ArrayList<Turn>();
	
	//The various game options are stored in a GameSettings variable
	private GameSettings settings = new GameSettings();
	
	//This is the code that the player is trying to guess
	private Code secretCode;

	// begin a new game with a predetermined secret code
	public Game(GameSettings settings, Code secretCode) {
		this.settings = settings;
		this.secretCode = secretCode;
	}

	// begin a new game with a random secret code
	public Game(GameSettings settings) {
		this.settings = settings;
		//can't have easy mode when theres too few peg options for the length.
		if(settings.isEasyMode() && settings.getNumPegOptions()<settings.getCodeLength())
			settings.setEasyMode(false);
		
		this.secretCode = new Code(settings.getCodeLength(), settings.getPegOptions(), settings.isEasyMode());
	}

	// begin a new game with a random secret code and the default game settings
	public Game() {
		settings = new GameSettings();
		this.secretCode = new Code(settings.getCodeLength(), settings.getPegOptions());
	}

	public Turn takeTurn(Turn t) {
		// add a new turn to the gamestate, which consists of a guessed code and a
		// calculated response
		turns.add(t);
		return t;

	}

	public Turn takeTurn(Code c) {
		// When given a code (without a response) as a parameter, takeTurn will
		// generate a response from the secret code
		Turn t = new Turn(c, new Response(c, secretCode));
		turns.add(t);
		return t;
	}

	public ArrayList<Turn> getTurns() {
		return turns;
	}

	public GameSettings getSettings() {
		return settings;
	}

	public Code getSecretCode() {
		return secretCode;
	}
}