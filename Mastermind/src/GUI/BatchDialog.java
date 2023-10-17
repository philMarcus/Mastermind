package GUI;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.AIBatchGames;
import ai.AnalyzedGame;
import baseGame.GameSettings;

public class BatchDialog extends JDialog implements ActionListener, ChangeListener {

	private BatchInputPanel inputPanel;
	private JTextArea txt;

	private GameSettings settings;

	private int length;
	private int numPegOpts;
	private int numGames;

	public BatchDialog(GameSettings settings) {
		super();
		this.settings = settings;

		this.setLayout(new GridLayout(0, 1));
		setBounds(900, 450, 600, 400);

		txt = new JTextArea("", 12, 40);
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

	public void runBatches() {
		settings.setCodeLength(length);
		settings.setPegOptions(numPegOpts);
		// for now no EZ mode in batches
		settings.setEasyMode(false);

		AIBatchGames batch = new AIBatchGames(settings);
		for (int i = 0; i < numGames; i++) {
			AnalyzedGame g = batch.runGame();
			txt.setText(batch.toString());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		runBatches();
	}

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

	class BatchInputPanel extends JPanel implements EventListener {

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