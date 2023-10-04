package GUI;

import java.awt.Container;
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
	private static Board board;
	private static GameMenuBar menuBar;

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

		game.takeTurn(t);
		// update the board display with the new turn
		board.addTurn(t);
		// check the new turn for victory
		if (t.isVictory()) {
			showVictoryDialog();
			reset();
		}
		else {

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
		CodeUniverse cU = game.getCodeUniverse(game.getTurnsTaken());
		AIPersonality pers = new TheProfessorAI(cU);
		if (cU.getSize() > 0) {
			choice = pers.getChoice();
		} else {
			JOptionPane.showMessageDialog(this,
					"HUMAN ERROR! HUMAN ERROR! \n" + "Your responses were inconsistent with any code. \n"
							+ "THIS is why humans will be sent to the crypto mines when...*never mind*");
			choice = getSecretCode();
			reset();
		}
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
		if (!t.isVictory()) {

		}

	}

	// clear the board and gamestate, select new secret code, and reset the AI
	// , rerandomize the guess input combo boxes
	public void reset() {
		board.clear();
		game = new AnalyzedGame(settings);
		guessPanel.resetCBoxes();

	}

	public GameSettings getSettings() {
		return settings;
	}

	public Code getSecretCode() {
		return secretCode;
	}

	private void createAndShowGUI() {
		MastermindGUI window = new MastermindGUI();
		this.setTitle("Marcus Mastermind");

		// Set this window's location and size:
		setBounds(300, 300, 500, 600);

		// create menu bar
		menuBar = new GameMenuBar();
		setJMenuBar(menuBar);
		// listen to menu selections
		menuBar.reset.addActionListener(window);
		menuBar.easyMode.addItemListener(window);
		menuBar.humanGuesser.addActionListener(window);
		menuBar.aiGuesser.addActionListener(window);
		menuBar.aiSetter.addActionListener(window);
		menuBar.humanSetter.addActionListener(window);

		// Create a Board, which is a kind of JPanel:
		board = new Board(window);

		// Create the user input panel for guessing a code
		guessPanel = new GuessInputPanel(window);
		// Create the user input dialog for entering a response
		responseDialog = new ResponseInputDialog(window);
		// Create Panel for taking an AI turn
		aiTurnPanel = new AITurnPanel(window);
		aiTurnPanel.setPreferredSize(guessPanel.getPreferredSize());

		// Add panels to window and layout:
		Container c = getContentPane();
		GroupLayout layout = new GroupLayout(c);
		c.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(board)
				.addComponent(guessPanel).addComponent(aiTurnPanel));
		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(board).addComponent(guessPanel).addComponent(aiTurnPanel));

		// check settings for human or ai player
		menuBar.humanGuesser.doClick();
		if (game.getSettings().isAiGuesser())
			menuBar.aiGuesser.doClick();

		// check settings for human or ai setter
		menuBar.aiSetter.doClick();
		if (!game.getSettings().isAiSetter())
			menuBar.humanSetter.doClick();

		// show and configure application window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
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

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MastermindGUI gameGUI = new MastermindGUI();
				gameGUI.createAndShowGUI();
			}
		});
	}

}
