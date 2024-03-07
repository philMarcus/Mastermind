package ai;

import baseGame.Code;
import baseGame.Peg;

//PegPossibility is a class intended to keep track of the min and max number of pegs  
//there could be in the secret code, as well
//as the probability of finding each peg in each slot
public class PegPossibility {
	private Peg p; // the peg under analysis

	// the maximum number of times this peg could be found in the secret code
	private int maxNum;

	// the minimum number of times this peg could be found in the secret code
	private int minNum;

	private int len; // code length

	// the fraction of remaining possible codes that contain the peg in each slot
	private double[] probabilityPerSlot;

	// construct the peg possibility for a given peg and a given code universe
	public PegPossibility(Peg peg, CodeUniverse cU) {
		len = cU.getLength(); // code length
		p = peg;

		// fraction of remaining codes containing this peg in each slot
		probabilityPerSlot = new double[len];

		// process code universe to update probabilityPerSlot and min and max nums
		processCU(cU);

	}

	// analyze a codeUniverse to determine the max and min number of times the peg
	// can appear in the secret code, and the probabilities of finding the peg in
	// each slot
	private void processCU(CodeUniverse cU) {
		int tot = cU.getSize(); // number of possible codes in the code universe
		// iterate through possible codes and count how many matches the peg has in each
		// slot
		for (Code c : cU.getCodeUniverse()) {
			int count = 0; // count of the peg in this code
			for (int slot = 0; slot < len; slot++) {
				// check if the peg is found in the slot in this code
				if (c.getPeg(slot) == p) {
					// add to the count of the codes in which this peg is found in the slot
					probabilityPerSlot[slot]++;
					// add to the count of this peg in this code
					count++;
				}
			}
			// update min and max nums based on the count of the peg in the code
			if (count < minNum)
				minNum = count;
			if (count > maxNum)
				maxNum = count;
		}
		// divide count of codes with the peg in each slot by total codes
		for (int slot = 0; slot < len; slot++) {
			probabilityPerSlot[slot] /= tot;
		}

	}

	// return the peg under analysis
	public Peg getPeg() {
		return p;
	}

	// returns true if the peg is guaranteed to be found at least once in the secret
	// code
	public boolean isIdentified() {
		if (minNum > 0)
			return true;
		else
			return false;
	}

	// return the max number of times the peg could be found in the secret code
	public int getMaxNum() {
		return maxNum;
	}

	// return the minimum number of times the peg could be found in the secret code
	public int getNumIdentified() {
		return minNum;
	}

	// returns true if we are certain of how many times this peg is found in the
	// secret code
	public boolean numIsCertain() {
		return (maxNum == minNum);
	}

	// returns the number of slots in which this peg is definitely not found
	public int getNumExcludedSlots() {
		int n = 0;
		for (double x : probabilityPerSlot) {
			if (x == 0)
				n++;
		}
		return n;
	}

	// returns true if the peg is definitely not found in slot i
	public boolean isExcludedAt(int i) {
		return probabilityPerSlot[i] == 0;
	}

	// returns the number of slots in which this peg will definitely be found in the
	// secret code
	public int getNumLocated() {
		int n = 0;
		for (double x : probabilityPerSlot) {
			if (x == 1)
				n++;
		}
		return n;
	}

	// returns true if there is at least one slot in which we know this peg will be
	// found
	public boolean isLocated() {
		return (getNumLocated() > 0);
	}

	// returns true if we know this peg is in slot i in the secret code
	public boolean isLocatedAt(int i) {
		return probabilityPerSlot[i] == 1;
	}

	// prints the peg possibility as a string, if probs is true, will print the
	// peg probabilities as well as the peg possibilities
	public String toString(Boolean probs) {
		String s = new String();
		s += getPeg().getKey() + " " + getNumIdentified() + "-" + getMaxNum();
		s += " [";
		for (int i = 0; i < len; i++) {
			if (isLocatedAt(i))
				s += "O";
			else if (isExcludedAt(i))
				s += "x";
			else
				s += ".";
		}
		s += "]";
		if (probs) {
			s += "[";
			for (int i = 0; i < len; i++) {
				int pct = (int) (probabilityPerSlot[i] * 100 + 0.5);
				s += " " + pct + "%";
			}
			s += "]";
		}
		s += System.lineSeparator();
		return s;
	}

}