package com.wise.soar.achievements;

import com.wise.soar.level.Level;

public class AllComplete extends Achievement {
	public AllComplete(Level level) {
		super(level);
	}

	public String getDescription() {
		return "Complete all achievements";
	}

	public String getUnlock() {
		return "Able to use all abilities at once!";
	}

	public String getInGameMessage() {
		return "All Achievements have been completed!";
	}

	public void setAchievement(boolean b) {
	}

	public boolean getCondition() {
		boolean bool = true;

		for (int i = 0; i < level.getAchievements().size(); i++) {
			Achievement a = level.getAchievements().get(i);

			if (a.equals(this) || a instanceof HighScore)
				continue;

			if (!a.received) {
				bool = false;
				break;
			}
		}

		return bool;
	}

	public boolean isReceived() {
		return getCondition();
	}
}
