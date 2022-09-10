

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel{
	
		private GameSettings settings;
		private int turnsTaken=0;

		
	  //construct empty board display
	  public Board(Game gameState) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		settings = gameState.getSettings();

		//add an empty turn panel for each "untaken" turn
		for(int i=0;i < settings.getMaxTries(); i++) {
			this.add(new TurnPanel(settings.getCodeLength()));
		}
		
	  }
	  
	  public void addTurn(Turn turn) {
		  // add the new turn panel and remove an "untaken" turn panel at the end
		  turnsTaken++;
		  this.add(new TurnPanel(turn),turnsTaken-1);
		  this.remove(this.getComponentCount()-1);
		  //redraw the board
		  this.revalidate();
		  this.repaint();
	  }
	  
	  public void clear() {
		  // remove all the "taken" turn panels and add that many "untaken" turn panels
		  for (int i=0; i<turnsTaken;i++) {
			  this.remove(0);
			  this.add(new TurnPanel(settings.getCodeLength()));

		  }
		  turnsTaken=0;
		  //redraw the board
		  this.revalidate();
		  this.repaint();
	  }
		
}

class TurnPanel extends JPanel{
	private Turn turn;
	private int w = GameSettings.getPegSlotWidth()*2;
	private int h = GameSettings.getPegSlotHeight()*3/2;
	
	//draw the pegs and response pins for a turn
	public TurnPanel(Turn drawTurn) {
		super();
		turn = drawTurn;
		w = w*turn.getGuess().getLength();

		this.add(new PegsPanel(turn.getGuess()));
		this.add(new PinsPanel(turn.getResponse()));
		this.setMaximumSize(new Dimension(w,h));
	}
	//draw blank turn panel
	public TurnPanel(int numEmpty) {
		super();
		w = w*numEmpty;
		this.add(new PegsPanel(numEmpty));
		this.add(new PinsPanel(new Response(numEmpty)));
		this.setMaximumSize(new Dimension(w,h));
		
	}
	
	
}


class PegsPanel extends JPanel {
	
      private Code code;
  	  private GridLayout layout = new GridLayout(1,0);

 
	  public PegsPanel(Code drawCode) {
	  	  super();
		  code = drawCode;
		  this.setLayout(layout);
	
		  for (int i = 0; i < code.getLength(); i++) {
			  PegPanel p = new PegPanel(code.getPeg(i));
			  this.add(p);
		  }
	  }
	  	//draw an empty panel
	  	public PegsPanel(int numEmpty) {
		super();
		
		  this.setLayout(layout);
		  for (int i = 0; i < numEmpty; i++) {
			  this.add(new PegPanel());
		}
	  }
}




class PinsPanel extends JPanel {
	
	private Response rsp;
	//creates a display panel with the number of black, white, 
	//and empty pins specified in the passed turn response
	public PinsPanel(Response response) {
		super();
		rsp = response;
		this.setLayout(new GridLayout(2,0));

		for(int i=0;i<rsp.getNumBlack();i++) {
			this.add(new PinPanel(true));
		}
		for(int i=0;i<rsp.getNumWhite();i++) {
			this.add(new PinPanel(false));
		}
		for(int i=0;i<rsp.getNumEmpty();i++) {
			JPanel p = new JPanel();
			p.setBackground(Color.GRAY);
			p.setPreferredSize(new Dimension(GameSettings.getPinSlotWidth(),GameSettings.getPinSlotHeight()));
			this.add(p);
		}
		}

}

class PinPanel extends JPanel{
		private Color color;
		private int d = GameSettings.getPinWidth();
		private int w = GameSettings.getPinSlotWidth();
		private int h = GameSettings.getPinSlotHeight();
	
	  public void paintComponent(Graphics g)
	  {
	    super.paintComponent(g);  // Call JPanel's paintComponent method
	                              //  then paint the centered black or white pin
	    int x = (w-d)/2;
	    int y = (h-d)/2;
	    g.setColor(color);
	    g.fillOval(x,y,d,d);
	  }
	  
	  
	  public PinPanel(boolean isBlack) {
		 super();
		 this.setBackground(Color.GRAY);
		 if (isBlack) {
			 color = Color.BLACK; 
		 }
		 else color = Color.WHITE;
	  	}
	  
	  	//ensure the panel size is given by the game settings
		public Dimension getPreferredSize() {
			return new Dimension (w,h);
		}
	 
}
	  


/*class SecretCodePanel extends PegsPanel{
	  public void paintComponent(Graphics g)
	  {
	    super.paintComponent(g);  // Call PegsPanel's paintComponent method
	                              //  to paint the background
	    
	  }
	
}
*/


