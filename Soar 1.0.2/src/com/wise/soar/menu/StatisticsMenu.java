package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;

public class StatisticsMenu extends SubMenu {
	private int highScore;
	private String longestTime;
	private String timePlayed;
	private int powerUpsCollected;
	private int deaths;

	private final String[] stats = { "High Score:", "Longest Run:", "Time Played:", "Power Ups Collected:", "Deaths:" };

	public StatisticsMenu(Menu parent, Level level) {
		super(parent, level);
		refresh();
	}

	protected void struct() {
	}

	public void render(Canvas canvas) {
		if (!isOpen())
			return;

		super.render(canvas);

		Game.renderText(canvas, "Statistics", Game.getDisplayWidth() / 2 - 165, 200, 0xff3434ef, 92);

		int size = 48;
		for (int i = 0; i < stats.length; i++)
			Game.renderText(canvas, stats[i], 60, 350 + (i * 80), 0xffaaffaa, size);

		Game.renderText(canvas, "" + highScore, Game.getDisplayWidth() - 240, 350, 0xffdddddd, size);
		Game.renderText(canvas, "" + longestTime, Game.getDisplayWidth() - 240, 430, 0xffdddddd, size);
		Game.renderText(canvas, "" + timePlayed, Game.getDisplayWidth() - 240, 510, 0xffdddddd, size);
		Game.renderText(canvas, "" + powerUpsCollected, Game.getDisplayWidth() - 240, 590, 0xffdddddd, size);
		Game.renderText(canvas, "" + deaths, Game.getDisplayWidth() - 240, 670, 0xffdddddd, size);
	}

	private void refresh() {
		highScore = Saves.HIGH_SCORE;

		{
			long longTime = Saves.LONGEST_RUN;
			int seconds = (int) longTime / 60;
			int minutes = 0;
			if (seconds > 59) {
				minutes = seconds / 60;
				seconds -= (minutes * 60);
			}

			if (seconds < 10)
				longestTime = "" + minutes + ":0" + seconds;
			else
				longestTime = "" + minutes + ":" + seconds;
		}

		{
			long time = Saves.TIME_PLAYED;
			int seconds = (int) time / 60;
			int minutes = 0;
			if (seconds > 59) {
				minutes = seconds / 60;
				seconds -= (minutes * 60);
			}

			int hours = 0;
			if (minutes > 59) {
				hours = minutes / 60;
				minutes -= (hours * 60);
			}

			String mins = ":";
			String secs = ":";

			if (minutes < 10)
				mins = ":0";

			if (seconds < 10)
				secs = ":0";

			if (hours > 0)
				timePlayed = "" + hours + mins + minutes + secs + seconds;
			else
				timePlayed = "" + minutes + secs + seconds;
		}

		powerUpsCollected = Saves.POWER_UPS;
		deaths = Saves.DEATHS;
	}

	public void open() {
		refresh();
		super.open();
	}
}