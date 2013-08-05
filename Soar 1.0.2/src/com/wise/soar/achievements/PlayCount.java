package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class PlayCount extends IterableAchievement {
	private final int[] gameFlags = { 1, 5, 10, 25, 50 };

	private int lastGameAchievement = -1;

	public PlayCount(Level level) {
		super(level);
		milestones = gameFlags;
	}

	protected void initStage() {
		int games = Saves.DEATHS;

		for (int i = 0; i < gameFlags.length; i++) {
			if (games >= gameFlags[i]) {
				if (i == gameFlags.length - 1) {
					finished = true;
					stage = i;
				} else {
					stage = i + 1;
				}
			}
		}
	}

	protected String[] getDescriptions() {
		String[] strings = new String[gameFlags.length];

		for (int i = 0; i < strings.length; i++) {
			if (i == 0) {
				strings[i] = "Play a Game!";
				continue;
			}

			strings[i] = "Play " + gameFlags[i] + " Games!";
		}

		return strings;
	}

	protected String[] getUnlocks() {
		return new String[] { "Unlocks a new Balloon Color!" };
	}

	protected String[] getInGameMessages() {
		String[] strings = new String[gameFlags.length];

		for (int i = 0; i < strings.length; i++) {
			if (i == 0) {
				strings[i] = "Played a game!";
				continue;
			}

			strings[i] = "Played " + gameFlags[i] + " Games!";
		}

		return strings;
	}

	protected boolean[] getConditions() {
		boolean[] bools = new boolean[gameFlags.length];

		for (int i = 0; i < bools.length; i++)
			bools[i] = Saves.DEATHS == gameFlags[i] && lastGameAchievement != i && level.isGameOver();

		return bools;
	}

	protected void doAchievement() {
		lastGameAchievement = stage;
	}
}
