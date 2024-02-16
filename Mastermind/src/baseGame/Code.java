package baseGame;

import java.util.ArrayList;


//This class represents a "code" in the game. In the standard game, this consists of a series
//of four pegs of six possible colors. The goal of the game is for the player to choose
//a code identical to the "secret code" set at the start of the game
public class Code {

	//the code is stored as an array of Pegs.
	private Peg[] pegs;

	// constructor for a Code from given pegs
	public Code(Peg[] givenPegs) {
		pegs = givenPegs.clone(); // clone to ensure this turns pegs aren't updated later.
	}

	// constructor to create Code with random pegs, if easy mode is true, then no
	// pegs are repeated.
	public Code(int codeLength, ArrayList<Peg> pegOptions, boolean easyMode) {
		pegs = new Peg[codeLength];
		ArrayList<Peg> remainingOpts = (ArrayList<Peg>) pegOptions.clone();
		for (int i = 0; i < pegs.length; i++) {
			double rand = Math.random() * (remainingOpts.size());
			int rInt = (int) rand;
			if (easyMode)
				//on easy mode, a used peg should be removed from options, so that
				//it can't be used again.
				pegs[i] = remainingOpts.remove(rInt);
			else
				pegs[i] = remainingOpts.get(rInt);
		}
	}

	// constructor to create Code with random pegs
	public Code(int codeLength, ArrayList<Peg> pegOptions) {
		pegs = new Peg[codeLength];
		ArrayList<Peg> remainingOpts = (ArrayList<Peg>) pegOptions.clone();
		for (int i = 0; i < pegs.length; i++) {
			double rand = Math.random() * (remainingOpts.size());
			int rInt = (int) rand;
			pegs[i] = remainingOpts.get(rInt);
		}
	}

	// constructor for an array of integers. The integers represent the index of
	//the desired peg in the pegOptions list
	public Code(int[] ints, ArrayList<Peg> pegOptions) {
		pegs = new Peg[ints.length];
		for (int i = 0; i < pegs.length; i++) {
			pegs[i] = pegOptions.get(ints[i]);
		}
	}

	//returns the number of pegs in a code
	public int getLength() {
		return pegs.length;
	}

	//returns a specific peg from this code
	public Peg getPeg(int index) {
		return pegs[index];
	}

	//returns the array of pegs in this code
	public Peg[] getPegs() {
		return pegs;
	}

	//sets a single peg in this code
	public void setPeg(Peg peg, int index) {
		pegs[index] = peg;
	}

	// output Code as a String of Pegs
	public String toString() {
		String str = new String();
		for (int i = 0; i < pegs.length; i++) {
			str = str + " " + pegs[i].getText();
		}
		return str;
	}
}
