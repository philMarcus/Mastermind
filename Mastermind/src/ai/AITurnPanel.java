package ai;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import baseGame.Game;

public class AITurnPanel extends JPanel {
	private JButton aiTurnButton;

	public AITurnPanel(Game gameState) {
		super();
		aiTurnButton = new JButton("AI Take Turn");
		aiTurnButton.addActionListener(gameState);

		this.add(aiTurnButton);
		//this.setMaximumSize(new Dimension(500,500));
	}

}
