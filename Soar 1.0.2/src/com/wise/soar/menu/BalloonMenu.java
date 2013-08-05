package com.wise.soar.menu;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.entity.Player;
import com.wise.soar.level.Level;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class BalloonMenu extends SubMenu {
	public static int FINAL_COLOR = Player.COLOR_RED;
	public static int FINAL_TYPE = Player.TYPE_NORMAL;

	private Image[] colors;

	private Image balloon;

	private AbilityMenu abil;

	//	private int type;

	private int currentColor = FINAL_COLOR;

	private int[] color = { Player.COLOR_RED, Player.COLOR_BLUE, Player.COLOR_PINK, Player.COLOR_GREEN, Player.COLOR_PURPLE, Player.COLOR_ORANGE, Player.COLOR_LIGHT_RED, Player.COLOR_LIGHT_BLUE,
			Player.COLOR_LIGHT_PINK, Player.COLOR_LIGHT_GREEN, Player.COLOR_GOLD };

	public BalloonMenu(Menu menu, Level level) {
		super(menu, level);
		abil = new AbilityMenu(this, level);
	}

	public void render(Canvas canvas) {
		if (!isOpen())
			return;

		super.render(canvas);

		abil.render(canvas);

		Game.renderText(canvas, "Choose your color:", width / 2 - 195, 120, 0xff3434ff, 52);
	}

	public void tick() {
		if (!isOpen())
			return;

		super.tick();

		abil.tick();

		if (InputHandler.isScreenTouched()) {
			for (int i = 0; i < colors.length; i++) {
				if (InputHandler.getX() > colors[i].getX() && InputHandler.getX() < colors[i].getX() + colors[i].getWidth()) {
					if (InputHandler.getY() > colors[i].getY() && InputHandler.getY() < colors[i].getY() + colors[i].getHeight()) {
						setBalloon(color[i]);
					}
				}
			}
		}
	}

	protected void struct() {
		int stage = level.playCount.getStage() + 1;
		stage += level.powerUpCount.getStage();

		if (stage > 10)
			stage = 10;

		if (level.allComplete.isComplete())
			stage++;

		colors = new Image[stage];

		Sprite[] sprites = { setColor(Player.COLOR_RED), setColor(Player.COLOR_BLUE), setColor(Player.COLOR_PINK), setColor(Player.COLOR_GREEN), setColor(Player.COLOR_PURPLE),
				setColor(Player.COLOR_ORANGE), setColor(Player.COLOR_LIGHT_RED), setColor(Player.COLOR_LIGHT_BLUE), setColor(Player.COLOR_LIGHT_PINK), setColor(Player.COLOR_LIGHT_GREEN),
				setColor(Player.COLOR_GOLD) };

		int len = 0;

		if (colors.length < 5)
			len = colors.length;
		else
			len = 5;

		int startX = (Game.getDisplayWidth() / 2) - (len * 29);

		int mult = 0;
		int y = 0;
		for (int i = 0; i < colors.length; i++) {
			if (i > 4) {
				mult = i - 5;
				y = 220;
			} else {
				mult = i;
				y = 160;
			}

			colors[i] = new Image(sprites[i], startX + (mult * 60), y);
		}

		if (FINAL_COLOR == Player.COLOR_GOLD) {
			FINAL_COLOR = Player.COLOR_RED;
		}

		setBalloon(FINAL_COLOR);

		for (int i = 0; i < colors.length; i++)
			add(colors[i]);

		add(balloon);
	}

	private Sprite setColor(int color) {
		Sprite orig = Resource.color_box;

		int width = orig.getWidth();
		int height = orig.getHeight();
		int[] pixels = new int[width * height];

		orig.image.getPixels(pixels, 0, width, 0, 0, width, height);

		for (int i = 0; i < pixels.length; i++) {
			int current = pixels[i] & 0xffffff;

			if (current == 0x808080) {
				pixels[i] = (((pixels[i] >> 24) & 0xff) << 24) | color;
			}
		}

		return new Sprite(Bitmap.createBitmap(pixels, width, height, Config.ARGB_8888));
	}

	public void onPlayEvent(int type) {
		FINAL_TYPE = type;
		FINAL_COLOR = currentColor;

		level.init();
		close();
		destroyElements();
	}

	private void setBalloon(int color) {
		if (currentColor == color)
			return;

		currentColor = color;
		remove(balloon);

		if (color == Player.COLOR_RED)
			balloon = new Image(Resource.balloon_large, 0, 0);
		else if (color == Player.COLOR_BLUE)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_BLUE, Player.COLOR_FADE_BLUE), 0, 0);
		else if (color == Player.COLOR_GREEN)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_GREEN, Player.COLOR_FADE_GREEN), 0, 0);
		else if (color == Player.COLOR_PINK)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_PINK, Player.COLOR_FADE_PINK), 0, 0);
		else if (color == Player.COLOR_PURPLE)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_PURPLE, Player.COLOR_FADE_PURPLE), 0, 0);
		else if (color == Player.COLOR_GOLD)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_GOLD, Player.COLOR_FADE_GOLD), 0, 0);
		else if (color == Player.COLOR_ORANGE)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_ORANGE, Player.COLOR_FADE_ORANGE), 0, 0);
		else if (color == Player.COLOR_LIGHT_BLUE)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_LIGHT_BLUE, Player.COLOR_FADE_LIGHT_BLUE), 0, 0);
		else if (color == Player.COLOR_LIGHT_GREEN)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_LIGHT_GREEN, Player.COLOR_FADE_LIGHT_GREEN), 0, 0);
		else if (color == Player.COLOR_LIGHT_PINK)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_LIGHT_PINK, Player.COLOR_FADE_LIGHT_PINK), 0, 0);
		else if (color == Player.COLOR_LIGHT_RED)
			balloon = new Image(Resource.balloonColorSwitch(Resource.balloon_large, Player.COLOR_LIGHT_RED, Player.COLOR_FADE_LIGHT_RED), 0, 0);

		balloon.setX(width / 2 - balloon.getWidth() / 2);
		balloon.setY(320);
		add(balloon);
	}
}