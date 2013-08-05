package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class NoPowerUps extends Achievement {
	public NoPowerUps(Level level) {
		super(level);
	}

	public String getDescription() {
		return "Get a score of 15000 without getting a single power up.";
	}

	public String getUnlock() {
		return "Unlocks an Extra Life!";
	}

	public String getInGameMessage() {
		return "Collected no power-ups after a score of 15000!";
	}

	public void setAchievement(boolean b) {
		Saves.ACHIEVEMENT_NO_POWER_UPS = true;
	}

	public boolean getCondition() {
		return level.getScore() >= 15000 && !player.hasCollectedPower() && !Saves.ACHIEVEMENT_NO_POWER_UPS;
	}

	public boolean isReceived() {
		return Saves.ACHIEVEMENT_NO_POWER_UPS;
	}
}
