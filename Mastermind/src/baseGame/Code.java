package baseGame;

import java.util.ArrayList;

public class Code {
	
	private Peg[] pegs;
	
	// constructor for a Code from given pegs
	public Code(Peg[] givenPegs) {
			pegs = givenPegs.clone(); //clone to ensure this turns pegs aren't updated later.
	}
	

	// constructor to create Code with random pegs, if easy mode is true, then no pegs are repeated.
	public Code(int codeLength, ArrayList<Peg> pegOptions, boolean easyMode) {
		pegs = new Peg[codeLength];
		ArrayList<Peg> remainingOpts = (ArrayList<Peg>)pegOptions.clone();
		for (int i=0; i < pegs.length;i++) {
			double rand = Math.random()*(remainingOpts.size());
			int rInt = (int)rand;
			if (easyMode)
				pegs[i] = remainingOpts.remove(rInt);
			else 
				pegs[i]=remainingOpts.get(rInt);
			
		}
	}
	
	//constructor for a test code of length 4
	public Code(int p1, int p2, int p3, int p4, ArrayList<Peg> pegOptions) {
		pegs = new Peg[4];
		pegs[0]=pegOptions.get(p1);
		pegs[1]=pegOptions.get(p2);
		pegs[2]=pegOptions.get(p3);
		pegs[3]=pegOptions.get(p4);
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
