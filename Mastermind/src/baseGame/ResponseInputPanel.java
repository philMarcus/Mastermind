package baseGame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ResponseInputPanel extends JPanel {
	private Game game;
	private int len;

}

class ResponseButton extends JButton {
	private Painter painter;
	private Response rsp;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		painter.paint(g);
	}
}
