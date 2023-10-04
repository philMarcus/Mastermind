package ai;

import baseGame.GameSettings;

public class AIBatchGames {

	private int numGames;
	//tracks number of games won in X tries.
	//so gamesWonIn[5] would be how many games were won in exaclty 5 tries
	private int[] gamesWonIn;
	
	private GameSettings settings;
	private AIPersonality ai;
	
	public AIBatchGames(GameSettings settings) {
		this.settings = settings;
		ai = new TheProfessorAI();
		
	}
	
	public AnalyzedGame runGame() {
		AnalyzedGame g = new AnalyzedGame(settings);
		
		return g;
	}
	
	public void runGames(int numGames) {
		for(int i=0;i<numGames;i++)
			runGame();
	}
	
	public int numWins() {
		
	}
	
	public int numLost() {
		
	}

	public int getNumGames() {
		return numGames;
	}

	public int getMeanTries() {
		
	}
	
	public int getMedianTries() {
		
	}
}

