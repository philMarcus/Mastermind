package ai;

import java.util.ArrayList;

import baseGame.Code;
import baseGame.Game;
import baseGame.GameSettings;
import baseGame.Turn;

//This class represents a game of Mastermind extended to include an analysis of which
//codes remain as possibilities for the secret code (the "Code Universe") and the probabilities
//of finding any given peg in any given slot ("Peg possibilities")
public class AnalyzedGame extends Game {
	private AIAnalysis ai; //contains methods and structures to perform analysis
	private int turnsTaken;
	
	//this is a list contains the codeUniverse analysis for each turn in the game
	ArrayList<CodeUniverse> cUs= new ArrayList<>(); 
	
	//this is a list containing the peg possibility analysis for each turn
	ArrayList<ArrayList<PegPossibility>> pegAnalyses = new ArrayList<>();

	//set up a new game with given settings
	public AnalyzedGame(GameSettings settings) {
		super(settings);
		ai = new AIAnalysis(settings);
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		
		//determine and set the initial peg possibilities
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
		
	}
	
	//set up a new game with default settings
	public AnalyzedGame() {
		super();
		ai = new AIAnalysis(this.getSettings());
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		
		//determine and set the initial peg possibilities
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
	}
	
	//overrides the Game takeTurn method. in addition to taking the game turn,
	//processes the turn's analysis. Returns the taken turn.
	//takes the turn for a given code & response
	public Turn takeTurn(Turn t) {
		super.takeTurn(t);
		processTurn(t);
		return t;
		
	}
	
	//overrides the Game takeTurn method. in addition to taking the game turn,
	//processes the turn's analysis. Returns the taken turn.
	//takes the turn for a given code
	public Turn takeTurn(Code c) {
		Turn t = super.takeTurn(c);
		processTurn(t);
		return t;
	}
	
	//calls the AIAnalysis methods that will process a turn, i.e. determine
	//the new reamining code universe, and the new peg possibilities	
	private void processTurn(Turn t) {
		turnsTaken++;
		//call to ai to run the tuen's analysis
		ai.processTurn(t);
		//don't add more analyses to the arrays once the game is over.
		if(t.isVictory())
			return;
		//add the new codeUniverse to the list
		cUs.add(ai.getCodeUniverse());
		
		//determine and add the new turn's peg possibilities to the list
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
	}
	
	public int getTurnsTaken() {
		return turnsTaken;
	}
	
	//get the entire list of code universes for this game
	public ArrayList<CodeUniverse> getCodeUniverses() {
		return cUs;
	}
	
	//get the code universe for a single turn in this game
	public CodeUniverse getCodeUniverse(int i) {
		return cUs.get(i);
	}

	//get the entire list of peg possibilities for this game
	public ArrayList<ArrayList<PegPossibility>> getPegAnalyses() {
		return pegAnalyses;
	}
	
	//get the peg possibilities for a single turn in this game
	public ArrayList<PegPossibility> getPegAnalysis(int i){
		return pegAnalyses.get(i);
	}
	
	public String toString(){
		String s = "Turn "+(turnsTaken+1)+" | ";
		s+= this.getSettings().toString();
		s+= "\n"+ ai.toString();
		return s;
	}
	
}
