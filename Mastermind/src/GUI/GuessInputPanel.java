package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import baseGame.Code;
import baseGame.Peg;

//this component contains the combo boxes that the user uses to make a guess
//and also the button to take the turn with the guess
//It is placed below the gameboard in the main game window when a human plays the game
public class GuessInputPanel extends JPanel implements ActionListener {

	private MastermindGUI game; // the game state

	private ArrayList<Peg> opts; // list of peg options to poulate combo boxes
	private int len; // code length
	private Code userCode; // the code chosen/displayed on the combo boxes

	// the list of combo boxes, the are "len" of them
	private ArrayList<JComboBox<Peg>> cBoxes = new ArrayList<>();
	private JButton takeTurn; // button to take turn with selected code

	public GuessInputPanel(MastermindGUI gameState) {

		super();
		game = gameState;
		opts = game.getSettings().getPegOptions();
		len = game.getSettings().getCodeLength();
		// generate a random code to initially populate the combo boxes,
		// ensuring no repeats if easy mode is on
		userCode = new Code(len, opts, game.getSettings().isEasyMode());

		// create and initialize "len" combo boxes
		for (int i = 0; i < len; i++) {
			JComboBox<Peg> cb = new JComboBox<Peg>(getComboModel(opts));

			// the combo boxes will contain pegPanels
			cb.setRenderer(new PegRenderer());

			this.add(cb); // add the combo box to this panel
			cb.addActionListener(this); // this panel listens to each combo box

			// initializes the combo boxes to a random code
			cb.setSelectedItem(userCode.getPeg(i));

			cBoxes.add(cb); // add the combo box to the list of combo boxes
		}
		// add and initialize the taketurn button
		takeTurn = new JButton("Take Turn");
		takeTurn.addActionListener(game); // the main game window will listen to the button
		this.add(takeTurn);
	}

	public ArrayList<JComboBox<Peg>> getCBoxes() {
		return cBoxes;
	}

	public void setCBoxes(ArrayList<JComboBox<Peg>> cBoxes) {
		this.cBoxes = cBoxes;
	}

	// sets combo boxes to a new random code
	public void resetCBoxes() {
		userCode = new Code(len, opts, game.getSettings().isEasyMode());
		for (int i = 0; i < len; i++) {
			cBoxes.get(i).setSelectedItem(userCode.getPeg(i));
		}
	}

	public JButton getTakeTurn() {
		return takeTurn;
	}

	// create and return the List of Peg options as a Combo Box Model to populate
	// the combo boxes
	private DefaultComboBoxModel<Peg> getComboModel(ArrayList<Peg> pegOpts) {
		Peg[] cbModel = pegOpts.toArray(new Peg[0]);
		return new DefaultComboBoxModel<>(cbModel);
	}

	public Code getUserCode() {
		return userCode;
	}

	// this method fires when a combo box is changed, it will update the userCode
	// field.
	public void actionPerformed(ActionEvent e) {
		JComboBox<Peg> cb = (JComboBox<Peg>) e.getSource();
		for (int i = 0; i < cBoxes.size(); i++) {
			if (cBoxes.get(i) == cb) {
				userCode.setPeg((Peg) cb.getSelectedItem(), i);
			}
		}

	}

}

//this class creates the images (which are pegPanels) contained in the
//combo boxes
class PegRenderer extends JPanel implements ListCellRenderer<Peg> {
	public PegRenderer() {
		//our peg panels should be opaque
		setOpaque(true);
	}
	//returns the peg panel to render as a combo box option
	public Component getListCellRendererComponent(JList<? extends Peg> list, Peg value, int index, boolean isSelected,
			boolean cellHasFocus) {
		PegPanel pegPanel = new PegPanel(value);

		return pegPanel;
	}
}
