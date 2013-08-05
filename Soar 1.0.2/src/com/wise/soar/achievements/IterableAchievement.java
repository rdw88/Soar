package com.wise.soar.achievements;

import com.wise.soar.level.Level;

public abstract class IterableAchievement extends Achievement {
	protected int[] milestones;
	protected int stage;
	protected boolean finished;

	public IterableAchievement(Level level) {
		super(level);
	}

	public void init() {
		initStage();
		received = isReceived();
	}

	public boolean isReceived() {
		return finished;
	}

	protected abstract void initStage();

	protected abstract String[] getDescriptions();

	protected abstract String[] getUnlocks();

	protected abstract String[] getInGameMessages();

	protected abstract boolean[] getConditions();

	protected abstract void doAchievement();

	public void tick() {
		if (finished)
			return;

		if (getCondition()) {
			createAchievementNotifier(createInGameMessage(getInGameMessage()));
			setAchievementReceived(true);
		}
	}

	public void setAchievement(boolean b) {
		doAchievement();

		if (stage == getDescriptions().length - 1)
			finished = true;
		else
			stage++;
	}

	public String getDescription() {
		return getDescriptions()[stage];
	}

	public String getUnlock() {
		if (stage > getUnlocks().length - 1)
			return getUnlocks()[getUnlocks().length - 1];

		return getUnlocks()[stage];
	}

	public String getInGameMessage() {
		return getInGameMessages()[stage];
	}

	public boolean getCondition() {
		return getConditions()[stage];
	}

	public int getMilestone() {
		return milestones[stage];
	}

	public boolean isComplete() {
		return finished;
	}

	public int getStage() {
		return stage;
	}
}
