package baseGame;
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

//this component contains the combo boxes that the user uses to make a guess
//and also the button to take the turn with the guess
public class GuessInputPanel extends JPanel implements ActionListener {

	private Game game;

	private ArrayList<Peg> opts;
	private int len;
	private Code userCode;
	
	private ArrayList<JComboBox<Peg>> cBoxes = new ArrayList<>();
	private JButton takeTurn;


	
	public GuessInputPanel(Game gameState) {

		super();
		//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		game = gameState;
		opts = game.getSettings().getPegOptions();
		len = game.getSettings().getCodeLength();
		userCode = new Code(len, opts,game.getSettings().isEasyMode());
		
		for(int i=0;i<len;i++) {
			JComboBox<Peg> cb = new JComboBox<Peg>(getComboModel(opts));
			cb.setRenderer(new PegRenderer());
			this.add(cb);
			cb.addActionListener(this);
			//initializes the combo boxes to a random code
			cb.setSelectedItem(userCode.getPeg(i));
			cBoxes.add(cb);
		}
		takeTurn = new JButton("Take Turn");
		takeTurn.addActionListener(game);
		this.add(takeTurn);
	}
	
	public ArrayList<JComboBox<Peg>> getCBoxes() {
		return cBoxes;
	}

	public void setCBoxes(ArrayList<JComboBox<Peg>> cBoxes) {
		this.cBoxes = cBoxes;
	}
	
	public void resetCBoxes() {
		userCode = new Code(len, opts,game.getSettings().isEasyMode());
		for(int i=0;i<len;i++) {
			cBoxes.get(i).setSelectedItem(userCode.getPeg(i));
		}
	}
	

	public JButton getTakeTurn() {
		return takeTurn;
	}

	// create and return the List of Peg options as a Combo Box Model to populate the combo boxes
	private DefaultComboBoxModel<Peg> getComboModel(ArrayList<Peg> pegOpts){
		Peg[] cbModel = pegOpts.toArray(new Peg[0]); 
		return new DefaultComboBoxModel<>(cbModel);
	}
	
	public Code getUserCode() {
		return userCode;
	}

	public void actionPerformed(ActionEvent e) { 
	    //code that reacts to the action... 
		JComboBox<Peg> cb = (JComboBox<Peg>)e.getSource();
		for(int i=0;i<cBoxes.size();i++) {
			if(cBoxes.get(i)==cb) {
			
				userCode.setPeg((Peg)cb.getSelectedItem(),i); 
			}
		}
		
	}

}

class PegRenderer extends JPanel implements ListCellRenderer<Peg> {
    public PegRenderer() {
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList<? extends Peg> list,
                                                  Peg value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        PegPanel pegPanel = new PegPanel(value);

        return pegPanel;
    }
}


