package com.wise.soar.level;

import java.io.File;

import com.wise.soar.Game;
import com.wise.soar.res.Resource;

public class Saves {
	public static int HIGH_SCORE;
	public static long TIME_PLAYED;
	public static long LONGEST_RUN;
	public static int POWER_UPS;
	public static int DEATHS;

	public static boolean ACHIEVEMENT_NO_POWER_UPS;
	public static boolean ACHIEVEMENT_FIVE_POWER_DOWNS;
	public static boolean ACHIEVEMENT_EVERY_POWER_UP;

	private static boolean SOUND = true;

	private static final int NUM_SAVES = 9;

	public static void init() {
		File file = new File(Game.directory, "saves.txt");
		String[] values = Resource.readFromFile(file);

		if (values.length != NUM_SAVES) {
			Resource.writeToFile(file, new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "1" });
			values = Resource.readFromFile(file);
		}

		HIGH_SCORE = Integer.parseInt(values[0]);
		LONGEST_RUN = Long.parseLong(values[1]);
		TIME_PLAYED = Long.parseLong(values[2]);
		POWER_UPS = Integer.parseInt(values[3]);
		DEATHS = Integer.parseInt(values[4]);

		if (values[5].equals("1")) {
			ACHIEVEMENT_NO_POWER_UPS = true;
		} else {
			ACHIEVEMENT_NO_POWER_UPS = false;
		}

		if (values[6].equals("1")) {
			ACHIEVEMENT_FIVE_POWER_DOWNS = true;
		} else {
			ACHIEVEMENT_FIVE_POWER_DOWNS = false;
		}

		if (values[7].equals("1")) {
			ACHIEVEMENT_EVERY_POWER_UP = true;
		} else {
			ACHIEVEMENT_EVERY_POWER_UP = false;
		}

		if (values[8].equals("1")) {
			SOUND = true;
		} else {
			SOUND = false;
		}
	}

	public static void setHighScore(int score) {
		HIGH_SCORE = score;
	}

	public static void setTimePlayed(long time) {
		TIME_PLAYED += time;
	}

	public static void setLongestRun(long time) {
		LONGEST_RUN = time;
	}

	public static void setPowerUpCount(int count) {
		POWER_UPS += count;
	}

	public static void setDeaths(int increment) {
		DEATHS += increment;
	}

	public static void setSound(boolean b) {
		SOUND = b;
		writeCurrentStats();
	}

	public static boolean getSound() {
		return SOUND;
	}

	public static void writeCurrentStats() {
		File file = new File(Game.directory, "saves.txt");

		String[] parse = new String[NUM_SAVES];
		parse[0] = Integer.toString(HIGH_SCORE);
		parse[1] = Long.toString(LONGEST_RUN);
		parse[2] = Long.toString(TIME_PLAYED);
		parse[3] = Integer.toString(POWER_UPS);
		parse[4] = Integer.toString(DEATHS);

		if (ACHIEVEMENT_NO_POWER_UPS) {
			parse[5] = "1";
		} else {
			parse[5] = "0";
		}

		if (ACHIEVEMENT_FIVE_POWER_DOWNS) {
			parse[6] = "1";
		} else {
			parse[6] = "0";
		}

		if (ACHIEVEMENT_EVERY_POWER_UP) {
			parse[7] = "1";
		} else {
			parse[7] = "0";
		}

		if (SOUND) {
			parse[8] = "1";
		} else {
			parse[8] = "0";
		}

		Resource.writeToFile(file, parse);
	}
}