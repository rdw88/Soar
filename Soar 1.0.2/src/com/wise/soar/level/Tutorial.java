package com.wise.soar.level;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.RenderableObject;
import com.wise.soar.entity.Barrier;
import com.wise.soar.entity.LargeBall;
import com.wise.soar.entity.SmallBall;

public class Tutorial implements RenderableObject, Runnable {
	private boolean inTutorial;
	private Level level;

	private boolean flagWelcome, flagBalloon, flagBarrier, flagPowerUp, flagPowerDown, flagAchievements, flagEnd;

	private int fade = 0x001930ff;

	public Tutorial(Level level) {
		this.level = level;
	}

	public void start() {
		inTutorial = Saves.DEATHS == 0;

		if (inTutorial) {
			level.getPlayer().setShield(true);
			new Thread(this).start();
		} else
			level.getPlayer().setShield(false);
	}

	public void run() {
		sleep(1000);

		flagWelcome = true;
		fadeColor();

		sleep(4000);

		fadeAwayColor();
		flagWelcome = false;
		flagBalloon = true;
		fadeColor();

		sleep(6000);

		fadeAwayColor();
		flagBalloon = false;
		flagBarrier = true;
		fadeColor();
		level.add(new Barrier(level, 487, -20));

		sleep(5000);

		fadeAwayColor();
		flagBarrier = false;
		flagPowerUp = true;
		fadeColor();
		level.add(new SmallBall(level, 124, -50));

		sleep(8000);

		fadeAwayColor();
		flagPowerUp = false;
		flagPowerDown = true;
		fadeColor();
		level.add(new LargeBall(level, 474, -50));

		sleep(5000);

		fadeAwayColor();
		flagPowerDown = false;
		flagAchievements = true;
		fadeColor();

		sleep(10000);

		fadeAwayColor();
		flagAchievements = false;
		flagEnd = true;
		fadeColor();

		sleep(3000);

		fadeAwayColor();
		flagEnd = false;
		level.getPlayer().setShield(false);
		inTutorial = false;
	}

	public void render(Canvas canvas) {
		if (flagWelcome) {
			Game.renderText(canvas, "Welcome to Soar!", Game.getDisplayWidth() / 2 - 260, 300, fade, 75);
		} else if (flagBalloon) {
			Game.renderText(canvas, "Take control of your balloon by simply", 65, 300, fade, 42);
			Game.renderText(canvas, "touching it and dragging it", 165, 350, fade, 42);
			Game.renderText(canvas, "around the screen.", 230, 400, fade, 42);
		} else if (flagBarrier) {
			Game.renderText(canvas, "Navigate your balloon across the sky", 75, 300, fade, 42);
			Game.renderText(canvas, "to avoid Barriers.", 240, 350, fade, 42);
		} else if (flagPowerUp) {
			Game.renderText(canvas, "Collect Power-Ups along the way", 125, 300, fade, 42);
			Game.renderText(canvas, "to help improve your score!", 160, 350, fade, 42);
		} else if (flagPowerDown) {
			Game.renderText(canvas, "Be sure to watch out for", 180, 300, fade, 42);
			Game.renderText(canvas, "the         Power-Downs!", 192, 350, fade, 42);
			int alpha = (fade >> 24) & 0xff;
			int rest = (fade >> 8) & 0xffff;
			int clr = (alpha << 24) | (0xff << 18) | rest;
			Game.renderText(canvas, "RED", 265, 350, clr, 42);
		} else if (flagAchievements) {
			Game.renderText(canvas, "Complete achievements to unlock", 105, 300, fade, 42);
			Game.renderText(canvas, "special abilities for your balloon.", 120, 350, fade, 42);
			Game.renderText(canvas, "You can set your ability before", 135, 420, fade, 42);
			Game.renderText(canvas, "you start a game.", 235, 470, fade, 42);
		} else if (flagEnd) {
			Game.renderText(canvas, "Have Fun!", 240, 300, fade, 75);
		}
	}

	private void fadeColor() {
		int alpha = (fade >> 24) & 0xff;

		while (alpha < 0xdd) {
			int clr = fade & 0xffffff;
			alpha += 4;
			if (alpha > 0xdd)
				alpha = 0xdd;
			fade = (alpha << 24) | clr;

			sleep(5);
		}
	}

	private void fadeAwayColor() {
		int alpha = (fade >> 24) & 0xff;

		while (alpha > 0) {
			int clr = fade & 0xffffff;
			if (alpha < 4)
				alpha = 0;
			else
				alpha -= 4;
			fade = (alpha << 24) | clr;

			sleep(5);
		}
	}

	public void tick() {
	}

	public void dispose() {
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean inTutorial() {
		return inTutorial;
	}
}