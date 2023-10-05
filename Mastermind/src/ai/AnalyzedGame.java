package ai;

import java.util.ArrayList;

import baseGame.Code;
import baseGame.GameEngine;
import baseGame.GameSettings;
import baseGame.Turn;

public class AnalyzedGame extends GameEngine {
	private AIAnalysis ai;
	private int turnsTaken;
	ArrayList<CodeUniverse> cUs= new ArrayList<>();
	ArrayList<ArrayList<PegPossibility>> pegAnalyses = new ArrayList<>();

	public AnalyzedGame(GameSettings settings) {
		super(settings);
		ai = new AIAnalysis(settings);
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
		
	}
	
	public AnalyzedGame() {
		super();
		ai = new AIAnalysis(this.getSettings());
		//first element in the codeUniverse array correspond to the initial codeUniverse
		//so indices match the turn to the code universe it was chosen from.
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
	}
	
	public Turn takeTurn(Turn t) {
		super.takeTurn(t);
		processTurn(t);
		return t;
		
	}
	
	public Turn takeTurn(Code c) {
		Turn t = super.takeTurn(c);
		processTurn(t);
		return t;
	}
	
	private void processTurn(Turn t) {
		turnsTaken++;
		ai.processTurn(t);
		//don't add more analyses to the arrays once the game is over.
		if(t.isVictory())
			return;
		cUs.add(ai.getCodeUniverse());
		ai.setPegPossibilities();
		pegAnalyses.add(ai.getPegPossibilities());
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
	
	public String toString(){
		int len = this.getSettings().getCodeLength();
		int opts = this.getSettings().getNumPegOptions();
		String s = "Turn "+(turnsTaken+1);
		if(len==4 && opts==6)
			s += " | Standard Game ";
		else s+= " | Code Length: "+len+" | Peg Options: " + opts;
		if (this.getSettings().isEasyMode())
			s+=" EZ";
		s+= "\n"+ ai.toString();
		return s;
	}
	
}
