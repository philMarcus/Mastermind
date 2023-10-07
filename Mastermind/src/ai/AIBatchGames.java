package ai;

import baseGame.Code;
import baseGame.GameSettings;

public class AIBatchGames {

	private long numGames;
	//tracks number of games won in X tries.
	//so gamesWonIn[5] would be how many games were won in exactly 5 tries
	private long[] gamesWonIn;
	
	private GameSettings settings;
	//the AI personality makes the choice based on the analyzed game state
	private AIPersonality ai;
	
	public AIBatchGames(GameSettings settings) {
		this.settings = settings;
		
		//the "+1" makes the index more meaningful, even though the [0] element
		//will always be empty. Index directly corresponds to the turns taken to win.
		gamesWonIn = new long[settings.getMaxTries()+1];
	
	}
	
	public AnalyzedGame runGame() {
		AnalyzedGame g = new AnalyzedGame(settings);
		ai = new TheProfessorAI(g);	
		
		numGames++;
		for(int i=0;i<settings.getMaxTries();i++) {
			Code choice = ai.getChoice();
			//take the turn and check for victory
			if(g.takeTurn(choice).isVictory()){
				gamesWonIn[g.getTurnsTaken()]++;
				return g;
			}
		}
		//the game is lost, return without adding anything to gamesWonIn
		return g;
	}
	
	public void runGames(int numGames) {
		for(int i=0;i<numGames;i++)
			runGame();
	}
	
	public long numWins() {
		long w=0;
		for(int i=0;i<=settings.getMaxTries();i++)
			w += gamesWonIn[i];
		return w;
		
	}
	
	public long numLost() {
		return numGames - numWins();
	}

	public long getNumGames() {
		return numGames;
	}

	public double getMeanTries() {
		long sum=0;
		//add number of turns to win*number of games to get total tries
		for(int i=0;i<=settings.getMaxTries();i++)
			
			sum += i*gamesWonIn[i];
		//divide total tries by number of games played
		return (double)sum / numGames;
		
	}
	
	public int getMedianTries() {
		long c=0;
		for(int i=0;i<=settings.getMaxTries();i++) {
			c += gamesWonIn[i];
			//if count is now more than half of total games, we've passed the median, i.
			//">=" means: if the median would be in between two bins, the lower is taken.
			if (c >= (numWins()/2.0))
				return i;
		}
		//the loop should never complete, 0 indicates error
		return 0;
		
	}
	
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

