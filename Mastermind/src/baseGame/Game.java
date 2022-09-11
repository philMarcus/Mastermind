package baseGame;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import ai.AI;

public class Game implements ActionListener,ItemListener{

	private ArrayList<Turn> turns = new ArrayList<Turn>();
	private GameSettings settings = new GameSettings();
	// initializes a random secret code
	private Code secretCode = new Code(settings.getCodeLength(), settings.getPegOptions(),settings.isEasyMode());

	private static AI ai;

	// GUI components
	private static JFrame window;
	private static GuessInputPanel guessPanel;
	private static Board board;
	private static GameMenuBar menuBar;

	public void takeTurn(Code guess) {
		// add a new turn to the gamestate, which consists of a guessed code and a
		// calculated response
		turns.add(new Turn(guess, new Response(guess, secretCode)));
		// update the board display with the new turn
		board.addTurn(turns.get(turns.size() - 1));
		// check the new turn for victory
		if (turns.get(turns.size() - 1).isVictory()) {
			JOptionPane.showMessageDialog(window, "Win! The secret code was indeed" + secretCode);
			reset();
		}
		// check new turn for a loss
		else if (turns.size() >= settings.getMaxTries()) {
			JOptionPane.showMessageDialog(window, "Lose. The secret code was" + secretCode);
			reset();

		}

	}

	// clear the board and gamestate, select new secret code, and reset the AI
	// analysis
	public void reset() {
		board.clear();
		turns.clear();
		ai = new AI(getSettings());
		secretCode = new Code(settings.getCodeLength(), settings.getPegOptions(),settings.isEasyMode());
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

	private static void createAndShowGUI() {
		Game game = new Game();
		ai = new AI(game.getSettings());
		window = new JFrame("Marcus Mastermind");

		// Set this window's location and size:
		window.setBounds(300, 300, 500, 600);

		menuBar = new GameMenuBar();
		window.setJMenuBar(menuBar);
		menuBar.reset.addActionListener(game);
		menuBar.easyMode.addItemListener(game);

		// Create a Board, which is a kind of JPanel:
		board = new Board(game);

		// Create the user input panel
		guessPanel = new GuessInputPanel(game);

		// Add panels to window:
		Container c = window.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(board);
		c.add(guessPanel);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		// Registered on the "take turn" button on user input panel. Takes a turn.
		if (e.getActionCommand().equals("Reset")) {
			reset();
		} else if (e.getActionCommand().equals("Take Turn")){
			Code guess = guessPanel.getUserCode();
			takeTurn(guess);

			// test new AI code
			ai.analyze(turns);
			System.out.println(ai.toString());
		}
	}
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==ItemEvent.SELECTED) {
        	settings.setEasyMode(true);
        	reset();
        }
        else if (e.getStateChange()==ItemEvent.DESELECTED) {
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

class GameMenuBar extends JMenuBar {
	
	JMenu gameMenu;
	JMenuItem reset;
	JCheckBoxMenuItem easyMode;


	public GameMenuBar() {
		gameMenu = new JMenu("Game");


		this.add(gameMenu);
		
		reset = new JMenuItem("Reset");
		gameMenu.add(reset);
		
		easyMode = new JCheckBoxMenuItem("Easy Mode");
		gameMenu.add(easyMode);

	}
}
