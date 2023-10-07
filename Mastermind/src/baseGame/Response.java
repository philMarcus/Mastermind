package baseGame;

import java.util.ArrayList;

public class Response {
	private int numBlack = 0;
	private int numPins = 0;
	private int len;

	// construct the response for a given code and secretcode.
	// Code and secretcode should be the same length.
	public Response(Code guess, Code secretCode) {
		len = guess.getLength();
		ArrayList<Peg> checkedPegs = new ArrayList<>();

		for (int i = 0; i < len; i++) {
			// find number of black pins:
			// if the guess and the peg are equal, a black pin is awarded
			if (guess.getPeg(i) == secretCode.getPeg(i))
				numBlack++;

			// find total number of pins
			Peg p = guess.getPeg(i);
			//ensure we haven't already counted pins form this peg
			if (!checkedPegs.contains(p)) {
				// counts for this peg in guess and secret codes
				int g = 0;
				int s = 0;
				for (int j = 0; j < len; j++) {
					if (guess.getPeg(j) == p)
						g++;
					if (secretCode.getPeg(j) == p)
						s++;
				}
				// add to num pins the smaller of the two counts
				if (g < s)
					numPins += g;
				else
					numPins += s;
				// add to list of checked pegs to avoid doubly counting repeating pegs
				checkedPegs.add(p);
			}
		}
	}

	// an empty response of numEmpty pins
	public Response(int numEmpty) {

		len = numEmpty;
	}

	// construct any response, e.g. for iterating over all possible responses
	public Response(int b, int w, int l) {
		numBlack = b;
		numPins = b + w;
		len = l;
	}


	public int getNumBlack() {
		return numBlack;
	}

	public int getNumWhite() {
		return numPins - numBlack;
	}

	public int getLength() {
		return len;
	}

	public int getNumEmpty() {
		return len - numPins;
	}

	public int getNumPins() {
		return numPins;
	}

	// if all pins are black, it's a win!
	public boolean isVictory() {
		return (numBlack == len);
	}

	public boolean equals(Response r) {
		return (numBlack == r.getNumBlack() && numPins == r.getNumPins());
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < numBlack; i++)
			s += "b";
		for (int i = 0; i < getNumWhite(); i++)
			s += "w";
		if (isVictory())
			s += " WIN!";
		if (getNumEmpty() == len)
			s = "No Pins";

		return s;

	}
}