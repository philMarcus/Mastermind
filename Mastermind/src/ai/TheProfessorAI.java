package ai;

import baseGame.Code;

//Games Won In: 
//1 turns: 818
//2 turns: 9943
//3 turns: 77718
//4 turns: 329708
//5 turns: 442418
//6 turns: 130151
//7 turns: 9133
//8 turns: 111
//9 turns: 0
//10 turns: 0
//Won 1000000 out of 1000000 games. 
//Mean attempts to win: 4.630505
//Median attempts to win: 5

public class TheProfessorAI implements AIPersonality {
private AnalyzedGame game;

public TheProfessorAI(AnalyzedGame g) {
	game = g;
}
	@Override
	//This personality chooses a Code randomly from the remaining
	//codes in the code universe.
	public Code getChoice() {
		CodeUniverse cU = game.getCodeUniverse(game.getTurnsTaken());
		int r = (int)(Math.random()*cU.getSize());
		return cU.getCodeUniverse().get(r);
	}

}
