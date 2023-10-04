package ai;

import java.util.ArrayList;

import baseGame.GameEngine;
import baseGame.GameSettings;
import baseGame.Turn;

public class AnalyzedGame extends GameEngine {
	private AI ai;
	private int turnsTaken;
	ArrayList<CodeUniverse> cUs= new ArrayList<>();
	ArrayList<ArrayList<PegPossibility>> pegAnalyses = new ArrayList<>();

	public AnalyzedGame(GameSettings settings) {
		super(settings);
		ai = new AI(settings);
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
	}
	
	public AnalyzedGame() {
		super();
		ai = new AI(this.getSettings());
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
	}
	
	public boolean takeTurn(Turn t) {
		super.takeTurn(t);
		turnsTaken++;
		ai.processTurn(t);
		//don't add more analyses to the arrays once the game is over.
		if(t.isVictory())
			return true;
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
		return(t.isVictory());
		
	}

	public int getTurnsTaken() {
		return turnsTaken;
	}

	public ArrayList<CodeUniverse> getCodeUniverses() {
		return cUs;
	}
	
	public CodeUniverse getCodeUniverse(int i) {
		return cUs.get(i);
	}

	public ArrayList<ArrayList<PegPossibility>> getPegAnalyses() {
		return pegAnalyses;
	}
	
	public ArrayList<PegPossibility> getPegAnalysis(int i){
		return pegAnalyses.get(i);
	}
	
}
