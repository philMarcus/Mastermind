package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.EventListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.AIBatchGames;
import ai.AnalyzedGame;
import baseGame.GameSettings;

//this class represents the window that can run a batch of AI-player Mastermind
//Games. The user sets the code length, number of peg options, and number of games
//to run. The window displays updates counts for the number of games won in
//a given number of turns, and the mean and median attempte to win.
public class BatchDialog extends JDialog implements ActionListener, ChangeListener {

	private BatchInputPanel inputPanel; //panel for user to enter numbers and begin a run
	private JTextArea txt; //panel to display info about the run

	private GameSettings settings;

	private int length; //length of codes in this run's games
	private int numPegOpts; //number of peg options in this run's games
	private int numGames; //number of games in this run

	//construct a new window with given game settings
	public BatchDialog(GameSettings settings) {
		super();
		this.settings = settings;

		this.setTitle(settings.TITLE + " - AI Batch Games");
		 //use a "FlowLayout" to layout the text area and the input panel
		this.setLayout(new FlowLayout());
		//locate the window. TODO: add these numbers to game settings
		setBounds(900, 450, 600, 450);

		//initialize the text area and the input panel
		txt = new JTextArea(16, 40);
		txt.setFont(new Font("Courier New", Font.PLAIN, 20));
		inputPanel = new BatchInputPanel(settings);

		// initialize variables to default spinner values
		length = (int) inputPanel.lenModel.getValue();
		numPegOpts = (int) inputPanel.numPegsModel.getValue();
		numGames = (int) inputPanel.numGamesModel.getValue();

		// add components and register action/change listeners
		this.add(txt);
		this.add(inputPanel);
		inputPanel.lenInput.addChangeListener(this);
		inputPanel.numPegsInput.addChangeListener(this);
		inputPanel.numGamesInput.addChangeListener(this);
		inputPanel.run.addActionListener(this);

		this.setVisible(true);
	}

	//this method runs the batch of games using the settings found in the input panel
	public void runBatch() {
		settings.setCodeLength(length);
		settings.setPegOptions(numPegOpts);
		// for now no EZ mode in batches
		settings.setEasyMode(false);

		
		AIBatchGames batch = new AIBatchGames(settings);
		//loop to run a game and then update the text display
		for (int i = 0; i < numGames; i++) {
			batch.runGame();
			txt.setText(settings.toString() + "\n"+ batch.toString());
		}
	}	
	

	@Override
	//This method is called when the only button, "Run" is clocked
	public void actionPerformed(ActionEvent e) {
		//when "run" is clicked start a new batch thread running
	    BatchThread t = new BatchThread();
	    t.start();
	}

	//This method is called when a spinner value is changed
	@Override
	public void stateChanged(ChangeEvent e) {
		// update length, peg options, or numGames when a spinner value changes
		if (e.getSource().equals(inputPanel.lenInput)) {
			length = (int) inputPanel.lenModel.getValue();
		} else if (e.getSource().equals(inputPanel.numPegsInput)) {
			numPegOpts = (int) inputPanel.numPegsModel.getValue();
		} else if (e.getSource().equals(inputPanel.numGamesInput)) {
			numGames = (int) inputPanel.numGamesModel.getValue();
		}
	}
	
	//want to run batches in a new thread so as not to hang the UI while it goes
	//this class overrides the run() method of the Thread class
	class BatchThread extends Thread{
		public void run() {
			runBatch();
		}
	}
	
//this class represents the user input panel at the bottom of the window,
	//it contains the spinners with the settings, and the "run" button
	class BatchInputPanel extends JPanel {

		SpinnerNumberModel lenModel;
		JSpinner lenInput;
		SpinnerNumberModel numPegsModel;
		JSpinner numPegsInput;
		SpinnerNumberModel numGamesModel;
		JSpinner numGamesInput;
		JButton run;

		final int NUMGAMES_DEFAULT = 1000;
		final int NUMGAMES_MIN = 10;
		final int NUMGAMES_MAX = 1000000000;
		final int NUMGAMES_STEP = 1000;

		BatchInputPanel(GameSettings settings) {
			super();
			// initialize components
			lenModel = new SpinnerNumberModel(settings.getCodeLength(), 1, settings.MAXCODELENGTH, 1);
			lenInput = new JSpinner(lenModel);
			JLabel lenLabel = new JLabel("Code Length:");

			numPegsModel = new SpinnerNumberModel(settings.getNumPegOptions(), 1, settings.getMaxPegOptions(), 1);
			numPegsInput = new JSpinner(numPegsModel);
			JLabel pegLabel = new JLabel("Peg Options:");

			numGamesModel = new SpinnerNumberModel(NUMGAMES_DEFAULT, NUMGAMES_MIN, NUMGAMES_MAX, NUMGAMES_STEP);
			numGamesInput = new JSpinner(numGamesModel);
			JLabel gameLabel = new JLabel("Games:");

			run = new JButton("Run");

			// set the layout and add components
			this.setLayout(new FlowLayout());
			this.add(lenLabel);
			this.add(lenInput);
			this.add(pegLabel);
			this.add(numPegsInput);
			this.add(gameLabel);
			this.add(numGamesInput);
			this.add(run);
			
		}

	}
}