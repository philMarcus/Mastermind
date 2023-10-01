package baseGame;

public class Response {
	private int numBlack=0;
	private int numWhite=0;
	private int len;
	
	// construct the response for a given code and secretcode.
	//Code and secretcode should be the same length.
	public Response(Code guess, Code secretCode) {
		len = guess.getLength();
		boolean[] matched = new boolean[len];
		for (int i=0; i < len; i++) {
			//if the guess and the peg are equal, a black pin is awarded
			if(guess.getPeg(i)==secretCode.getPeg(i)) {
				numBlack++;
				
				// if this secret code peg was used to match an earlier white pin,
				//then white pins are over-counted
				 //BUT ONLY IF check pegs AFTER i
				// there are NO OTHER identical pegs to the right of the match.
				if (matched[i]) {
					int addl = 0;
					for (int j = i+1; j<len; j++) {
						if (guess.getPeg(i)==secretCode.getPeg(j)) {
							addl++;
						}
					}
					if (addl == 0)
						numWhite--;
				}
				
				
				matched[i] = true;
			}
			else {
				//award a white pin if the code peg i matches any  other unmatched secret code peg
				//also check to make sure only one white pin is added for a match
				boolean addedWhite = false;
				for (int j=0; j  < len; j++) {
					if (guess.getPeg(i)==secretCode.getPeg(j) && /*i != j &&*/ !matched[j] && !addedWhite) {
						numWhite++;
						matched[j]=true;
						addedWhite=true;
					}
				}
			}
		}
	}
	
	//an empty response of numEmpty pins
	public Response(int numEmpty) {
	
		len = numEmpty;
	}
	//construct any response, e.g. for iterating over all possible responses
	public Response(int b, int w, int l) {
		numBlack = b;
		numWhite = w;
		len = l;
	}

	public int getNumBlack() {
		return numBlack;
	}

	public int getNumWhite() {
		return numWhite;
	}
	
	public int getLength() {
		return len;
	}
	
	public int getNumEmpty() {
		return len-numBlack-numWhite;
	}
	
	public int getNumPins() {
		return numBlack+numWhite;
	}
	//if all pins are black, it's a win!
	public boolean isVictory() {
		return (numBlack==len);
	}

	public boolean equals(Response r) {
		return (numBlack==r.getNumBlack()&&numWhite==r.getNumWhite());
	}
	public String toString() {
		String s ="";
		for(int i=0;i<numBlack;i++) 
			s+="b";
		for(int i=0;i<numWhite;i++)
			s+="w";
		if (isVictory())
			s+=" WIN!";
		if(getNumEmpty()==len)
			s="No Pins";
		
		return s;
	//	return "Black Pins: " + numBlack + " White Pins: " + numWhite;
	}
}