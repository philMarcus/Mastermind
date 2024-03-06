package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import baseGame.Code;
import baseGame.GameSettings;
import baseGame.Response;
import baseGame.Turn;

@SuppressWarnings("serial")
//This class represents the graphical display of the game board
//It consists of ten rows of turn panels, with each turn
//consisting of empty or full peg slots and a pegsPanel displaying a response
public class Board extends JPanel {

	private GameSettings settings;
	private int turnsTaken = 0;
	//true if displaying the guess while a human chooses a response
	private boolean guessShown;

	// construct empty board display
	public Board(MastermindGUI gameState) {
		super();
		//use the BoxLayout to line up the turn panels vertically
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		settings = gameState.getSettings();

		// add an empty turn panel for each "untaken" turn
		for (int i = 0; i < settings.getMaxTries(); i++) {
			addEmptyTurn();
		}

	}
	
	//add a row of empty peg slots with an empty response
	public void addEmptyTurn() {
		this.add(new TurnPanel(settings.getCodeLength()));
	}

	//add a row of pegs corresponding to a turn's guess, along with
	//pins representing the response to the guess
	public void addTurn(Turn turn) {
		// add the new turn panel and 
		turnsTaken++;
		this.add(new TurnPanel(turn), turnsTaken - 1);
		//remove an "untaken" turn panel at the end
		this.remove(this.getComponentCount() - 1);
		// redraw the board
		this.revalidate();
		this.repaint();
	}
	
	//add only the first half of a turn, the guess, while leaving the turn's response empty
	public void addTurnGuess(Turn turn) {
		this.add(new TurnPanel(turn), turnsTaken);
		//rmove an "untaken" turn panel at the bottom
		this.remove(this.getComponentCount() - 1);
		this.setGuessShown(true); //flag that we're displaying half a turn
		// redraw the board
		this.revalidate();
		this.repaint();
	}
	
	//remove the half-turn in preparation for adding the full turn
	public void removeTurnGuess() {
		this.remove(turnsTaken);
		guessShown=false;
	}

	//Clears the board to display all empty turns
	public void clear() {
		//if a half-turn is displayed, replace it with an empty turn
		if (guessShown&&turnsTaken<settings.getMaxTries()) {
			removeTurnGuess();
			addEmptyTurn();
		}
		// remove all the "taken" turn panels and add that many "untaken" turn panels
		for (int i = 0; i < turnsTaken; i++) {
			this.remove(0);
			this.add(new TurnPanel(settings.getCodeLength()));
		}
		turnsTaken = 0;
		guessShown=false;
		// redraw the board
		this.revalidate();
		this.repaint();
	}
	
	public boolean isGuessShown() {
		return guessShown;
	}

	public void setGuessShown(boolean guessShown) {
		this.guessShown = guessShown;
	}

}

//This class represents the graphical display of a single turn row on the 
//game board. It consists of empty or full peg slots and a square rep
//resenting the response, that may or may not contain pins 
class TurnPanel extends JPanel {
	private Turn turn; //the game turn to display in this panel
	//use game settings for display size of peg slots
	private int w = GameSettings.getPegSlotWidth() * 2;
	private int h = GameSettings.getPegSlotHeight() * 3 / 2;

	// draw the pegs and response pins for a turn
	public TurnPanel(Turn drawTurn) {
		super();
		turn = drawTurn;
		//use the code length to determine correct display width
		w = w * turn.getGuess().getLength();
		//add a panel for the pegs and for the response pins to this panel
		this.add(new PegsPanel(turn.getGuess()));
		this.add(new PinsPanel(turn.getResponse()));
	}

	// draw blank turn panel
	public TurnPanel(int numEmpty) {
		super();
		//set the display width based on the code length
		w = w * numEmpty;
		//add an empty panel for the pegs and for the response 
		this.add(new PegsPanel(numEmpty));
		this.add(new PinsPanel(new Response(numEmpty)));
	}

}


//This class represents a panel containing the peg slots, empty or full, for one turn
class PegsPanel extends JPanel {

	private Code code; //the code to display on this panel
	//Use GridLayout to lay out the pegs in a row
	private GridLayout layout = new GridLayout(1, 0);

	public PegsPanel(Code drawCode) {
		super();
		code = drawCode;
		this.setLayout(layout);
		//add a single "pegPanel" to this panel for each peg in the code
		for (int i = 0; i < code.getLength(); i++) {
			PegPanel p = new PegPanel(code.getPeg(i));
			this.add(p);
		}
	}

	// draw an empty panel
	public PegsPanel(int numEmpty) {
		super();

		this.setLayout(layout);
		//add an empty peg slot, codelength times. 
		for (int i = 0; i < numEmpty; i++) {
			this.add(new PegPanel());
		}
	}
}


