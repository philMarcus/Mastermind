package baseGame;

import java.awt.Color;
import java.util.ArrayList;


public class GameSettings {
	private int maxTries = 10;
	private int codeLength = 4;
	private boolean allowMultiples = true;
	private int numPegOptions;
	private ArrayList<Peg> pegOptions = new ArrayList<Peg>();
	private boolean easyMode = false;
	
	//settings for swing display
	private static int pegWidth = 30;
	private static int pegSlotWidth = 40;
	private static int pegSlotHeight = 35;
	private static int pinWidth = pegWidth/3;
	private static int pinSlotWidth = pegSlotWidth/2;
	private static int pinSlotHeight = pegSlotHeight/2;
	
	//displaying AI analyses in console
	private boolean printCodeUniverse=false;
	private boolean printPegPossibilities=true;
	private boolean printPegProbabilities=true;
	
	
	// add the six default colors to pegOptions list and set numPegOptions	
	public GameSettings() {
	pegOptions.add(new Peg(Color.BLACK, "Black", "B"));
	pegOptions.add(new Peg(Color.WHITE, "White", "W"));
	pegOptions.add(new Peg(Color.RED, "Red", "R"));
	pegOptions.add(new Peg(Color.GREEN, "Green", "G"));
	pegOptions.add(new Peg(Color.BLUE, "Blue", "U"));
	pegOptions.add(new Peg(Color.YELLOW, "Yellow", "Y"));
	numPegOptions = pegOptions.size();

	}
	

	
	//construct a non-default color option Settings
	public GameSettings (ArrayList<Peg> nonDefaultPegOption) {
		pegOptions = nonDefaultPegOption;
		numPegOptions = pegOptions.size();
	}
	
	
	public int getMaxTries() {
		return maxTries;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public boolean isEasyMode() {
		return easyMode;
	}

	public void setEasyMode(boolean easyMode) {
		this.easyMode = easyMode;
	}

	public boolean allowsMultiples() {
		return allowMultiples;
	}

	public int getNumPegOptions() {
		return numPegOptions;
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


	public boolean isPrintPegPossibilities() {
		return printPegPossibilities;
	}


	public boolean isPrintPegProbabilities() {
		return printPegProbabilities;
	}


	

	
}
