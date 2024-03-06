package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import baseGame.GameSettings;
import baseGame.Response;

//This class represents a panel to graphically display the pins in a response (or an empty response)
public class PinsPanel extends JPanel {

	private Response rsp; // the response pins to display

	// creates a display panel with the number of black, white,
	// and empty pins specified in the passed turn response
	public PinsPanel(Response response) {
		super();
		rsp = response;
		this.setLayout(new GridLayout(2, 0)); //pins are drawn using a grid layout
		//draw black pins
		for (int i = 0; i < rsp.getNumBlack(); i++) {
			this.add(new PinPanel(true));
		}
		//draw white pins
		for (int i = 0; i < rsp.getNumWhite(); i++) {
			this.add(new PinPanel(false));
		}
		//draw empty pin slots, which are empty gray panels sized according to game settings
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

//This class represents the display of a single black or white pin
class PinPanel extends JPanel {
	private Color color; //the color of the pin
	private PinPainter painter; //used to draw the pin circle on the panel

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Call JPanel's paintComponent method
									// then paint the centered black or white pin
		painter.paint(g);

	}

	//creates a black or white pin panel
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

//This class renders a circle sized according to game settings
class PinPainter {
	private Color color;
	private int d = GameSettings.getPinWidth();
	private int w = GameSettings.getPinSlotWidth();
	private int h = GameSettings.getPinSlotHeight();

	public PinPainter(Color color) {
		this.color = color;
	}

	public void paint(Graphics g) {
		int x = (w - d) / 2;
		int y = (h - d) / 2;
		g.setColor(color);
		g.fillOval(x, y, d, d);

	}

}