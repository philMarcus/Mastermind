package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class GameMenuBar extends JMenuBar {

	JMenu gameMenu;
	JMenu settingsMenu;
	JMenuItem reset;
	JCheckBoxMenuItem easyMode;
	JRadioButtonMenuItem humanGuesser;
	JRadioButtonMenuItem aiGuesser;
	JRadioButtonMenuItem humanSetter;
	JRadioButtonMenuItem aiSetter;

	public GameMenuBar() {
		gameMenu = new JMenu("Game");
		settingsMenu = new JMenu("Settings");

		this.add(gameMenu);
		this.add(settingsMenu);

		reset = new JMenuItem("Reset");
		gameMenu.add(reset);

		easyMode = new JCheckBoxMenuItem("Easy Mode");
		settingsMenu.add(easyMode);

		ButtonGroup guessers = new ButtonGroup();
		humanGuesser = new JRadioButtonMenuItem("Human Guesser");
		aiGuesser = new JRadioButtonMenuItem("AI Guesser");
		guessers.add(humanGuesser);
		guessers.add(aiGuesser);

		settingsMenu.addSeparator();
		settingsMenu.add(humanGuesser);
		settingsMenu.add(aiGuesser);
		
		ButtonGroup setters = new ButtonGroup();
		humanSetter = new JRadioButtonMenuItem("Human Code Setter");
		aiSetter = new JRadioButtonMenuItem("AI Code Setter");
		setters.add(humanSetter);
		setters.add(aiSetter);

		settingsMenu.addSeparator();
		settingsMenu.add(humanSetter);
		settingsMenu.add(aiSetter);

	}
}
