package ai;

import java.util.ArrayList;
import java.util.Iterator;

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

	private ArrayList<Code> codeUniverse = new ArrayList<>();
	private int len;
	private ArrayList<Peg> opts;
	private int numOpts;

	// Function to compute Nth digit
	// from right in base B
	static int nthDigit(int a, int n, int b) {

		// Skip N-1 Digits in Base B
		for (int i = 1; i < n; i++)
			a = a / b;

		// Nth Digit from right in Base B
		return a % b;
	}

	public CodeUniverse(GameSettings settings) {
		len = settings.getCodeLength();
		opts = settings.getPegOptions();
		numOpts = settings.getNumPegOptions();

		// generate an arrayList of each of (numOptios^length) possible Codes
		for (int i = 0; i < Math.pow(numOpts, len); i++) {
			int[] vals = new int[len];
			for (int slot = 0; slot < len; slot++) {
				vals[slot] = nthDigit(i, len - slot, numOpts);
			}
			codeUniverse.add(new Code(vals, opts));

		}
	}
	
	public void processTurn(Turn t) {
		for (int i=0;i<codeUniverse.size();i++) {
			Code c = codeUniverse.get(i);
			//generate r, the response turn's guess would generate if c was the secret code
			Response r = new Response(t.getGuess(),c);
			//remove c from the codeUniverse if r doesn't match the turn's response
			if(!(r.equals(t.getResponse()))){
				codeUniverse.remove(i);
				i--;
			}
		}
	}
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
