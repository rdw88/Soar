package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.achievements.Achievement;
import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;
import com.wise.soar.res.Resource;

public class AchievementMenu extends SubMenu {
	private int games = Saves.DEATHS;
	private int powers = Saves.POWER_UPS;

	private Level level;
	private Button next, previous;

	private int page;
	private final int numPages = 2;

	public AchievementMenu(Menu menu, Level level) {
		super(menu, level);
		this.level = level;
		refresh();
	}

	protected void struct() {
		next = new Button(500, Game.getDisplayHeight() - 150, "");
		previous = new Button(200, Game.getDisplayHeight() - 150, "");

		next.setType(Button.TYPE_NEXT);
		previous.setType(Button.TYPE_PREVIOUS);

		next.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (page == numPages - 1)
					return;

				page++;
			}
		});

		previous.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (page == 0)
					return;

				page--;
			}
		});

		add(next);
		add(previous);
	}

	public void render(Canvas canvas) {
		if (!isOpen())
			return;

		super.render(canvas);

		Game.renderText(canvas, "Achievements", Game.getDisplayWidth() / 2 - 260, 180, 0xff3434ef, 92);
		Game.renderText(canvas, "Page: " + (page + 1) + "/" + numPages, Game.getDisplayWidth() / 2 - 60, Game.getDisplayHeight() - 135, 0xffededed, 32);

		if (page == 0)
			page1(canvas);
		else
			page2(canvas);
	}

	private void page1(Canvas canvas) {
		int size = 42;

		if (level.playCount.isComplete()) {
			Game.renderText(canvas, level.playCount.getDescription(), 25, 300, 0xffdddddd, size);
			Game.renderText(canvas, getBooleanText(true), Game.getDisplayWidth() - 260, 300, getBooleanColor(true), size - 10);
		} else {
			Game.renderText(canvas, level.playCount.getDescription(), 25, 300, 0xffdddddd, size);
			Game.renderText(canvas, games + "/" + level.playCount.getMilestone(), Game.getDisplayWidth() - 260, 300, 0xffffffff, size);
		}

		Game.renderText(canvas, level.playCount.getUnlock(), 25, 340, 0xff004A83, size - 10);

		if (level.powerUpCount.isComplete()) {
			Game.renderText(canvas, level.powerUpCount.getDescription(), 25, 400, 0xffdddddd, size);
			Game.renderText(canvas, getBooleanText(true), Game.getDisplayWidth() - 260, 400, getBooleanColor(true), size - 10);
		} else {
			Game.renderText(canvas, level.powerUpCount.getDescription(), 25, 400, 0xffdddddd, size);
			Game.renderText(canvas, powers + "/" + level.powerUpCount.getMilestone(), Game.getDisplayWidth() - 260, 400, 0xffffffff, size);
		}

		Game.renderText(canvas, level.powerUpCount.getUnlock(), 25, 440, 0xff004A83, size - 10);

		renderDescription(level.scoreMilestones, 500, size, canvas);

		renderDescription(level.fivePowerDowns, 600, size, canvas);

		renderDescription(level.noPowerUps, 750, size, canvas);
	}

	private void page2(Canvas canvas) {
		int size = 42;

		renderDescription(level.everyPowerUp, 300, size, canvas);

		renderDescription(level.allComplete, 500, size, canvas);
	}

	private void renderDescription(Achievement a, int startY, int size, Canvas canvas) {
		String s = a.getDescription();
		double charsPerLine = 20.0;
		String[] split = Resource.divideString(s, charsPerLine);
		int lines = split.length;

		for (int i = 0; i < lines; i++)
			Game.renderText(canvas, split[i], 25, startY + (i * 50), 0xffdddddd, size);

		int end = startY + (lines * 50);
		Game.renderText(canvas, getBooleanText(a.isComplete()), Game.getDisplayWidth() - 260, ((end - startY) / 2) + startY, getBooleanColor(a.isComplete()), size - 10);
		Game.renderText(canvas, a.getUnlock(), 25, end - 10, 0xff004A83, size - 10);
	}

	private void refresh() {
		games = Saves.DEATHS;
		powers = Saves.POWER_UPS;
	}

	private String getBooleanText(boolean b) {
		return b ? "COMPLETE!" : "INCOMPLETE";
	}

	private int getBooleanColor(boolean b) {
		return b ? 0xff00ff00 : 0xffff0000;
	}

	public void open() {
		refresh();
		super.open();
	}

}