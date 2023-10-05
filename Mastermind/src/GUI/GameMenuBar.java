package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class GameMenuBar extends JMenuBar {

	JMenu gameMenu;
	JMenu playersMenu;
	JMenu settingsMenu;
	// game menu items
	JMenuItem reset;
	// players menu items
	JRadioButtonMenuItem humanGuesser;
	JRadioButtonMenuItem aiGuesser;
	JRadioButtonMenuItem humanSetter;
	JRadioButtonMenuItem aiSetter;
	// settings menu items
	JCheckBoxMenuItem easyMode;
	JMenuItem increaseCodeLength;
	JMenuItem decreaseCodeLength;
	JMenuItem addPegOption;
	JMenuItem removePegOption;

	public GameMenuBar() {
		gameMenu = new JMenu("Game");
		playersMenu = new JMenu("Players");
		settingsMenu = new JMenu("Settings");

		this.add(gameMenu);
		this.add(playersMenu);
		this.add(settingsMenu);

		reset = new JMenuItem("Reset");
		gameMenu.add(reset);



		ButtonGroup guessers = new ButtonGroup();
		humanGuesser = new JRadioButtonMenuItem("Human Guesser");
		aiGuesser = new JRadioButtonMenuItem("AI Guesser");
		guessers.add(humanGuesser);
		guessers.add(aiGuesser);

	
		
		ButtonGroup setters = new ButtonGroup();
		humanSetter = new JRadioButtonMenuItem("Human Code Setter");
		aiSetter = new JRadioButtonMenuItem("AI Code Setter");
		setters.add(humanSetter);
		setters.add(aiSetter);
		
		//settingsMenu.addSeparator();
		playersMenu.add(humanGuesser);
		playersMenu.add(aiGuesser);

		playersMenu.addSeparator();
		playersMenu.add(humanSetter);
		playersMenu.add(aiSetter);
		
		easyMode = new JCheckBoxMenuItem("Easy Mode");
		increaseCodeLength = new JMenuItem("Increase Code Length");
		decreaseCodeLength = new JMenuItem("Decrease Code Length");
		settingsMenu.add(easyMode);
		settingsMenu.addSeparator();
		settingsMenu.add(increaseCodeLength);
		settingsMenu.add(decreaseCodeLength);
		addPegOption = new JMenuItem("Add Peg Option");
		removePegOption = new JMenuItem("Remove Peg Option");
		settingsMenu.addSeparator();
		settingsMenu.add(addPegOption);
		settingsMenu.add(removePegOption);

	}
}
