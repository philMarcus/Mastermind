package baseGame;

public class Turn {

	private Code guess;
	private Response response;
	private boolean isVictory;
	
	public Turn(Code guessPegs, Response responsePins) {
		guess = new Code(guessPegs.getPegs());
		response = responsePins;
		if (response.getNumBlack() == guess.getLength()) isVictory = true;
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

	public boolean isVictory() {
		return isVictory;
	}
	
	public String toString() {
			return guess.toString()+" Response: "+response.toString();
	}
}
