package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class HighScore extends Achievement {
	private boolean gotHighScore;

	public HighScore(Level level) {
		super(level);
	}

	public String getDescription() {
		return "";
	}

	public String getUnlock() {
		return "";
	}

	public String getInGameMessage() {
		return "New High Score! Keep Going!";
	}

	public void setAchievement(boolean b) {
		gotHighScore = true;
	}

	public boolean getCondition() {
		return level.getScore() > Saves.HIGH_SCORE && !gotHighScore && Saves.DEATHS != 0 && !level.isGameOver();
	}

	public boolean gotHighScore() {
		return gotHighScore;
	}

	public boolean isReceived() {
		return false;
	}
}
