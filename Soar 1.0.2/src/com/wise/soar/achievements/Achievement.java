package com.wise.soar.achievements;

import com.wise.soar.entity.Player;
import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public abstract class Achievement {
	// I want to have what functionalities?
	// - description of the achievement
	// - what it unlocks
	// - what i need to do to get it
	// - these achievements tick to implement in-game HUD

	protected Level level;
	protected Player player;

	protected boolean received;

	public Achievement(Level level) {
		this.level = level;
		this.player = level.getPlayer();
	}

	public void init() {
		received = isReceived();
		this.player = level.getPlayer();
	}

	public abstract boolean isReceived();

	public abstract String getDescription();

	public abstract String getUnlock();

	public abstract String getInGameMessage();

	public final void setAchievementReceived(boolean b) {
		received = b;
		setAchievement(b);
	}

	public abstract void setAchievement(boolean b);

	/*
	 * getCondition() returns the actual boolean as to if the player got the achievement.
	 */
	public abstract boolean getCondition();

	public void tick() {
		if (received)
			return;

		if (getCondition()) {
			setAchievementReceived(true);
			createAchievementNotifier(createInGameMessage(getInGameMessage()));
		}
	}

	protected void createAchievementNotifier(final String[] message) {
		level.createAchievementNotifier(message);
	}

	protected String[] createInGameMessage(String message) {
		return Resource.divideString(message, 35.0);
	}

	public boolean isComplete() {
		return received;
	}
}