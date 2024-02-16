package baseGame;

//This class represents a single turn of a game of Mastermind. The turn consists of a 
//guessed Code and the Response based on how well the guessed code matches the secret code.
public class Turn {
	//The code guessed this turn
	private Code guess;
	//The response, detailing how well the guessed coed matches the secret code
	private Response response;
	
	public Turn(Code guessPegs, Response responsePins) {
		guess = guessPegs;
		response = responsePins;
		}
	
	//returns the number of times a given peg was guessed this turn
	public int numGuessed(Peg peg) {
		int n=0;
		 for(int i=0; i< guess.getLength();i++) {
			 if (guess.getPeg(i) == peg) n++;
		 }
		return n;
	}

	public Code getGuess() {
		return guess;
	}

	public Response getResponse() {
		return response;
	}

	//if the number of black pins is the same as the code length, it's a win!
	public boolean isVictory() {
		return response.getNumBlack() == guess.getLength();
	}
	
	public String toString() {
			return guess.toString()+" Response: "+response.toString();
	}
}
