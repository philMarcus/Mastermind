package ai;

import baseGame.GameSettings;

public class BatchTester {

	public static void main(String[] args) {
		final int GAMES = 1000;
		GameSettings settings = new GameSettings();
		//settings.setCodeLength(5);
		AIBatchGames batch = new AIBatchGames(settings);
		for (int i = 0; i < GAMES; i++) {
			AnalyzedGame g = batch.runGame();
			System.out.println(batch);
		}

	}

}
