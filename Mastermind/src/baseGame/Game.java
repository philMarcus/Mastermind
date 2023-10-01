package baseGame;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import ai.AI;
import ai.AIPersonality;
import ai.TheProfessorAI;

//by Phil Marcus
public class Game implements ActionListener, ItemListener {

	private ArrayList<Turn> turns = new ArrayList<Turn>();
	private GameSettings settings = new GameSettings();
	// initializes a random secret code; if easyMode is true, then the pegs won't
	// repeat.
	private Code secretCode = new Code(settings.getCodeLength(), settings.getPegOptions(), settings.isEasyMode());

	private AI ai = new AI(settings);
	// private CodeUniverse codeUniverse = new CodeUniverse(settings);

	// GUI components
	private static JFrame window;
	private static GuessInputPanel guessPanel;
	private static ResponseInputDialog responseDialog;
	private static Board board;
	private static GameMenuBar menuBar;

	public boolean takeTurn(Code guess) {
		// add a new turn to the gamestate, which consists of a guessed code and a
		// calculated response
		Turn t = new Turn(guess, new Response(guess, secretCode));
		turns.add(t);
		// update the board display with the new turn
		board.addTurn(t);
		// process turn with AI
		ai.processTurn(t);
		// check the new turn for victory
		if (t.isVictory()) {
			JOptionPane.showMessageDialog(window, "Win! The secret code was indeed" + secretCode);
			reset();

		}
		// check new turn for a loss
		else if (turns.size() >= settings.getMaxTries()) {
			JOptionPane.showMessageDialog(window, "Lose. The secret code was" + secretCode);
			reset();

		}
		return t.isVictory();

	}

	public void aiPlayTurn() {
		// an AI personality makes a choice of code
		AIPersonality pers = new TheProfessorAI(ai.getCodeUniverse());
		Code choice = pers.getChoice();
		// update combo boxes with chosen code
		ArrayList<JComboBox<Peg>> cbs = guessPanel.getCBoxes();
		for (int i = 0; i < settings.getCodeLength(); i++) {
			cbs.get(i).setSelectedItem(choice.getPeg(i));
		}
		// take the turn with chosen code
		takeTurn(choice);
	}
	

	private void humanResponds(Response response) {
		// TODO Auto-generated method stub
		
	}

	// clear the board and gamestate, select new secret code, and reset the AI
	// , rerandomize the guess input combo boxes
	public void reset() {
		board.clear();
		turns.clear();
		secretCode = new Code(settings.getCodeLength(), settings.getPegOptions(), settings.isEasyMode());
		guessPanel.resetCBoxes();
		ai = new AI(settings);
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

	public static JFrame getWindow() {
		return window;
	}

	private static void createAndShowGUI() {
		Game game = new Game();

		window = new JFrame("Marcus Mastermind");

		// Set this window's location and size:
		window.setBounds(300, 300, 500, 600);

		menuBar = new GameMenuBar();
		window.setJMenuBar(menuBar);
		menuBar.reset.addActionListener(game);
		menuBar.easyMode.addItemListener(game);
		menuBar.humanGuesser.addActionListener(game);
		menuBar.aiGuesser.addActionListener(game);
		menuBar.aiSetter.addActionListener(game);
		menuBar.humanSetter.addActionListener(game);

		// Create a Board, which is a kind of JPanel:
		board = new Board(game);

		// Create the user input panel
		guessPanel = new GuessInputPanel(game);

		responseDialog = new ResponseInputDialog(game);

		// Add panels to window:
		Container c = window.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(board);
		c.add(guessPanel);

		// check settings for human or ai player
		menuBar.humanGuesser.setSelected(true);
		if (game.getSettings().isAiGuesser())
			menuBar.aiGuesser.doClick();

		// check settings for human or ai setter
		menuBar.aiSetter.setSelected(true);
		if (!game.getSettings().isAiSetter())
			menuBar.humanSetter.doClick();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		// Registered on the "take turn" button on user input panel. Takes a turn.
		if (e.getActionCommand().equals("Reset")) {
			reset();
		} else if (e.getActionCommand().equals("Take Turn")) {
			Code guess = guessPanel.getUserCode();
			takeTurn(guess);
		} else if (e.getActionCommand().equals(("Human Guesser"))) {
			settings.setAiGuesser(false);
			guessPanel.getTakeTurn().setText("Take Turn");
			reset();
		} else if (e.getActionCommand().equals(("AI Guesser"))) {
			settings.setAiGuesser(true);
			guessPanel.getTakeTurn().setText("AI Game");
			reset();

		} else if (e.getActionCommand().equals(("AI Code Setter"))) {
			settings.setAiSetter(true);
			responseDialog.dispose();
			reset();

		} else if (e.getActionCommand().equals(("Human Code Setter"))) {

			settings.setAiSetter(false);
			responseDialog.setVisible(true);
			reset();

		} else if (e.getActionCommand().equals("AI Game")) {

			aiPlayTurn();
		} else {
			
			for(int i =0;i<responseDialog.getButtons().size();i++)
				if(e.getSource().equals(responseDialog.getButtons().get(i))) {
					humanResponds(responseDialog.getButtons().get(i).getResponse());
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
				createAndShowGUI();
			}
		});
	}

}

//class GameMenuBar extends JMenuBar {
//
//	JMenu gameMenu;
//	JMenu settingsMenu;
//	JMenuItem reset;
//	JCheckBoxMenuItem easyMode;
//	JRadioButtonMenuItem humanGuesser;
//	JRadioButtonMenuItem aiGuesser;
//	JRadioButtonMenuItem humanSetter;
//	JRadioButtonMenuItem aiSetter;
//
//	public GameMenuBar() {
//		gameMenu = new JMenu("Game");
//		settingsMenu = new JMenu("Settings");
//
//		this.add(gameMenu);
//		this.add(settingsMenu);
//
//		reset = new JMenuItem("Reset");
//		gameMenu.add(reset);
//
//		easyMode = new JCheckBoxMenuItem("Easy Mode");
//		settingsMenu.add(easyMode);
//
//		ButtonGroup guessers = new ButtonGroup();
//		humanGuesser = new JRadioButtonMenuItem("Human Guesser");
//		aiGuesser = new JRadioButtonMenuItem("AI Guesser");
//		guessers.add(humanGuesser);
//		guessers.add(aiGuesser);
//
//		settingsMenu.addSeparator();
//		settingsMenu.add(humanGuesser);
//		settingsMenu.add(aiGuesser);
//		
//		ButtonGroup setters = new ButtonGroup();
//		humanSetter = new JRadioButtonMenuItem("Human Code Setter");
//		aiSetter = new JRadioButtonMenuItem("AI CodeSetter");
//		setters.add(humanSetter);
//		setters.add(aiSetter);
//
//		settingsMenu.addSeparator();
//		settingsMenu.add(humanSetter);
//		settingsMenu.add(aiSetter);
//
//	}
//}
