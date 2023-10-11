package ai;

import baseGame.Code;

//Games Won In: 
//1 turns: 771576
//2 turns: 9841997
//3 turns: 77085437
//4 turns: 329423907
//5 turns: 442875492
//6 turns: 130637311
//7 turns: 9251496
//8 turns: 112631
//9 turns: 153
//10 turns: 0
//Won 1000000000 out of 1000000000 games. 
//Mean attempts to win: 4.633271732
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
