package GUI;

import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class BatchDialog extends JDialog {

	BatchInputPanel inputPanel;
	JTextArea batchText;
}


class BatchInputPanel extends JPanel implements EventListener{
	
	SpinnerNumberModel lenModel;
	JSpinner lenInput;
	SpinnerNumberModel numPegsModel;
	JSpinner numPegsInput;
	SpinnerNumberModel numGamesModel;
	JSpinner numGamesInput;
	JButton run;
	
}