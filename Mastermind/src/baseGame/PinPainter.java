package baseGame;

import java.awt.Color;
import java.awt.Graphics;

public class PinPainter implements Painter {
	private Color color;
	private int d = GameSettings.getPinWidth();
	private int w = GameSettings.getPinSlotWidth();
	private int h = GameSettings.getPinSlotHeight();

	public PinPainter(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics g) {
		int x = (w - d) / 2;
		int y = (h - d) / 2;
		g.setColor(color);
		g.fillOval(x, y, d, d);

	}

}
