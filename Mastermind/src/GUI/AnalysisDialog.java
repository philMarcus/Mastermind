package GUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import ai.AnalyzedGame;

public class AnalysisDialog extends JDialog {
	JTextArea txt;
	MastermindGUI gameState;

	public AnalysisDialog(MastermindGUI game) {
		super();
		gameState = game;

		txt = new JTextArea(gameState.toString(),8,40);
		txt.setFont(new Font("Courier New", Font.PLAIN, 20));
		
		this.add(txt);
//		setSize(new Dimension(getPreferredSize()));
		setBounds(700, 350, 500, 400);
	}

	public void updateText() {
		txt.setText(gameState.toString());
	}
}
