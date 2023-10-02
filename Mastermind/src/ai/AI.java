package ai;

import java.util.ArrayList;
import java.util.Iterator;

import baseGame.GameSettings;
import baseGame.Peg;
import baseGame.Turn;

//Calculations based on the game state; used to inform guesses by specific AI strategies(ists?).
public class AI {
	private CodeUniverse cU;
	private ArrayList<PegPossibility> ps = new ArrayList<>();
	private GameSettings settings;
	private ArrayList<Peg> opts;

	public AI(GameSettings s) {
		settings = s;
		cU = new CodeUniverse(settings);
		opts = settings.getPegOptions();
		setPegPossibilities();

	}

	public void setPegPossibilities() {
		// initialize the peg-possibilities array
		ps = new ArrayList<>();
		Iterator<Peg> it = opts.iterator();
		while (it.hasNext()) {
			ps.add(new PegPossibility(it.next(), cU));
		}
	}

	public void processTurn(Turn t) {
		cU.processTurn(t);
		setPegPossibilities();
		if (settings.isPrintCodeUniverse())
			System.out.println(cU.toString());
		if(settings.isPrintNumCodes())
			System.out.println(cU.getSize()+" possible codes.\n");
		if (settings.isPrintPegPossibilities()) {
			String s = "";
			for (PegPossibility p : ps) {
				s += p.toString(settings.isPrintPegProbabilities());
			}
			System.out.println(s);
		}
	}

	public CodeUniverse getCodeUniverse() {
		return cU;
	}

	public ArrayList<PegPossibility> getPegPossibilities() {
		return ps;
	}

}

//DEPRECATED OLD HUMANISH BUGGY AI	
//	private ArrayList<PegPossibility> ps = new ArrayList<>();
//	private int len;
//	private int maxGroupSize;
//	private ArrayList<Peg> opts;
//
//	
//	public AI(GameSettings settings) {
//		len = settings.getCodeLength();
//		
//		//by default we'll check all possible groups of turns up to the code length
//		maxGroupSize=len;
//		
//		opts = settings.getPegOptions();
//		
//		//initialize the peg-possibilities array
//		Iterator<Peg> it = opts.iterator();
//		while (it.hasNext()) {
//			ps.add(new PegPossibility(it.next(), len));
//		}
//	}
//	
//	//main analysis loop, returns the analysis as list of peg possiblities, one for each peg option
//	public ArrayList<PegPossibility> analyze(ArrayList<Turn> turns) {
//		PegPossibility.setNeedsUpdate();
//		while (PegPossibility.needsUpdate()) {
//			PegPossibility.resetUpdatedFlag();
//			// n is the size of the group, only 2 for now
//			//bifurcate!
//			for(int i=0;i<turns.size();i++) {
//				for(int j=0;j<turns.size();j++) {
//					Turn[] ts = {turns.get(i),turns.get(j)};
//					if (i<j && areGroupable(ts)) 
//						analyzeGroupableTurns(ts);
//				}	
//			}
//			//analyze individual turns
//			for (Turn t : turns) {
//				analyzeTurnMinimums(t);
//				analyzeTurnMaximums(t);
//				findExcludedSlots(t);
//				findLocatedPegs(t);
//			}
//		}
//			
//		return ps;
//	}
//	
//	public boolean areGroupable(Turn[] turns) {
//		//if these two (or possibly more)have no pegs
//		//occurring in multiple turns, the turns are said to be groupable. Any identified pegs should be ignored,
//		//with each identified peg decreasing the length and the response pins effectively by one 
//		//for each turn in which the identified peg is present. Excluded pegs need not be present at all.
//
//		for (PegPossibility p : ps) {
//			// pegs for which we know the number for certain do not need checking, are effectively ignorable
//			if (!p.numIsCertain()){
//				int n=0;
//				for (Turn t : turns) {
//					if(t.numGuessed(p.getPeg()) > p.getNumIdentified()) n++;
//				}
//				// turns are not groupable if more than one turn has more of a peg than is known, 
//				//CHANGED REMOVE:or if no turns
//				//have more of a peg than is known.END CHANGE
//				if (n > 1) return false; //changed from  (n != 1)
//			}
//		}
//		return true;
//	}
//	
//	public void analyzeGroupableTurns(Turn[] turns) {
//		int totPins=0; 
//		int numIDtheseTurns=0;
//		for (Turn t:turns) {
//			totPins += t.getResponse().getNumPins();
//			numIDtheseTurns += numPegsIdentified(t);
//		}
//		
//		//defecit pins correspond to the number of "missing" pins in a group of turns, minus the number of
//		//those pins that have been accounted for so far
//		int defecitPins = len - totalPegsIdentified() - (totPins - numIDtheseTurns);
//		//System.out.println("Defecit pins: "+defecitPins);
//		if (defecitPins==0) { //this means that each correct peg is present in the turns, so no pegs are present
//								//more often than they appear (disregarding pins/pins already accounted for)
//			for (Turn t : turns) {
//				for (PegPossibility p : ps) {
//					int guessed = t.numGuessed(p.getPeg());
//					if (guessed > 0) { //only check pegs present in this turn
//						
//						//maxNum is the smaller of the number of times the peg was guessed this turn; 
//						//and the difference between the number of pins 
//						//and the number of pins accounted for by other pegs
//						int diff = Math.max(0,t.getResponse().getNumPins()
//								-numOtherPegsIdentified(t,p.getPeg()));
//						int max = Math.min(guessed, diff);
//						
//					//only update maxnum if it decreases 
//					if (max < p.getMaxNum())
//						p.setMaxNum(max);
//					}
//				}
//			}
//		}
//		else if (defecitPins>0) { //then a pin is used somewhere more often than it is present in the turns.
//			for (Turn t : turns) {
//				for (PegPossibility p : ps) {
//					int guessed = t.numGuessed(p.getPeg());
//					if (guessed > 0) { //only check pegs present in this turn
//						//if number unaccounted pins is smaller than number of unaccounted times peg was tried
//						// then the maxnum is the number of pins minus other pegs accounted for,
//						//but no lower than zero
//						if(t.getResponse().getNumPins()-numPegsIdentified(t) < guessed - p.getNumIdentified()){ 
//							int max = Math.max(
//									0,t.getResponse().getNumPins() - numOtherPegsIdentified(t,p.getPeg())
//									);
//							if (max < p.getMaxNum())
//								p.setMaxNum(max);
//						}
//						else {
//							//if number unaccounted pins is at least as large as the number of 
//							//unaccounted times the peg was tried, the maxNumber of a pin is the number
//							//of pins that COULD come from the colors in this turn, minus the pins that
//							//aren't necessarily our peg, minus any deficit pins accounted for 
//							//(poor wording, see notes)
//							int deficitPinsAccountedFor=0;
//							for (PegPossibility p2: ps) {
//								int numTriedinGroup = 0;
//								for (Turn t2: turns) numTriedinGroup += t2.numGuessed(p2.getPeg());
//								deficitPinsAccountedFor += Math.max(0, p2.getNumIdentified()-numTriedinGroup);	
//							}
//							int otherGuessPins=0;
//							for(Turn t2 : turns) {
//								if (t2 != t)
//									otherGuessPins += t2.getResponse().getNumPins();
//							}
//							int max = Math.max(0,
//									len- otherGuessPins - 
//									(t.getResponse().getNumPins()-t.numGuessed(p.getPeg()))
//									- deficitPinsAccountedFor);
//							if (max < p.getMaxNum())
//								p.setMaxNum(max);
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	/*When two turn are identical except for a one-peg change:*/
//	public boolean areSimilar(Turn[] turns) {
//		//TODO
//		return false;
//	}
//
//		/*If one pin is lost -> the old peg increases numMin (exists) and the new peg reduces maxNum 
//		to other appearances of the peg in the turn (doesn't exist).
//		If that pin was black, the old peg is located.
//
//		If a black changes to a white pin, the old peg exists and is located (increase numMin) 
//		and the new peg exists also. (increase numMin)
//			*/
//	public void analyzeSimilarTurns(Turn [] turns) {
//		//TODO
//	}
//	
//	//If number of pins for a turn is bigger than the maximum number of other pegs that COULD be pins, 
//	//then a number of the peg is identified equal to the pins minus this max number.
//	public void analyzeTurnMinimums(Turn turn) {
//		
//		for (PegPossibility pp : ps) {
//			if (turn.numGuessed(pp.getPeg()) > 0) { //only check pegs guessed this turn
//				int maxOther=0;
//				//This max number is equal to the sum over the lower of (maxNum or numTried) 
//				//over every OTHER unique peg in the turn
//				for (PegPossibility p2 : ps) {
//					if (p2 != pp) maxOther += Math.min(p2.getMaxNum(), turn.numGuessed(p2.getPeg()));
//				}
//				int min = turn.getResponse().getNumPins() - maxOther;
//				//only update minNum if it will increase.
//				if (min > pp.getNumIdentified()) {
//					pp.setMinNum(min);
//
//				}
//			}
//				
//		}
//	}
//	
//	//If  the number of pins in a turn minus the number of pins accounted for by other pegs
//	//is less than the number guessed,
//	//then that modified pin count is the new maxNum
//	public void analyzeTurnMaximums(Turn turn) {
//		for (PegPossibility pp : ps) {
//			if (turn.numGuessed(pp.getPeg()) > 0) { //only check pegs guessed this turn
//				
//				int unaccountedPins = turn.getResponse().getNumPins() - numOtherPegsIdentified(turn, pp.getPeg());
//				
//				//the modified pin count should also be smaller than the current maxNum if we are to update maxNum.
//				if ( unaccountedPins < Math.min(turn.numGuessed(pp.getPeg()),pp.getMaxNum())) { 
//					pp.setMaxNum(unaccountedPins);
//					if (unaccountedPins<0) {
//						System.out.print("error "+pp.getPeg()+" max: "+unaccountedPins+" Min: "+pp.getNumIdentified());
//						System.out.println(" Pins: "+turn.getResponse().getNumPins()+
//							" numOtherPegsIdentified: "+numOtherPegsIdentified(turn, pp.getPeg()));
//					}
//				}
//			}
//		}
//	}
//	
//	public void findExcludedSlots(Turn turn) {
//		// If all pins are white, or if all black pins correspond to located pegs, all other pegs
//		//can be excluded from the slot in which they were guessed. (maxNum is always checked and reduced to 
//		//the number of non-excluded slots if necessary.)
//		int numLocated = numPegsLocated(turn);
//
//		if (turn.getResponse().getNumBlack() == numLocated) { //all black pins are accounted for
//			for (int i=0; i < len ; i++) {
//				for (PegPossibility pp : ps) {
//					//if an unlocated peg was guessed in a slot, we now know it's not there.
//					//we shouldn't update the slot if it is already excluded.
//					if (turn.getGuess().getPeg(i) == pp.getPeg() && !pp.isLocatedAt(i) && !pp.isExcludedAt(i)) {
//						pp.excludeSlot(i); 
//					
//					}
//				}
//			}
//		}
//	}
//	
//	//if all pins are black, or if all white pins correspond to pegs known to exist and be in excluded slots,
//	//then all identified pegs can be located as long as the number identified is at least as big as the number tried
//	public void findLocatedPegs(Turn turn) {
//		int numExcl = numIdentifiedinExcludedSlots(turn);
//		//don't bother if there are no black pins
//		if (turn.getResponse().getNumBlack()>0 && turn.getResponse().getNumWhite()==numExcl) {
//			for (int i=0; i < len ; i++) {
//				for (PegPossibility pp : ps) {
//					//also make sure not to update an already located slot
//					if (turn.getGuess().getPeg(i) == pp.getPeg() && !pp.isLocatedAt(i)
//							&& !pp.isExcludedAt(i) && pp.getNumIdentified()>=turn.numGuessed(pp.getPeg())) {
//						pp.setLocatedSlot(i); 
//						
//					}
//				}
//			}
//		}
//	}
//	
//	//returns the number of pegs known to exist guessed in a turn, not counting the passed peg
//	public int numOtherPegsIdentified(Turn turn, Peg peg) {
//		int n=0;
//		for (PegPossibility p : ps) {
//			if (peg != p.getPeg()) {
//				n += Math.min(p.getNumIdentified(), turn.numGuessed(p.getPeg()));	
//			}
//		}	
//		return n;
//	}
//	//returns number of pegs guessed in a turn that are known to exist
//	public int numPegsIdentified(Turn turn) {
//		int n=0;
//		for (PegPossibility p : ps) {
//			n += Math.min(p.getNumIdentified(), turn.numGuessed(p.getPeg()));			
//		}
//		return n;
//	}
//	// returns number of pegs known to be at the correct location in a turn
//	public int numPegsLocated(Turn turn) {
//		int numLocated = 0;
//		for (int i=0; i < len ; i++) {
//			Peg peg = turn.getGuess().getPeg(i);
//			int numLocPeg=0;
//			for (PegPossibility pp : ps) {
//				if (peg == pp.getPeg() && pp.isLocatedAt(i)) numLocPeg++; //overcounts....
//			}
//			numLocated += numLocPeg / turn.numGuessed(peg); //fixes overcount
//		}
//		return numLocated;
//	}
//	//returns the number of identified pegs known to be in an excluded slot in a turn, 
//	//(and which correspond therefore to known white pins)
//	public int numIdentifiedinExcludedSlots(Turn turn) {
//		int n=0;
//		for (PegPossibility pp : ps) {
//			int excl=0;
//			for (int i=0; i < len ; i++) {
//				Peg peg = turn.getGuess().getPeg(i);
//				if (peg == pp.getPeg() && pp.isExcludedAt(i)) excl++; 
//
//			}
//			//it's the smaller of the number identified and the number tried in known excluded slots
//			//that certainly correspond to white pins
//			n += Math.min(pp.getNumIdentified(), excl);  
//		}
//		return n;
//	}
//	
//	// returns the total number of ID'ed pegs
//	public int totalPegsIdentified() {
//		int n=0;
//		for (PegPossibility p : ps) n += p.getNumIdentified();
//		return n;
//	}
//	public String toString() {
//		String s = new String();
//		for (PegPossibility p:ps) {
//		s+=p.toString();
//		}
//		return s;
//	}
//}
