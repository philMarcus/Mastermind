package ai;

import baseGame.Code;

public class TheProfessorAI implements AIPersonality {
private CodeUniverse cU;

public TheProfessorAI(CodeUniverse cU) {
	this.cU=cU;
}
	@Override
	//This personality chooses a Code randomly from the remaining
	//codes in the code universe.
	public Code getChoice() {
		int r = (int)(Math.random()*cU.getSize());
		return cU.getCodeUniverse().get(r);
	}

}
