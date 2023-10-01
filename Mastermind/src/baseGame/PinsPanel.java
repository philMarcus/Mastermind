package baseGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class PinsPanel extends JPanel {

	private Response rsp;

	// creates a display panel with the number of black, white,
	// and empty pins specified in the passed turn response
	public PinsPanel(Response response) {
		super();
		rsp = response;
		this.setLayout(new GridLayout(2, 0));

		for (int i = 0; i < rsp.getNumBlack(); i++) {
			this.add(new PinPanel(true));
		}
		for (int i = 0; i < rsp.getNumWhite(); i++) {
			this.add(new PinPanel(false));
		}
		for (int i = 0; i < rsp.getNumEmpty(); i++) {
			JPanel p = new JPanel();
			p.setBackground(Color.GRAY);
			p.setPreferredSize(new Dimension(GameSettings.getPinSlotWidth(), GameSettings.getPinSlotHeight()));
			this.add(p);
		}
	}

	public Response getRsp() {
		return rsp;
	}

}

class PinPanel extends JPanel {
	private Color color;
	private Painter painter;
//	private int d = GameSettings.getPinWidth();


	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's paintComponent method
									// then paint the centered black or white pin
		painter.paint(g);

	}

	public PinPanel(boolean isBlack) {
		super();
		this.setBackground(Color.GRAY);
		if (isBlack) {
			color = Color.BLACK;
		} else
			color = Color.WHITE;
		painter = new PinPainter(color);
	}

	// ensure the panel size is given by the game settings
	public Dimension getPreferredSize() {
		int w = GameSettings.getPinSlotWidth();
		int h = GameSettings.getPinSlotHeight();
		return new Dimension(w, h);
	}

}