package baseGame;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ResponseInputDialog extends JDialog {
	private Game game;
	private int len;
	private ArrayList<ResponseButton> buttons = new ArrayList<>();
	
	private GridLayout layout;
	
	public ResponseInputDialog(Game gameState) {
		super(gameState.getWindow(),"Response Input");
		game = gameState;
		len = game.getSettings().getCodeLength();
		layout= new GridLayout(len, 0);
		
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
		this.setBounds(750, 500, 400, 200);
	}

	public ArrayList<ResponseButton> getButtons() {
		return buttons;
	}

}

class ResponseButton extends JButton {
//	private Painter painter;
	private Response rsp;
	private int len;

	public ResponseButton(Response r) {
		super(r.toString());
		rsp = r;

	}

	public Response getResponse() {
		return rsp;
	}
}

//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		painter.paint(g);
//	}
//}