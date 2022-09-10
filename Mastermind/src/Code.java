
import java.util.ArrayList;

public class Code {
	
	private Peg[] pegs;
	
	// constructor for a Code from given pegs
	public Code(Peg[] givenPegs) {
			pegs = givenPegs.clone(); //clone to ensure this turns pegs aren't updated later.
	}
	
	// constructor to create Code with random pegs
	public Code(int codeLength, ArrayList<Peg> pegOptions) {
		pegs = new Peg[codeLength];
		for (int i=0; i < pegs.length;i++) {
			double rand = Math.random()*(pegOptions.size());
			int rInt = (int)rand;
			pegs[i] = pegOptions.get(rInt);
		}
	}
	
	public int getLength(){
		return pegs.length;
	}
	public Peg getPeg(int index) {
		return pegs[index];
	}
	public Peg[] getPegs() {
		return pegs;
	}
	public void setPeg(Peg peg,int index) {
		pegs[index]=peg;
	}
	
	//output Code as a String of Pegs
	public String toString() {
		String str = new String();
		for (int i=0; i < pegs.length; i++) {
			str = str + " " + pegs[i].getText();
		}
		return str;
	}
}
