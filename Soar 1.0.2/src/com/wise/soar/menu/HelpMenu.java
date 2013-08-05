package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class HelpMenu extends SubMenu {
	private Sprite[][] allPowers = new Sprite[5][];
	private Sprite[] current = new Sprite[5];

	private int counter;
	private int page;
	private int numPages = 2;

	private final int textColor = 0xff0000ff;

	private Button next, prev;

	public HelpMenu(Menu menu, Level level) {
		super(menu, level);

		setPowers();
	}

	protected void struct() {
		next = new Button(500, Game.getDisplayHeight() - 150, "");
		prev = new Button(200, Game.getDisplayHeight() - 150, "");

		next.setType(Button.TYPE_NEXT);
		prev.setType(Button.TYPE_PREVIOUS);

		next.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (page == numPages - 1)
					return;

				page++;

				setPowers();
			}
		});

		prev.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (page == 0)
					return;

				page--;

				setPowers();
			}
		});

		add(next);
		add(prev);
	}

	private void setPowers() {
		if (page == 1) {
			allPowers = new Sprite[3][];
			allPowers[0] = Resource.powerLarge;
			allPowers[1] = Resource.speedUp;
			allPowers[2] = Resource.scoreDown;
		} else if (page == 0) {
			allPowers = new Sprite[5][];
			allPowers[0] = Resource.powerSmall;
			allPowers[1] = Resource.scoreUp;
			allPowers[2] = Resource.speedDown;
			allPowers[3] = Resource.x2mult;
			allPowers[4] = Resource.x4mult;
		}

		current = new Sprite[allPowers.length];

		for (int i = 0; i < current.length; i++) {
			current[i] = allPowers[i][0];
		}
	}

	public void render(Canvas canvas) {
		super.render(canvas);

		if (!isOpen())
			return;

		Game.renderText(canvas, "Help!", Game.getDisplayWidth() / 2 - 92, 150, 0xffdd3333, 92);
		Game.renderText(canvas, "Page: " + (page + 1) + "/" + numPages, Game.getDisplayWidth() / 2 - 55, Game.getDisplayHeight() - 135, 0xffededed, 32);

		if (page == 0)
			page0(canvas);
		if (page == 1)
			page1(canvas);
	}

	private void page0(Canvas canvas) {
		String[] intro = { "You are a balloon and you want to do", "what all balloons want, Soar as high as you can!", "Grab hold of the balloon with your finger",
				"and guide it past obstacles on its way out", "of our atmosphere." };

		String[] power = { "Collect the many power-ups on your way up", "to help you soar even higher." };

		String[] descriptions = { "The size of your balloon is reduced for 20 seconds.", "Your score is increased by 1000.", "Obstacles' speeds are reduced for 20 seconds.",
				"Your score increases twice as fast for 20 seconds.", "Your score increases four times as fast for 20 seconds." };

		for (int i = 0; i < descriptions.length; i++)
			Game.renderText(canvas, descriptions[i], 160, 585 + (i * 80), textColor, 26);

		for (int i = 0; i < intro.length; i++)
			Game.renderText(canvas, intro[i], 50, 250 + (i * 40), textColor, 36);

		for (int i = 0; i < power.length; i++)
			Game.renderText(canvas, power[i], 50, 320 + ((intro.length - 1) * 40) + (i * 40), textColor, 36);

		for (int i = 0; i < current.length; i++)
			canvas.drawBitmap(current[i].image, 75, 550 + (i * 80), Game.paint);
	}

	private void page1(Canvas canvas) {
		Game.renderText(canvas, "Be sure to watch out for the         power-downs!", 40, 250, textColor, 36);
		Game.renderText(canvas, "RED", 485, 250, 0xffff0000, 36);
		Game.renderText(canvas, "They will be moving fast so watch out!", 40, 300, textColor, 36);

		String[] descriptions = { "The size of your balloon is increased for 20 seconds.", "Obstacles' speeds are increased for 20 seconds.", "Your score is reduced by 1000." };

		Game.renderText(canvas, "Complete achievements to unlock new colors", 40, 630, textColor, 36);
		Game.renderText(canvas, "and special abilities for you balloon.", 40, 675, textColor, 36);
		Game.renderText(canvas, "Abilities:", Game.getDisplayWidth() / 2 - 95, 750, textColor, 54);

		String[] abilities = { "Extra-Life - ", "Flash - ", "Pulse - ", "Magnet - " };
		String[] desc = { "Get a second chance!", "Instantly destroys all obstacles. (45 second cooldown)", "Repels Power-Downs", "Attracts Power-Ups" };

		for (int i = 0; i < current.length; i++)
			canvas.drawBitmap(current[i].image, 75, 350 + (i * 80), Game.paint);

		for (int i = 0; i < descriptions.length; i++)
			Game.renderText(canvas, descriptions[i], 160, 385 + (i * 80), textColor, 26);

		for (int i = 0; i < abilities.length; i++) {
			Game.renderText(canvas, abilities[i], 60, 810 + (i * 50), 0xffff2323, 24);
			Game.renderText(canvas, desc[i], 210, 810 + (i * 50), textColor, 24);
		}
	}

	public void tick() {
		super.tick();

		if (!isOpen())
			return;

		if (counter % 4 == 0) {
			if (page == 0) {
				for (int i = 0; i < current.length - 2; i++) {
					current[i] = allPowers[i][counter / 4];
				}

				if (counter / 4 == allPowers[0].length - 1)
					counter = 0;
			} else if (page == 1) {
				for (int i = 0; i < current.length; i++) {
					current[i] = allPowers[i][counter / 4];
				}

				if (counter / 4 == allPowers[0].length - 1)
					counter = 0;
			}
		}

		counter++;
	}
}
