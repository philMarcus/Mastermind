package GUI;


import javax.swing.JButton;
import javax.swing.JPanel;

public class AITurnPanel extends JPanel {
	private JButton aiTurnButton;

	public AITurnPanel(MastermindGUI gameState) {
		super();
		aiTurnButton = new JButton("AI Take Turn");
		aiTurnButton.addActionListener(gameState);

		this.add(aiTurnButton);
		//this.setMaximumSize(new Dimension(500,500));
	}

}