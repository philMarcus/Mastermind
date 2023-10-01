package ai;

import baseGame.Code;
import baseGame.Peg;

//For each peg we need to keep track of the min and max number there could be in the code, as well
//as which slots the peg can't be found in and which slots they have been found in
public class PegPossibility {
	private Peg p;
	private int maxNum;
	private int minNum;
	private int len;
	private boolean[] excludedSlots;
	private boolean[] locatedSlots;
	private double[] probabilityPerSlot;
	private static boolean update = true;

	public PegPossibility(Peg peg, CodeUniverse cU) {
		len = cU.getLength();
		// initially there could be any number of a peg, up to the length of the code
		// itself
		minNum = len;
		maxNum = 0;
		p = peg;
		// flags slots this peg can't be in
		excludedSlots = new boolean[len];
		// flags slots this peg is definitely in
		locatedSlots = new boolean[len];
		// fraction of remaining codes containing this peg in each slot
		probabilityPerSlot = new double[len];

		processCU(cU);

	}

	private void processCU(CodeUniverse cU) {
		int tot = cU.getSize();
		// iterate through possible codes and count how many matches the peg has in each
		// slot
		for (Code c : cU.getCodeUniverse()) {
			int count = 0;
			for (int slot = 0; slot < len; slot++) {
				if (c.getPeg(slot) == p) {
					probabilityPerSlot[slot]++;
					count++;
				}
			}
			if (count < minNum)
				setMinNum(count);
			if (count > maxNum)
				setMaxNum(count);
		}
		// divide count by total for probability
		for (int slot = 0; slot < len; slot++) {
			probabilityPerSlot[slot] /= tot;
			// exclude slots with no possible codes
			if (probabilityPerSlot[slot] <= 0)
				excludeSlot(slot);
			// mark as located slots in which every code contains this peg
			if (probabilityPerSlot[slot] >= 1)
				setLocatedSlot(slot);
		}

	}

	public Peg getPeg() {
		return p;
	}

	public boolean isIdentified() {
		if (minNum > 0)
			return true;
		else
			return false;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int max) {
		maxNum = max;
		update = true;
	}

	public int getNumIdentified() {
		return minNum;
	}

	public void setMinNum(int min) {
		minNum = min;
		update = true;
	}

	public boolean numIsCertain() {
		return (maxNum == minNum);
	}

	public int getNumExcludedSlots() {
		int n = 0;
		for (boolean x : excludedSlots) {
			if (x)
				n++;
		}
		return n;
	}

	public boolean isExcludedAt(int i) {
		return excludedSlots[i];
	}

	public int getNumLocated() {
		int n = 0;
		for (boolean x : locatedSlots) {
			if (x)
				n++;
		}
		return n;
	}

	public boolean isLocated() {
		return (getNumLocated() > 0);
	}

	public boolean isLocatedAt(int i) {
		return locatedSlots[i];
	}

	public void excludeSlot(int slot) {
		excludedSlots[slot] = true;
		update = true;
		// always ensure maxNum is no larger than number of non-excluded slots.
		int max = len - getNumExcludedSlots();
		if (getMaxNum() > max)
			setMaxNum(max);
	}

	public void setLocatedSlot(int slot) {
		locatedSlots[slot] = true;
		update = true;
		// always ensure minNum is as large as number of peg locations
		if (getNumIdentified() < getNumLocated())
			setMinNum(getNumLocated());
	}

	public static boolean needsUpdate() {
		return update;
	}

	public static void resetUpdatedFlag() {
		update = false;
	}

	public static void setNeedsUpdate() {
		update = true;
	}

	public String toString(Boolean probs) {
		String s = new String();
		s += getPeg().getKey()+" "+getNumIdentified()+"-"+getMaxNum();
		s +=" [";
		for(int i=0; i<len;i++) {
			if (isLocatedAt(i)) s+="O";
			else if (isExcludedAt(i)) s+="x";
			else s+=".";
		}
		s += "]";
		if(probs) {
			s+="[";
			for(int i=0; i<len;i++) {
				int pct = (int)(probabilityPerSlot[i]*100+0.5);
				s+=" "+pct+"%";
			}
			s+="]";
		}
		s+=System.lineSeparator();
		return s;
	}

}