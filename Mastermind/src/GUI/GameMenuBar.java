package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

//This class represents the menu bar on the Mastermind game window
public class GameMenuBar extends JMenuBar {
	//menu bar contains a "game" menu, a "players" menu
	//and a "settings" menu
	JMenu gameMenu;
	JMenu playersMenu;
	JMenu settingsMenu;
	
	// game menu items
	JMenuItem reset; //begins a new game with the currect settings
	JMenuItem showAnalysis; //opens the window dislaying game analysis info
	JMenuItem aiBatches; //opens the window to run a batch of AI-player games
	
	// players menu items
	JRadioButtonMenuItem humanGuesser; //sets the player to human
	JRadioButtonMenuItem aiGuesser; //sets the player to AI
	JRadioButtonMenuItem humanSetter; //allows the human entry of the secret code
	JRadioButtonMenuItem aiSetter; // secret code is generated at random
	
	// settings menu items
	JCheckBoxMenuItem easyMode; //easy mode prevents repeated pegs in the secret code
	JMenuItem increaseCodeLength; //begins a new game with the code length increased by one
	JMenuItem decreaseCodeLength; //begins a new game with the code length decreased by one
	JMenuItem addPegOption; //begins a new game with peg options increased by one
	JMenuItem removePegOption; //begins a new game with peg options decreased by one
	JMenuItem defaultSettings; //begins a new game with the default settings

	public GameMenuBar() {
		//initialize menus
		gameMenu = new JMenu("Game");
		playersMenu = new JMenu("Players");
		settingsMenu = new JMenu("Settings");

		//add menus to menu bar
		this.add(gameMenu);
		this.add(playersMenu);
		this.add(settingsMenu);

		//initialize and add items to game menu
		reset = new JMenuItem("Reset");
		gameMenu.add(reset);
		showAnalysis = new JMenuItem("Show Game Analysis");
		gameMenu.add(showAnalysis);
		aiBatches = new JMenuItem("AI Batch Games");
		gameMenu.add(aiBatches);

		//create the "guessers" button group, so only one of the 
		//guesser items can be selected
		ButtonGroup guessers = new ButtonGroup();
		humanGuesser = new JRadioButtonMenuItem("Human Guesser");
		aiGuesser = new JRadioButtonMenuItem("AI Guesser");
		guessers.add(humanGuesser);
		guessers.add(aiGuesser);
		
		//acreate the "setters" button group, so only one of the 
		//setter items can be selected
		ButtonGroup setters = new ButtonGroup();
		humanSetter = new JRadioButtonMenuItem("Human Code Setter");
		aiSetter = new JRadioButtonMenuItem("AI Code Setter");
		setters.add(humanSetter);
		setters.add(aiSetter);
		
		//add items to the players menu
		playersMenu.add(humanGuesser);
		playersMenu.add(aiGuesser);
		playersMenu.addSeparator();
		playersMenu.add(humanSetter);
		playersMenu.add(aiSetter);
		

		//initialize and add settings menu items to settings menu
		easyMode = new JCheckBoxMenuItem("Easy Mode");
		settingsMenu.add(easyMode);
		
		increaseCodeLength = new JMenuItem("Increase Code Length");
		decreaseCodeLength = new JMenuItem("Decrease Code Length");
		settingsMenu.addSeparator();
		settingsMenu.add(increaseCodeLength);
		settingsMenu.add(decreaseCodeLength);
		
		addPegOption = new JMenuItem("Add Peg Option");
		removePegOption = new JMenuItem("Remove Peg Option");
		settingsMenu.addSeparator();
		settingsMenu.add(addPegOption);
		settingsMenu.add(removePegOption);
		

		defaultSettings = new JMenuItem("Default Settings");
		settingsMenu.addSeparator();
		settingsMenu.add(defaultSettings);
	}
}
