package baseGame;

import java.awt.Color;
import java.util.ArrayList;

public class GameSettings {
	public final int MAXCODELENGTH = 7; // longer than 7 takes a very long time to analyze

	private int maxTries = 10;
	private int codeLength = 4;

	private ArrayList<Peg> pegOptions = new ArrayList<Peg>();
	private ArrayList<Peg> reservePegOptions = new ArrayList<Peg>();

	// setting to disallow repeating pegs in secret code
	private boolean easyMode = false;
	// settings to have ai guess and set codes
	private boolean aiGuesser = false;
	private boolean aiSetter = true;

	public final String TITLE = "Marcus Mastermind";

	// settings for swing display
	private static int pegWidth = 30;
	private static int pegSlotWidth = 40;
	private static int pegSlotHeight = 35;
	private static int pinWidth = pegWidth / 3;
	private static int pinSlotWidth = pegSlotWidth / 2;
	private static int pinSlotHeight = pegSlotHeight / 2;

	// displaying AI analyses in console
	private boolean printNumCodes = true;
	private boolean printCodeUniverse = false;
	private boolean printPegPossibilities = true;
	private boolean printPegProbabilities = true;

	// add the six default colors to pegOptions list and set numPegOptions
	public GameSettings() {
		pegOptions.add(new Peg(Color.BLACK, "Black", "B"));
		pegOptions.add(new Peg(Color.WHITE, "White", "W"));
		pegOptions.add(new Peg(Color.RED, "Red", "R"));
		pegOptions.add(new Peg(Color.GREEN, "Green", "G"));
		pegOptions.add(new Peg(Color.BLUE, "Blue", "U"));
		pegOptions.add(new Peg(Color.YELLOW, "Yellow", "Y"));

		reservePegOptions.add(new Peg(Color.ORANGE, "Orange", "O"));
		reservePegOptions.add(new Peg(Color.LIGHT_GRAY, "Gray", "A"));
		reservePegOptions.add(new Peg(Color.CYAN, "Cyan", "C"));
		reservePegOptions.add(new Peg(Color.PINK, "Pink", "P"));
	}

	public void addPegOption() {
		int r = reservePegOptions.size();
		if (r > 0)
			pegOptions.add(reservePegOptions.remove(r - 1));

	}

	public void removePegOption() {
		int r = pegOptions.size();
		if (r > 0)
			reservePegOptions.add(pegOptions.remove(pegOptions.size() - 1));

	}

	public boolean setPegOptions(int num) {
		// ensure num is within the allowed range of peg options
		if (num > getMaxPegOptions() || num < 1) {
			return false;
		}
		int old = getNumPegOptions();
		//call addPegOption or removePegOption enough
		//times to make up the difference between old and num
		if (num > old) {
			for (int i = 0; i < num - old; i++)
				addPegOption();
		}
		if (num < old) {
			for (int i = 0; i < old - num; i++)
				removePegOption();
		}
		return true;
	}

//	//construct a non-default color option Settings
//	public GameSettings (ArrayList<Peg> nonDefaultPegOption) {
//		pegOptions = nonDefaultPegOption;
//		numPegOptions = pegOptions.size();
//	}

	public int getMaxTries() {
		return maxTries;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public boolean isEasyMode() {
		return easyMode;
	}

	public void setEasyMode(boolean easyMode) {
		this.easyMode = easyMode;
	}

	public int getNumPegOptions() {
		return pegOptions.size();
	}

	public int getMaxPegOptions() {
		return pegOptions.size() + reservePegOptions.size();
	}

	public ArrayList<Peg> getPegOptions() {
		return pegOptions;
	}

	public static int getPegWidth() {
		return pegWidth;
	}

	public static int getPegSlotWidth() {
		return pegSlotWidth;
	}

	public static int getPegSlotHeight() {
		return pegSlotHeight;
	}

	public static int getPinWidth() {
		return pinWidth;
	}

	public static int getPinSlotWidth() {
		return pinSlotWidth;
	}

	public static int getPinSlotHeight() {
		return pinSlotHeight;
	}

	public boolean isPrintCodeUniverse() {
		return printCodeUniverse;
	}

	public boolean isPrintNumCodes() {
		return printNumCodes;
	}

	public void setPrintNumCodes(boolean printNumCodes) {
		this.printNumCodes = printNumCodes;
	}

	public boolean isPrintPegPossibilities() {
		return printPegPossibilities;
	}

	public boolean isPrintPegProbabilities() {
		return printPegProbabilities;
	}

	public boolean isAiGuesser() {
		return aiGuesser;
	}

	public void setAiGuesser(boolean aiGuesser) {
		this.aiGuesser = aiGuesser;
	}

	public boolean isAiSetter() {
		return aiSetter;
	}

	public void setAiSetter(boolean aiSetter) {
		this.aiSetter = aiSetter;
	}

	public String toString() {
		String s = "";
		if (codeLength == 4 && getNumPegOptions() == 6)
			s += "Standard Game ";
		else
			s += "Code Length: " + codeLength + " | Peg Options: " + getNumPegOptions();
		if (easyMode)
			s += " EZ";
		return s;
	}

}
