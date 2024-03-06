package GUI;


import javax.swing.JButton;
import javax.swing.JPanel;

//This class represents the GUI panel used when the "player" is the AI rather than
//a human. It consists of a single button used to tell the AI to take a turn
public class AITurnPanel extends JPanel {
	private JButton aiTurnButton;

	public AITurnPanel(MastermindGUI gameState) {
		super();
		aiTurnButton = new JButton("AI Take Turn");
		//ensure the Mastermind GUI is listening to this button
		aiTurnButton.addActionListener(gameState);

		this.add(aiTurnButton);
	}

}
