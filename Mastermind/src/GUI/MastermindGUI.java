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
//This class is the main window for the game of Marcus Mastermind.
//It contains a menu bar, a graphical game board, and a user input panel
//It listens and reacts to the items in the menu bar and the take turn button.
public class MastermindGUI extends JFrame implements ActionListener, ItemListener {

	// initialize default game settings, a new analyzed game, and a new secret code
	private GameSettings settings = new GameSettings();
	private AnalyzedGame game = new AnalyzedGame(settings);
	private Code secretCode = game.getSecretCode();

	// GUI components
	private static GuessInputPanel guessPanel; // user code entry panel
	// when a human is the code setter, this is how the response is entered
	private static ResponseInputDialog responseDialog;
	private static AITurnPanel aiTurnPanel; // button for AI player to take its turn
	private static AnalysisDialog analysisDialog; // window to display game analysis
	private static Board board; // the game board display
	private static GameMenuBar menuBar;

	// creates a new GUI with default game settings
	public MastermindGUI() {
		super();
	}

	// creates a new GUI with given game settings
	public MastermindGUI(GameSettings settings) {
		super();
		this.settings = settings;
		game = new AnalyzedGame(settings);
		secretCode = game.getSecretCode();
	}

	// called when the game is won. Displays a dialog declaring victory
	private void showVictoryDialog() {
		if (!settings.isAiSetter()) {
			if (settings.isAiGuesser())
				// human setter and AI guesser message
				JOptionPane.showMessageDialog(this,
						"I win of course. \n" + "You responded correctly, human. You may be spared.");
			else if (!settings.isAiGuesser())
				// human setter and human guesser message
				JOptionPane.showMessageDialog(this, "Victory. According to a human.");
		} else {
			// AI setter message
			JOptionPane.showMessageDialog(this, "Win! The secret code was indeed" + secretCode);
		}
	}

	// called when the game is lost. Displays a dialog declaring the loss
	private void showLoseDialog() {
		if (settings.isAiSetter())
			// AI setter message
			JOptionPane.showMessageDialog(this, "Lose. The secret code was" + secretCode);
		else
			// human setter message
			JOptionPane.showMessageDialog(this, "Lose.");
	}

	// this method processes a turn, called when take turn button is clicked
	public Turn takeTurn(Turn t) {
		// add and process turn to the game
		game.takeTurn(t);
		// update the board display with the new turn
		board.addTurn(t);
		// update the game analysis text
		analysisDialog.updateText();

		// check for empty code Universe
		// break out of game if there are no possible solutions to the game state
		// can only occur when the human setter errs in responding
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
		return t; // returns the taken turn
	}

	//called when the take turn button is clicked for an AI player
	public void aiPlayTurn() {
		// an AI personality makes a choice of code
		Code choice;
		// create AI personality
		AIPersonality pers = new TheProfessorAI(game);
		// get AIs choice of code
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
			// draw the guess half-turn with empty response until human chooses a response
			board.addTurnGuess(new Turn(choice, new Response(settings.getCodeLength())));
			responseDialog.requestFocus();
		}

	}

	//called when a human setter responds to the turn
	private void humanResponds(Response response, Code choice) {
		//remove the half-turn from the board
		board.removeTurnGuess();
		board.addEmptyTurn();
		//take the full turn, with the chosen response
		Turn t = new Turn(choice, response);
		this.takeTurn(t);

	}

	// clear the board and gamestate, select new secret code, and reset the AI
	// , re-randomize the guess input combo boxes, etc.
	public void reset() {
		board.clear();
		game = new AnalyzedGame(settings);
		secretCode = game.getSecretCode();
		guessPanel.resetCBoxes();
		menuBar.easyMode.setSelected(settings.isEasyMode());
		analysisDialog.updateText();
		updateTitle();

	}

	public GameSettings getSettings() {
		return settings;
	}

	public Code getSecretCode() {
		return secretCode;
	}

	//called when the increase peg options menu item is selected
	private void addPegOption() {
		//ensure we can add an option without going over the maximum
		if (settings.getNumPegOptions() < settings.getMaxPegOptions()) {
			//update game settings with the new option
			settings.addPegOption();
			//update the input panel so new options are available
			guessPanel = new GuessInputPanel(this);
			//update the layout
			layoutPanels();
			//generate text for dialog
			int num = settings.getNumPegOptions();
			String s = settings.getPegOptions().get(num - 1).getText();
			s += " pegs added. " + num + " Peg Options";
			//display dialog to confirm peg option addition
			JOptionPane.showMessageDialog(this, s);
		} else
			//display dialog showing failure to add peg option
			JOptionPane.showMessageDialog(this, "No more pegs");
	}

	//called when the decrease peg options menu item is selected
	private void removePegOption() {
		//ensure we can remove an option without having zero options
		if (settings.getNumPegOptions() > 1) {
			int num = settings.getNumPegOptions();
			//update game settings with the new option
			//generate text for dialog
			String s = settings.getPegOptions().get(num - 1).getText();
			s += " pegs removed. " + (num - 1) + " Peg Options";
			//display dialog to confirm peg option removal
			JOptionPane.showMessageDialog(this, s);
			//update game settings with the new options
			settings.removePegOption();
			//update the input panel so new options are available
			guessPanel = new GuessInputPanel(this);
			layoutPanels();
		} else {
			//display dialog showing that there are no more peg options
			JOptionPane.showMessageDialog(this, "No pegs, no game.");
			//close the window!!
			this.dispose();
		}
	}

	
	//called when a new window is needed, such as when the code length setting is changed
	private void newWindow() {
		//get rid of any analysis dialog window
		analysisDialog.dispose();
		//create and display a new game window
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MastermindGUI newGUI = new MastermindGUI(settings);
				newGUI.createAndShowGUI();
			}
		});
	}

	//called when the AI batch games menu item is selected
	//opens the batch games dialog set to current settings
	private void openBatches() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int len = settings.getCodeLength();
				int opt = settings.getNumPegOptions();
				BatchDialog batches = new BatchDialog(new GameSettings(len, opt));
			}
		});
	}

		//Lays out the board and panels using GroupLayout
	private void layoutPanels() {
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
		guessPanel.setVisible(!settings.isAiGuesser());
		aiTurnPanel.setVisible(settings.isAiGuesser());

	}

	//sets the window title bar to display pertinent game info
	private void updateTitle() {
		this.setTitle(settings.TITLE + " | " + settings.toString());
	}

	//initializes and displays the main game window
	private void createAndShowGUI() {
		updateTitle();

		// Set this window's location and size: TODO add to game settings
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
		menuBar.showAnalysis.addActionListener(this);
		menuBar.aiBatches.addActionListener(this);
		menuBar.defaultSettings.addActionListener(this);

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
		// create the analysis dialog
		analysisDialog = new AnalysisDialog(this);
		// analysisDialog.setVisible(true);

		// Add panels to window and layout:
		layoutPanels();

		// check settings for human or ai player
		guessPanel.setVisible(!settings.isAiGuesser());
		aiTurnPanel.setVisible(settings.isAiGuesser());
		//ensure menu selections conform to game settings
		menuBar.aiGuesser.setSelected(settings.isAiGuesser());
		menuBar.humanGuesser.setSelected(!settings.isAiGuesser());
		menuBar.aiSetter.setSelected(settings.isAiSetter());
		menuBar.humanSetter.setSelected(!settings.isAiSetter());
		menuBar.easyMode.setSelected(settings.isEasyMode());

		// show and configure application window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(new Dimension(getPreferredSize()));
		setResizable(false);
	}

	//fires when a button is clicked or a menu item is selected
	public void actionPerformed(ActionEvent e) {
		//Reset menu item
		if (e.getActionCommand().equals("Reset")) {
			reset();
			
			//Show Game Analysis menu item
		} else if (e.getActionCommand().equals("Show Game Analysis")) {
			analysisDialog.setVisible(true);
			
			//AI  Batch Games menu item
		} else if (e.getActionCommand().equals("AI Batch Games")) {
			openBatches();
			
			//Take Turn button clicked
		} else if (e.getActionCommand().equals("Take Turn")) {
			Code guess = guessPanel.getUserCode(); //get the code from the input panel
			if (settings.isAiSetter())
				// take the turn with chosen code
				this.takeTurn(new Turn(guess, new Response(guess, secretCode)));
			else
			// draw the guess with empty response until human chooses (if not already)
			if (!board.isGuessShown()) {
				board.addTurnGuess(new Turn(guess, new Response(settings.getCodeLength())));
				responseDialog.requestFocus();
			}
			//Human guesser menu item
		} else if (e.getActionCommand().equals(("Human Guesser"))) {
			// show the guess panel and hide the ai turn button
			settings.setAiGuesser(false);
			guessPanel.setVisible(true);
			aiTurnPanel.setVisible(false);
			reset();
			
			//AI guesser menu item
		} else if (e.getActionCommand().equals(("AI Guesser"))) {
			// hide the guess panel and show the ai turn button
			settings.setAiGuesser(true);
			guessPanel.setVisible(false);
			aiTurnPanel.setVisible(true);
			reset();

			//AI Code setter menu item
		} else if (e.getActionCommand().equals(("AI Code Setter"))) {
			settings.setAiSetter(true);
			responseDialog.dispose();
			reset();
			
			//Human code setter menu item
		} else if (e.getActionCommand().equals(("Human Code Setter"))) {

			settings.setAiSetter(false);
			// ensure that you can't manually close response dialog
			responseDialog.setDefaultCloseOperation(0);
			//show response dialog for human response
			responseDialog.setVisible(true);
			reset();

			// AI take turn button is clicked (and game is ready for an AI turn)
		} else if (e.getActionCommand().equals("AI Take Turn") && !board.isGuessShown()) {
			aiPlayTurn();
			
			//Increase Code Length menu item
		} else if (e.getActionCommand().equals("Increase Code Length")) {
			if (settings.getCodeLength() < settings.MAXCODELENGTH) {
				settings.setCodeLength(settings.getCodeLength() + 1);
				// new instance of GUI needed to accommodate new board
				newWindow();
				this.dispose();
			} else
				//dialog when code length can't be increased
				JOptionPane.showMessageDialog(this, "No, thank you.");
			
			//Decrease code length menu item
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
			
			//Add peg option menu item
		} else if (e.getActionCommand().equals("Add Peg Option")) {
			addPegOption();
			reset();
			
			//Remove peg option menu item
		} else if (e.getActionCommand().equals("Remove Peg Option")) {
			removePegOption();
			reset();
			
			//Default settings menu item
		} else if (e.getActionCommand().equals("Default Settings")) {
			settings.setCodeLength(4);
			settings.setPegOptions(6);
			newWindow();
			this.dispose();
			//A human response button was clicked
		} else {
			// check each response button to see if it was pressed
			for (int i = 0; i < responseDialog.getButtons().size(); i++)
				if (e.getSource().equals(responseDialog.getButtons().get(i)) && board.isGuessShown()) {
					//set response to clicked button
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

	//create a new game window
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MastermindGUI gameGUI = new MastermindGUI();
				gameGUI.createAndShowGUI();
			}
		});
	}

}
