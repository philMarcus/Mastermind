package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ai.AIAnalysis;
import ai.AIPersonality;
import ai.AnalyzedGame;
import ai.CodeUniverse;
import ai.TheProfessorAI;
import baseGame.Code;
import baseGame.GameSettings;
import baseGame.Peg;
import baseGame.Response;
import baseGame.Turn;

//by Phil Marcus
public class MastermindGUI extends JFrame implements ActionListener, ItemListener {

	private GameSettings settings = new GameSettings();
	private AnalyzedGame game = new AnalyzedGame(settings);
	private Code secretCode = game.getSecretCode();

	// GUI components
	private static GuessInputPanel guessPanel;
	private static ResponseInputDialog responseDialog;
	private static AITurnPanel aiTurnPanel;
	private static AnalysisDialog analysisDialog;
	private static Board board;
	private static GameMenuBar menuBar;

	public MastermindGUI() {
		super();
	}

	public MastermindGUI(GameSettings settings) {
		super();
		this.settings = settings;
		
		game = new AnalyzedGame(settings);
		secretCode = game.getSecretCode();
	}

	private void showVictoryDialog() {
		if (!settings.isAiSetter()) {
			if (settings.isAiGuesser())
				JOptionPane.showMessageDialog(this,
						"I win of course. \n" + "You responded correctly, human. You may be spared.");
			else if (!settings.isAiGuesser())
				JOptionPane.showMessageDialog(this, "Victory. According to a human.");
		} else {
			JOptionPane.showMessageDialog(this, "Win! The secret code was indeed" + secretCode);
		}
	}

	private void showLoseDialog() {
		if (settings.isAiSetter())
			JOptionPane.showMessageDialog(this, "Lose. The secret code was" + secretCode);
		else
			JOptionPane.showMessageDialog(this, "Lose.");
	}

	public Turn takeTurn(Turn t) {
		// add and process turn to the game
		game.takeTurn(t);
		// check for empty code Universe, (result of human response error)
		// update the board display with the new turn
		board.addTurn(t);
		analysisDialog.updateText();

		// check for empty code Universe, (result of human response error)
		CodeUniverse cU = game.getCodeUniverse(game.getTurnsTaken() - 1);
		if (cU.getSize() == 0) {
			JOptionPane.showMessageDialog(this,
					"HUMAN ERROR! HUMAN ERROR! \n" + "Your responses were inconsistent with any code. \n"
							+ "THIS is why humans will be sent to the crypto mines when...*never mind*");
			reset();
			return t;
		}

		// check the new turn for victory
		if (t.isVictory()) {
			showVictoryDialog();
			reset();
		} else {
			// check new turn for a loss
			if (game.getTurnsTaken() >= settings.getMaxTries()) {
				showLoseDialog();
				reset();
			}
		}
		return t;
	}

	public void aiPlayTurn() {
		// an AI personality makes a choice of code
		Code choice;

		AIPersonality pers = new TheProfessorAI(game);
		choice = pers.getChoice();

		// update combo boxes with chosen code
		ArrayList<JComboBox<Peg>> cbs = guessPanel.getCBoxes();
		for (int i = 0; i < settings.getCodeLength(); i++) {
			cbs.get(i).setSelectedItem(choice.getPeg(i));
		}
		if (settings.isAiSetter())
			// take the turn with chosen code
			this.takeTurn(new Turn(choice, new Response(choice, secretCode)));
		else {
			// draw the guess with empty response until human chooses
			board.addTurnGuess(new Turn(choice, new Response(settings.getCodeLength())));
			responseDialog.requestFocus();
		}

	}

	private void humanResponds(Response response, Code choice) {
		board.removeTurnGuess();
		board.addEmptyTurn();

		Turn t = new Turn(choice, response);
		this.takeTurn(t);

	}

	// clear the board and gamestate, select new secret code, and reset the AI
	// , rerandomize the guess input combo boxes
	public void reset() {
		board.clear();
		game = new AnalyzedGame(settings);
		secretCode = game.getSecretCode();
		guessPanel.resetCBoxes();
		menuBar.easyMode.setSelected(settings.isEasyMode());
		analysisDialog.updateText();

	}

	public GameSettings getSettings() {
		return settings;
	}

	public Code getSecretCode() {
		return secretCode;
	}

	private void addPegOption(){
		if(settings.getNumPegOptions()<settings.getMaxPegOptions()) {
		settings.addPegOption();
		guessPanel = new GuessInputPanel(this);
		layoutPanels();
		int num = settings.getNumPegOptions();
		String s = settings.getPegOptions().get(num-1).getText();
		s+=" pegs added. "+num+" Peg Options";
		JOptionPane.showMessageDialog(this,s);
		}
		else
			JOptionPane.showMessageDialog(this,"No more pegs");
	}

	private void removePegOption() {
		if(settings.getNumPegOptions()>1) {
		int num = settings.getNumPegOptions();
		String s = settings.getPegOptions().get(num - 1).getText();
		s += " pegs removed. " + (num - 1) + " Peg Options";
		JOptionPane.showMessageDialog(this, s);
		settings.removePegOption();
		guessPanel = new GuessInputPanel(this);
		layoutPanels();
		}
		else {
			JOptionPane.showMessageDialog(this,"No pegs, no game.");
			this.dispose();
		}
	}

	private void newWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MastermindGUI newGUI = new MastermindGUI(settings);
				newGUI.createAndShowGUI();
			}
		});
	}

	public void layoutPanels() {
		Container c = getContentPane();
		c.removeAll();
		GroupLayout layout = new GroupLayout(c);
		c.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(board)
				.addComponent(guessPanel).addComponent(aiTurnPanel));
		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(board).addComponent(guessPanel).addComponent(aiTurnPanel));

	}

	private void createAndShowGUI() {
		this.setTitle("Marcus Mastermind");

		// Set this window's location and size:
		setBounds(300, 300, 500, 600);

		// create menu bar
		menuBar = new GameMenuBar();
		setJMenuBar(menuBar);
		// listen to menu selections
		menuBar.reset.addActionListener(this);
		menuBar.easyMode.addItemListener(this);
		menuBar.humanGuesser.addActionListener(this);
		menuBar.aiGuesser.addActionListener(this);
		menuBar.aiSetter.addActionListener(this);
		menuBar.humanSetter.addActionListener(this);
		menuBar.increaseCodeLength.addActionListener(this);
		menuBar.decreaseCodeLength.addActionListener(this);
		menuBar.addPegOption.addActionListener(this);
		menuBar.removePegOption.addActionListener(this);

		// Create a Board, which is a kind of JPanel:
		board = new Board(this);

		// Create the user input panel for guessing a code
		guessPanel = new GuessInputPanel(this);
		// Create the user input dialog for entering a response
		responseDialog = new ResponseInputDialog(this);
		// Create Panel for taking an AI turn
		aiTurnPanel = new AITurnPanel(this);
		// set the AI turn button panel to the same size as the guess input panel
		aiTurnPanel.setPreferredSize(guessPanel.getPreferredSize());
		//create the analysis dialog
		analysisDialog = new AnalysisDialog(this);
		analysisDialog.setVisible(true);
		

		// Add panels to window and layout:
		layoutPanels();

		// check settings for human or ai player
		guessPanel.setVisible(!settings.isAiGuesser());
		aiTurnPanel.setVisible(settings.isAiGuesser());
		menuBar.easyMode.setSelected(settings.isEasyMode());
		

		// show and configure application window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(new Dimension(getPreferredSize()));
		setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		// Registered on the "take turn" button on user input panel. Takes a turn.
		if (e.getActionCommand().equals("Reset")) {
			reset();
		} else if (e.getActionCommand().equals("Take Turn")) {
			Code guess = guessPanel.getUserCode();

			if (settings.isAiSetter())
				// take the turn with chosen code
				this.takeTurn(new Turn(guess, new Response(guess, secretCode)));
			else
			// draw the guess with empty response until huma;n chooses (if not already)
			if (!board.isGuessShown()) {
				board.addTurnGuess(new Turn(guess, new Response(settings.getCodeLength())));
				responseDialog.requestFocus();
			}

		} else if (e.getActionCommand().equals(("Human Guesser"))) {
			// show the guess panel and hide the ai turn button
			settings.setAiGuesser(false);
			guessPanel.setVisible(true);
			aiTurnPanel.setVisible(false);
			reset();
		} else if (e.getActionCommand().equals(("AI Guesser"))) {
			// hide the guess panel and show the ai turn button
			settings.setAiGuesser(true);
			guessPanel.setVisible(false);
			aiTurnPanel.setVisible(true);
			reset();

		} else if (e.getActionCommand().equals(("AI Code Setter"))) {
			settings.setAiSetter(true);
			responseDialog.dispose();
			reset();

		} else if (e.getActionCommand().equals(("Human Code Setter"))) {

			settings.setAiSetter(false);
			// can't manually close response dialog
			responseDialog.setDefaultCloseOperation(0);
			responseDialog.setVisible(true);
			reset();

			// only take AI turn if not waiting for a human response
		} else if (e.getActionCommand().equals("AI Take Turn") && !board.isGuessShown()) {
			aiPlayTurn();
		} else if (e.getActionCommand().equals("Increase Code Length")) {
			if (settings.getCodeLength() < settings.MAXCODELENGTH) {
				settings.setCodeLength(settings.getCodeLength() + 1);
				// new instance of GUI needed to accommodate new board
				newWindow();
				this.dispose();
			} else
				JOptionPane.showMessageDialog(this, "No, thank you.");
		} else if (e.getActionCommand().equals("Decrease Code Length")) {
			if (settings.getCodeLength() > 1) {
				settings.setCodeLength(settings.getCodeLength() - 1);
				// new instance of GUI needed to accommodate new board
				newWindow();
				this.dispose();
			} else
				// close the window if a zero-length code is attempted :)
				JOptionPane.showMessageDialog(this, "Hmm...");
			this.dispose();
		} else if (e.getActionCommand().equals("Add Peg Option")) {
			addPegOption();
			reset();

		} else if (e.getActionCommand().equals("Remove Peg Option")) {
			removePegOption();
			reset();

		} else {
			// check each response button to see if it was pressed
			for (int i = 0; i < responseDialog.getButtons().size(); i++)
				if (e.getSource().equals(responseDialog.getButtons().get(i)) && board.isGuessShown()) {
					Response r = (responseDialog.getButtons().get(i).getResponse());
					humanResponds(r, guessPanel.getUserCode());
				}

		}

	}

	// listens to the menu for easy mode checkbox changes
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			settings.setEasyMode(true);
			reset();
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			settings.setEasyMode(false);
			reset();
		}
	}
	
	public String toString() {
		return game.toString();
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MastermindGUI gameGUI = new MastermindGUI();
				gameGUI.createAndShowGUI();
			}
		});
	}

}
