package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class EveryPowerUp extends Achievement {
	public EveryPowerUp(Level level) {
		super(level);
	}

	public String getDescription() {
		return "Collect every Power-Up and reach a score of 25000.";
	}

	public String getUnlock() {
		return "Unlocks Magnet!";
	}

	public String getInGameMessage() {
		return "Collected every Power-Up and reached a score of 25000!";
	}

	public void setAchievement(boolean b) {
		Saves.ACHIEVEMENT_EVERY_POWER_UP = true;
	}

	public boolean getCondition() {
		return level.getPowerUpsCreated() == level.getPowerCount() && level.getScore() >= 25000;
	}

	public boolean isReceived() {
		return Saves.ACHIEVEMENT_EVERY_POWER_UP;
	}
}
