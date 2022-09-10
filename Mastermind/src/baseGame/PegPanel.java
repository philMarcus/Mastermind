package baseGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PegPanel extends JPanel {
		private Peg peg;
		private Color color;
		private int d = GameSettings.getPegWidth();
		private int w = GameSettings.getPegSlotWidth();
		private int h = GameSettings.getPegSlotHeight();

		
		 public void paintComponent(Graphics g){
			    super.paintComponent(g);  // Call JPanel's paintComponent method
			                              //  to paint the background
			    int x = (w-d)/2;
			    int y = (h-d)/2;
			    // draw outline and color in circle,
			    //centered on the panel, with the size found in settings
			    g.setColor(Color.BLACK);
			    g.fillOval(x-1,y-1, d+2, d+2);
			    g.setColor(color);
			    g.fillOval(x,y,d,d);
			   
		 }	
		 
		// a panel for a given Peg
		public PegPanel(Peg drawPeg) {
			super();
			peg = drawPeg;
			color = peg.getColor();
			}
		
		//a panel for an empty peg slot
		// draw a half-diameter gray circle
		public PegPanel() {
			super();
			color = Color.gray;
			d = d/2;
		}
		
		//ensure size of panel comes from game settings
		public Dimension getPreferredSize() {
			return new Dimension (w,h);
		}
}
