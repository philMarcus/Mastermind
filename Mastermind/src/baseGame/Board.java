package baseGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {

	private GameSettings settings;
	private int turnsTaken = 0;
	//true if displaying the guess while a human chooses a response
	private boolean guessShown;

	// construct empty board display
	public Board(Game gameState) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		settings = gameState.getSettings();

		// add an empty turn panel for each "untaken" turn
		for (int i = 0; i < settings.getMaxTries(); i++) {
			//this.add(new TurnPanel(settings.getCodeLength()));
			addEmptyTurn();
		}

	}
	
	public void addEmptyTurn() {
		this.add(new TurnPanel(settings.getCodeLength()));
	}

	public void addTurn(Turn turn) {
		// add the new turn panel and remove an "untaken" turn panel at the end
		turnsTaken++;
		this.add(new TurnPanel(turn), turnsTaken - 1);
		this.remove(this.getComponentCount() - 1);
		// redraw the board
		this.revalidate();
		this.repaint();
	}
	
	public void addTurnGuess(Turn turn) {
		this.add(new TurnPanel(turn), turnsTaken);
		this.remove(this.getComponentCount() - 1);
		this.setGuessShown(true);
		// redraw the board
		this.revalidate();
		this.repaint();
	}
	public void removeTurnGuess() {
		this.remove(turnsTaken);
		guessShown=false;
	}

	public void clear() {
		if (guessShown&&turnsTaken<10) {
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
	
//	public void addEmpty() {
//		this.add(new TurnPanel(settings.getCodeLength()));
//	}

	public boolean isGuessShown() {
		return guessShown;
	}

	public void setGuessShown(boolean guessShown) {
		this.guessShown = guessShown;
	}

}


class TurnPanel extends JPanel {
	private Turn turn;
	private int w = GameSettings.getPegSlotWidth() * 2;
	private int h = GameSettings.getPegSlotHeight() * 3 / 2;

	// draw the pegs and response pins for a turn
	public TurnPanel(Turn drawTurn) {
		super();
		turn = drawTurn;
		w = w * turn.getGuess().getLength();

		this.add(new PegsPanel(turn.getGuess()));
		this.add(new PinsPanel(turn.getResponse()));
		this.setMaximumSize(new Dimension(w, h));
	}

	// draw blank turn panel
	public TurnPanel(int numEmpty) {
		super();
		w = w * numEmpty;
		this.add(new PegsPanel(numEmpty));
		this.add(new PinsPanel(new Response(numEmpty)));
		this.setMaximumSize(new Dimension(w, h));

	}

}

class PegsPanel extends JPanel {

	private Code code;
	private GridLayout layout = new GridLayout(1, 0);

	public PegsPanel(Code drawCode) {
		super();
		code = drawCode;
		this.setLayout(layout);

		for (int i = 0; i < code.getLength(); i++) {
			PegPanel p = new PegPanel(code.getPeg(i));
			this.add(p);
		}
	}

	// draw an empty panel
	public PegsPanel(int numEmpty) {
		super();

		this.setLayout(layout);
		for (int i = 0; i < numEmpty; i++) {
			this.add(new PegPanel());
		}
	}
}

/*
 * class SecretCodePanel extends PegsPanel{ public void paintComponent(Graphics
 * g) { super.paintComponent(g); // Call PegsPanel's paintComponent method // to
 * paint the background
 * 
 * }
 * 
 * }
 */
