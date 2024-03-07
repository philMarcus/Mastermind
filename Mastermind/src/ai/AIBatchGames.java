package ai;

import baseGame.Code;
import baseGame.GameSettings;

//this class represents a large number of AI-played games of Mastermind,
//along with statistics tracking the number of games won in each number of turns
//and mean and median turns to win 
public class AIBatchGames {

	private long numGames; //number of games in the batch
	
	//tracks number of games won in X tries.
	//so gamesWonIn[5] would be how many games were won in exactly 5 tries
	private long[] gamesWonIn;
	
	private GameSettings settings;
	
	//the AI personality that makes a guess based on the analyzed game state
	private AIPersonality ai;
	
	//construct a new batch of games with given settings
	public AIBatchGames(GameSettings settings) {
		this.settings = settings;
		
		//initialize the array of long integers that tracks number of games won
		//in each number of turns.
		//
		//the "+1" makes the index more meaningful, even though the [0] element
		//will always be empty. Index directly corresponds to the turns taken to win.
		gamesWonIn = new long[settings.getMaxTries()+1];
	
	}
	
	//run a single game
	public AnalyzedGame runGame() {
		AnalyzedGame g = new AnalyzedGame(settings);
		ai = new TheProfessorAI(g);	//use an AIPersonality implementation to guess a code
		
		numGames++;
		//check setting for the max number of turns in a game (10 is standard)
		for(int i=0;i<settings.getMaxTries();i++) {
			Code choice = ai.getChoice();
			//take the turn and check for victory
			if(g.takeTurn(choice).isVictory()){
				//increment relevant element of gamesWonIn array
				gamesWonIn[g.getTurnsTaken()]++;
				return g;
			}
		}
		//the game is lost, return without adding anything to gamesWonIn
		return g;
	}
	
	//runs the given number of games
	public void runGames(int numGames) {
		for(int i=0;i<numGames;i++)
			runGame();
	}
	
	//returns total number of wins by summing the elements of the gamesWonIn array
	public long numWins() {
		long w=0;
		for(int i=0;i<=settings.getMaxTries();i++)
			w += gamesWonIn[i];
		return w;
		
	}
	
	//returns total number of games lost in the batch
	public long numLost() {
		return numGames - numWins();
	}

	public long getNumGames() {
		return numGames;
	}

	//returns the mean number of turns taken to win a game in this batch
	public double getMeanTries() {
		long sum=0;
		//add number of turns to win*number of games to get total tries
		for(int i=0;i<=settings.getMaxTries();i++)
			
			sum += i*gamesWonIn[i];
		//divide total tries by number of games played
		return (double)sum / numGames;
		
	}
	
	//return the median number of turns taken to win a game in this batch
	public int getMedianTries() {
		long c=0;
		//loop over and index representing the number of turns to win
		for(int i=0;i<=settings.getMaxTries();i++) {
			//add number of games won in i turns to the count
			c += gamesWonIn[i];
			//if count is now more than half of total games, we've passed the median, i.
			//">=" means: if the median would be in between two bins, the lower is taken.
			if (c >= (numWins()/2.0))
				return i;
		}
		//the loop should never complete, 0 indicates bug
		return 0;
		
	}
	
	//store the stats as a string. Displayed in the GUI BatchDialog.
	public String toString() {
		String s = "Games Won In: \n";
		for(int i=1;i<=settings.getMaxTries();i++)
			s +=i+" turns: "+ gamesWonIn[i]+"\n";
		s += "Won "+numWins()+" out of "+ numGames+ " games. \n";
		s += "Mean attempts to win: "+getMeanTries()+"\n";
		s += "Median attempts to win: "+getMedianTries()+"\n";
		return s;
	}
}

