package com.wise.soar.achievements;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class PowerUpCount extends IterableAchievement {
	private final int[] powerFlags = { 10, 25, 50, 100, 200 };

	public PowerUpCount(Level level) {
		super(level);
		milestones = powerFlags;
	}

	protected void initStage() {
		int games = Saves.POWER_UPS;

		for (int i = 0; i < powerFlags.length; i++) {
			if (games >= powerFlags[i]) {
				if (i == powerFlags.length - 1) {
					finished = true;
					stage = i;
				} else {
					stage = i + 1;
				}
			}
		}
	}

	protected String[] getDescriptions() {
		String[] strings = new String[powerFlags.length];

		for (int i = 0; i < strings.length; i++) {
			strings[i] = "Collect " + powerFlags[i] + " Power-Ups";
		}

		return strings;
	}

	protected String[] getUnlocks() {
		return new String[] { "Unlocks a new Balloon Color!", "Unlocks a new Balloon Color!", "Unlocks a new Balloon Color!", "Unlocks a new Balloon Color!", "Unlocks Pulse!" };
	}

	protected String[] getInGameMessages() {
		String[] strings = new String[powerFlags.length];

		for (int i = 0; i < strings.length; i++) {
			strings[i] = "Collected " + powerFlags[i] + " Power-Ups!";
		}

		return strings;
	}

	protected boolean[] getConditions() {
		boolean[] bools = new boolean[powerFlags.length];

		for (int i = 0; i < bools.length; i++)
			bools[i] = Saves.POWER_UPS + level.getPowerCount() == powerFlags[i] && !received && level.getPowerCount() != 0 && !level.isGameOver();

		return bools;
	}

	protected void doAchievement() {
	}
}
