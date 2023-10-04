package baseGame;

import java.util.ArrayList;

public class GameEngine {


	private ArrayList<Turn> turns = new ArrayList<Turn>();
	private GameSettings settings = new GameSettings();
	private Code secretCode;
	
	public GameEngine(GameSettings settings, Code secretCode) {
		this.settings = settings;
		this.secretCode = secretCode;
	}

	public GameEngine(GameSettings settings) {
		this.settings = settings;
		this.secretCode = new Code(settings.getCodeLength(), settings.getPegOptions());
	}
	
	public GameEngine() {
		settings = new GameSettings();
		this.secretCode = new Code(settings.getCodeLength(), settings.getPegOptions());
	}
	
	public boolean takeTurn(Turn t) {
		// add a new turn to the gamestate, which consists of a guessed code and a
		// calculated response
		turns.add(t);
		return t.isVictory();

	}

	public boolean takeTurn(Code c) {
		// When given a code (without a response) as a parameter, takeTurn will
		// generate a response from the secret code
		Turn t = new Turn(c, new Response(c, secretCode));
		takeTurn(t);
		return t.isVictory();
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