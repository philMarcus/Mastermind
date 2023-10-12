package GUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import baseGame.GameSettings;

public class BatchDialog extends JDialog {

	BatchInputPanel inputPanel;
	JTextArea txt;
	
	public BatchDialog(GameSettings settings) {
		super();
		
		txt = new JTextArea("",8,40);
		txt.setFont(new Font("Courier New", Font.PLAIN, 20));
		inputPanel = new BatchInputPanel(settings);
		
		this.add(txt);
		this.add(inputPanel);
		
		this.setVisible(true);
	}
}


class BatchInputPanel extends JPanel implements EventListener{
	
	SpinnerNumberModel lenModel;
	JSpinner lenInput;
	SpinnerNumberModel numPegsModel;
	JSpinner numPegsInput;
	SpinnerNumberModel numGamesModel;
	JSpinner numGamesInput;
	JButton run;
	
	final int NUMGAMES_DEFAULT = 1000;
	final int NUMGAMES_MIN = 10;
	final int NUMGAMES_MAX=1000000000;
	final int NUMGAMES_STEP=1000;
	
	BatchInputPanel(GameSettings settings){
		super();
		//initialize components
		lenModel = new SpinnerNumberModel(settings.getCodeLength(),1,settings.MAXCODELENGTH,1);
		lenInput = new JSpinner(lenModel);
		
		numPegsModel = new SpinnerNumberModel(settings.getNumPegOptions(),1,settings.getMaxPegOptions(),1);
		numPegsInput = new JSpinner(numPegsModel);
		
		numGamesModel = new SpinnerNumberModel(NUMGAMES_DEFAULT,NUMGAMES_MIN,NUMGAMES_MAX,NUMGAMES_STEP);
		numGamesInput = new JSpinner(numGamesModel);
		
		run = new JButton("Run");
		
		//set the layout and add components
		this.setLayout(new FlowLayout());
		this.add(lenInput);
		this.add(numPegsInput);
		this.add(numGamesInput);
		this.add(run);
	}
	
}