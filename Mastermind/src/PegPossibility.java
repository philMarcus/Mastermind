
//For each peg we need to keep track of the min and max number there could be in the code, as well
//as which slots the peg can't be found in and which slots they have been found in
public class PegPossibility {
	private Peg p;
	private int maxNum;
	private int minNum = 0;
	private int len;
	private boolean[] excludedSlots;
	private boolean[] locatedSlots;
	private static boolean update = true;

	public PegPossibility(Peg peg, int length) {
		len = length;
		// initially there could be any number of a peg, up to the length of the code
		// itself
		maxNum = length;
		p = peg;
		// flags slots this peg can't be in
		excludedSlots = new boolean[len];
		// flags slots this peg is definitely in
		locatedSlots = new boolean[len];

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

}