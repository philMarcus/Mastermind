package ai;

import baseGame.Code;

//Testing results:
//Games Won In: 
//1 turns: 72
//2 turns: 988
//3 turns: 7655
//4 turns: 32874
//5 turns: 44359
//6 turns: 13065
//7 turns: 974
//8 turns: 13
//9 turns: 0
//10 turns: 0
//Won 100000 out of 100000 games. 
//Mean attempts to win: 4.63616
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
