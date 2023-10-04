package ai;

import baseGame.Code;

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
