package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class FourPowerDowns extends Achievement {
	private boolean gotFifthPowerDown;

	public FourPowerDowns(Level level) {
		super(level);
	}

	public String getDescription() {
		return "Survive 4 Power-Downs in one life.";
	}

	public String getUnlock() {
		return "Unlocks Flash!";
	}

	public String getInGameMessage() {
		return "Unlocked new Ability for surviving 4 power-downs!";
	}

	public void setAchievement(boolean b) {
		Saves.ACHIEVEMENT_FIVE_POWER_DOWNS = true;
	}

	public void tick() {
		super.tick();

		if (player == null)
			return;

		if (player.getBadPowers() == 4 && !Saves.ACHIEVEMENT_FIVE_POWER_DOWNS) {
			gotFifthPowerDown = true;
		}
	}

	public boolean getCondition() {
		return gotFifthPowerDown && !level.isGameOver() && !Saves.ACHIEVEMENT_FIVE_POWER_DOWNS;
	}

	public boolean isReceived() {
		return Saves.ACHIEVEMENT_FIVE_POWER_DOWNS;
	}
}
