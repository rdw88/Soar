package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class ScoreMilestones extends IterableAchievement {
	private final int[] scoreFlags = { 1000, 5000, 10000, 20000, 30000, 40000, 50000, 100000 };

	private int lastScoreAchievement = -1;

	public ScoreMilestones(Level level) {
		super(level);
		milestones = scoreFlags;
	}

	protected void initStage() {
		int games = Saves.HIGH_SCORE;

		for (int i = 0; i < scoreFlags.length; i++) {
			if (games >= scoreFlags[i]) {
				if (i == scoreFlags.length - 1) {
					finished = true;
					stage = i;
				} else {
					stage = i + 1;
				}
			}
		}
	}

	protected String[] getDescriptions() {
		String[] strings = new String[scoreFlags.length];

		for (int i = 0; i < strings.length; i++) {
			strings[i] = "Reach a score of " + scoreFlags[i] + "!";
		}

		return strings;
	}

	protected String[] getUnlocks() {
		return new String[] { "" };
	}

	protected String[] getInGameMessages() {
		String[] strings = new String[scoreFlags.length];

		for (int i = 0; i < strings.length; i++) {
			strings[i] = "Reached a score of " + scoreFlags[i] + "!";
		}

		return strings;
	}

	protected boolean[] getConditions() {
		boolean[] bools = new boolean[scoreFlags.length];

		for (int i = 0; i < bools.length; i++)
			bools[i] = level.getScore() >= scoreFlags[i] && level.getScore() < scoreFlags[i] + 50 && lastScoreAchievement != i;

		return bools;
	}

	protected void doAchievement() {
		lastScoreAchievement = stage;
	}
}
