package ai;

import baseGame.Code;


//The AIPersonality interface allows for different ai strategies. The intent is to make
//some AIs who don't have perfect information, or who don't make the best decisions, so
//that in future versions, a human player can play "against" a series of CPU opponents of
//varying difficulty. To date, the only implementation is the world-beating TheProfessorAI
public interface AIPersonality {

	//returns the code that is the AIs next guess
	Code getChoice();
	
}

