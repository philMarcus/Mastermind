package ai;

import java.util.ArrayList;

import baseGame.GameSettings;
import baseGame.Peg;
import baseGame.Turn;
import baseGame.Code;
import baseGame.Response;

//A CodeUniverse is the set of all codes that are possible solutions to
//a game of Mastermind. When a turn is processed, all codes that would not have 
//generated the turn's response are removed from the universe.
//
public class CodeUniverse {

	// a list of all codes consistent with the game's responses so far
	private ArrayList<Code> codeUniverse = new ArrayList<>();

	private int len; // code length
	private ArrayList<Peg> opts; // list of pegs in the game
	private int numOpts; // number of pegs options in this game

	// method to compute nth digit
	// from right of integer a in base b
	static int nthDigit(int a, int n, int b) {

		// Skip n-1 Digits in Base b
		for (int i = 1; i < n; i++)
			a = a / b;

		// nth Digit from right in Base b
		return a % b;
	}

	// determines if an array of digits (pegs) contains any repeats
	// returns true if so.
	boolean repeatsDigits(int[] a) {
		for (int i = 0; i < len; i++)
			for (int j = 0; j < i; j++)
				if ((a[i] == a[j]))
					return true;
		return false;

	}

	// Create the initial code universe for a given game settings. This is
	// accomplished by considering codes as a "length"-digit number
	// in a base corresponding to the number of peg options.
	//
	// Then, loop over each integer from 0 to numOptions^length-1, and create
	// a code corresponding to the digits in the integer in the proper base.
	//
	// The result is a list of all possible codes for the given game settings
	public CodeUniverse(GameSettings settings) {
		len = settings.getCodeLength(); // number of "digits" in the code
		opts = settings.getPegOptions(); // peg options in the game

		// number of peg options is the number of different digits,
		// ergo the base of the number representing the code
		numOpts = settings.getNumPegOptions();

		// generate an arrayList of each of (numOptios^length) possible Codes
		for (int i = 0; i < Math.pow(numOpts, len); i++) {
			// create an array of ints to contain the digits of the code in base_numOpts
			int[] vals = new int[len];
			// determine each digit in the array
			for (int slot = 0; slot < len; slot++) {
				vals[slot] = nthDigit(i, len - slot, numOpts);
			}
			// create a new code from the array of digits and add it to the list.
			// easy mode games can't have codes with repeated digits, so make this check
			// before adding the code to the list.
			if (!settings.isEasyMode() || !repeatsDigits(vals))
				codeUniverse.add(new Code(vals, opts));

		}
	}

	// this method updates the codeuniverse after a given turn
	// by removing codes that would not have generated this turn's response
	public void processTurn(Turn t) {
		for (int i = 0; i < codeUniverse.size(); i++) {
			//get the code c in the universe
			Code c = codeUniverse.get(i);
			// generate r, the response turn's guess would generate if c was the secret code
			Response r = new Response(t.getGuess(), c);
			// remove c from the codeUniverse if r doesn't match the turn's response
			if (!(r.equals(t.getResponse()))) {
				codeUniverse.remove(i);
				//if a code is removed, index needs to be decreased.
				i--;
			}
		}
	}

	//get the number of possible codes remaining
	public int getSize() {
		return codeUniverse.size();
	}

	public String toString() {
		String s = "";
		for (Code c : codeUniverse) {
			s += c.toString() + "\n";
		}

		return s;
	}

	public int getLength() {
		return len;
	}

	public ArrayList<Code> getCodeUniverse() {
		return codeUniverse;
	}

	public ArrayList<Peg> getOpts() {
		return opts;
	}

	public int getNumOpts() {
		return numOpts;
	}
}
