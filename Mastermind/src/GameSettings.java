
import java.awt.Color;
import java.util.ArrayList;


public class GameSettings {
	private int maxTries = 10;
	private int codeLength = 4;
	private boolean allowMultiples = true;
	private int numPegOptions;
	private ArrayList<Peg> pegOptions = new ArrayList<Peg>();
	//private String difficulty = "Normal";
	
	//settings for swing display
	private static int pegWidth = 30;
	private static int pegSlotWidth = 40;
	private static int pegSlotHeight = 35;
	private static int pinWidth = pegWidth/3;
	private static int pinSlotWidth = pegSlotWidth/2;
	private static int pinSlotHeight = pegSlotHeight/2;
	
	// add the six default colors to pegOptions list and set numPegOptions	
	public GameSettings() {
	pegOptions.add(new Peg(Color.BLACK, "Black", "b"));
	pegOptions.add(new Peg(Color.WHITE, "White", "w"));
	pegOptions.add(new Peg(Color.RED, "Red", "r"));
	pegOptions.add(new Peg(Color.GREEN, "Green", "g"));
	pegOptions.add(new Peg(Color.BLUE, "Blue", "u"));
	pegOptions.add(new Peg(Color.YELLOW, "Yellow", "y"));
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
	
	

	
}
