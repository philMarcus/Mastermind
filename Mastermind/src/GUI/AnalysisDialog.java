package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import ai.AnalyzedGame;

//This class represents the AI analysis window for a game of mastermind
//This will display information useful to the player for selecting their next
//code guess, such as the number of possible codes to pick from, and the probability
//of finding a given peg in a given slot. A human player using this panel during a game
//would be considered cheating!
public class AnalysisDialog extends JDialog {
	//the text area in which to display the info
	JTextArea txt;
	//the current state of a mastermind game
	MastermindGUI gameState;

	public AnalysisDialog(MastermindGUI game) {
		super();
		gameState = game;
		//set window title bar
		this.setTitle(game.getSettings().TITLE + " - Game Analysis");
		//uses the MastermindGUI toString method to generate the informational text
		txt = new JTextArea(gameState.toString(),8,40);
		txt.setFont(new Font("Courier New", Font.PLAIN, 20));

		
		this.add(txt);
		txt.setMargin(new Insets(15,15,15,15));
		//locate the window next to the main game window
		//TODO move these numbers to game settings
		setBounds(700, 350, 600, 400);
		
		
	}

	public void updateText() {
		txt.setText(gameState.toString());
	}
}
