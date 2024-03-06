package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import baseGame.GameSettings;
import baseGame.Peg;

//This class represents a graphical display of a single peg. It is used both to build
//The panel containing a code of pegs, and also to populate the combo boxes in the 
//user input panel.
public class PegPanel extends JPanel {
	private Peg peg; // the peg to draw
	private Color color; // color of the peg to draw
	// get dimensions of drawing from game settings
	private int d = GameSettings.getPegWidth();
	private int w = GameSettings.getPegSlotWidth();
	private int h = GameSettings.getPegSlotHeight();

	// overrides the JComponent paint method to paint the peg as a circle filled
	// with the peg's color
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's paintComponent method
									// to paint the background
		int x = (w - d) / 2;
		int y = (h - d) / 2;
		// draw black outline and color in circle,
		// centered on the panel, with the size found in settings
		g.setColor(Color.BLACK);
		g.fillOval(x - 1, y - 1, d + 2, d + 2);
		g.setColor(color);
		g.fillOval(x, y, d, d);

	}

	// construct a panel for a given Peg
	public PegPanel(Peg drawPeg) {
		super();
		peg = drawPeg;
		color = peg.getColor();
	}

	// a panel for an empty peg slot
	public PegPanel() {
		super();
		// empty peg slots are gray and have half the diameter of a peg
		color = Color.gray;
		d = d / 2;
	}

	// ensure size of panel comes from game settings
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}
}
