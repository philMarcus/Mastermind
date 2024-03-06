package GUI;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;

import baseGame.Response;

//This class represents the window that a human code-setter uses to choose a response
//to a guessed code
public class ResponseInputDialog extends JDialog {
	private MastermindGUI game;
	private int len; //code length
	//list of buttons conatining possible responses
	private ArrayList<ResponseButton> buttons = new ArrayList<>(); 
	
	private GridLayout layout;
	
	public ResponseInputDialog(MastermindGUI gameState) {
		super(gameState ,"Response Input"); //Create a dialog owned by the Mastermind GUI
		game = gameState;
		len = game.getSettings().getCodeLength();
		layout= new GridLayout(len, 0); //use a grid layout with codeLength rows
		
		this.setLayout(layout);
		
		//add a response buttons to the buttons array for each possible game response
		for(int b=0;b<=len;b++) {
			for(int w=0;w<=(len-b);w++) {
				ResponseButton btn =new ResponseButton(new Response(b,w,len));
				buttons.add(btn);
				btn.addActionListener(game);
				this.add(btn);
			}
		}
		//locate the window TODO: add to game settings
		this.setBounds(750, 500, 400, 200);
	}

	public ArrayList<ResponseButton> getButtons() {
		return buttons;
	}

}

//This class represents a single button for a single possible pin response
class ResponseButton extends JButton {
	private Response rsp; //the response represented by the button
	private int len; //code length

	public ResponseButton(Response r) {
		super(r.toString()); //the button text comes from the Response toString method
		rsp = r;

	}

	public Response getResponse() {
		return rsp;
	}
}

