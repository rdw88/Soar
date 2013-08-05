package com.wise.soar.entity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sound;
import com.wise.soar.res.Sprite;

public class Player extends Entity {
	public static final int NORMAL_SIZE = 0;
	public static final int SMALL_SIZE = 1;
	public static final int LARGE_SIZE = 2;

	public static final int TYPE_NORMAL = 3;
	public static final int TYPE_EXTRA_LIFE = 4;
	public static final int TYPE_REPEL_POWER_DOWN = 5;
	public static final int TYPE_ATTRACT_POWER_UP = 6;
	public static final int TYPE_ALL_ABILITIES = 7;
	public static final int TYPE_FLASH = 8;
	public static final int TYPE_HALF = 9;

	public static final int FLASH_COOLDOWN = 45;

	public static final int COLOR_RED = 0xff0000;
	public static final int COLOR_GREEN = 0x00be21;
	public static final int COLOR_BLUE = 0x0026ff;
	public static final int COLOR_PINK = 0xff3ae4;
	public static final int COLOR_PURPLE = 0x891cff;
	public static final int COLOR_GOLD = 0xffba00;
	public static final int COLOR_ORANGE = 0xff610c;
	public static final int COLOR_LIGHT_GREEN = 0x87ffab;
	public static final int COLOR_LIGHT_RED = 0xffa0ad;
	public static final int COLOR_LIGHT_BLUE = 0x91aaff;
	public static final int COLOR_LIGHT_PINK = 0xffb7db;

	public static final int COLOR_FADE_RED = 0xffbfbf;
	public static final int COLOR_FADE_GREEN = 0xafe0b7;
	public static final int COLOR_FADE_BLUE = 0xbac4ff;
	public static final int COLOR_FADE_PINK = 0xffbff6;
	public static final int COLOR_FADE_PURPLE = 0xd8b5ff;
	public static final int COLOR_FADE_GOLD = 0xffe093;
	public static final int COLOR_FADE_ORANGE = 0xffa575;
	public static final int COLOR_FADE_LIGHT_GREEN = 0xd6ffe2;
	public static final int COLOR_FADE_LIGHT_RED = 0xffc6ce;
	public static final int COLOR_FADE_LIGHT_BLUE = 0xbfceff;
	public static final int COLOR_FADE_LIGHT_PINK = 0xffddee;

	private Sprite[] sprites = new Sprite[3];
	private Sprite[][] pops = new Sprite[3][16];

	private PowerUp power;

	private final Sound pop = Resource.soundPop;

	private long grabTime;
	private long releaseTime;
	private float startX, startY;
	private float velocityX, velocityY;
	private boolean velocitySet, grabTimeSet;

	private int tickCount;
	private int powerTime;
	private int badPowers;
	private int size;
	private int currentColor;

	private boolean hasPower;
	private boolean invincible;
	private boolean visible = true;
	private boolean hasShield;
	private boolean collectedPower;
	private boolean popped;

	public Player(Level level, int color) {
		super(level);

		setColor(color);
		sprite = sprites[0];

		power = new PowerUp(level);

		x = Game.getDisplayWidth() / 2 - 24;
		y = Game.getDisplayHeight() + 100;
		width = sprite.getWidth();
		height = (sprite.getHeight() / 2) - 10;

		velocityX = 0.001f;
		velocityY = 0.005f;
		velocitySet = true;

		floatUp();
	}

	public void registerType(int type) {
		PowerUp.setSpecialty(PowerUp.NO_SPECIALTY);
		level.inFlashMode(false);
		setShield(false);

		if (type == TYPE_ATTRACT_POWER_UP)
			PowerUp.setSpecialty(PowerUp.ATTRACT);
		else if (type == TYPE_REPEL_POWER_DOWN)
			PowerUp.setSpecialty(PowerUp.REPEL);
		else if (type == TYPE_EXTRA_LIFE) {
			setShield(true);
		} else if (type == TYPE_ALL_ABILITIES) {
			PowerUp.setSpecialty(PowerUp.ATTRACT_AND_REPEL);
			setShield(true);
			level.inFlashMode(true);
			//level.inHalfMode(true);
		} else if (type == TYPE_FLASH) {
			level.inFlashMode(true);
		}
	}

	private void floatUp() {
		new Thread(new Runnable() {
			public void run() {
				while (y > Game.getDisplayHeight() / 2 + 95) {
					y -= 1.5f;

					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void render(Canvas canvas) {
		if (visible)
			canvas.drawBitmap(sprite.image, x, y, Game.paint);

		if (!hasPower)
			return;

		int color = 0;
		int green = 0xff00ff00;
		int red = 0xffff0000;

		if (powerTime / 60 > 5)
			color = power.isGood() ? green : red;
		else
			color = power.isGood() ? red : green;

		String text = power.toString() + ":";

		Game.paint.setColor(color);
		RectF rect = new RectF();
		rect.left = Game.getDisplayWidth() - 225;
		rect.top = 15;
		rect.right = Game.getDisplayWidth() - 25 - ((PowerUp.POWER_TIME - powerTime) / 6);
		rect.bottom = 45;

		canvas.drawRoundRect(rect, 10, 10, Game.paint);

		Game.renderText(canvas, text, Game.getDisplayWidth() - 250 - (text.length() * 17), 43, color, 40);
	}

	public void tick() {
		hasPower = power.isEnabled();

		if (power instanceof ScoreUp || power instanceof ScoreDown)
			hasPower = false;

		movementLogic();

		if (hasPower)
			powerTime--;
		else if (powerTime != PowerUp.POWER_TIME)
			powerTime = PowerUp.POWER_TIME;

		tickCount++;
	}

	private void setPixels(int color, int fade) {
		for (int i = 0; i < sprites.length; i++) {
			int width = sprites[i].getWidth();
			int height = sprites[i].getHeight();

			int[] pixels = new int[width * height];
			sprites[i].image.getPixels(pixels, 0, width, 0, 0, width, height);

			for (int k = 0; k < pixels.length; k++) {
				int clr = pixels[k] & 0xffffff;

				if (clr == 0xff0000) {
					int cc = (((pixels[k] >> 24) & 0xff) << 24) | color;
					pixels[k] = cc;
				} else if (clr == 0xffbfbf) {
					int cc = (((pixels[k] >> 24) & 0xff) << 24) | fade;
					pixels[k] = cc;
				}
			}

			sprites[i] = new Sprite(Bitmap.createBitmap(pixels, width, height, Config.ARGB_8888));
		}

		for (int i = 0; i < pops.length; i++) {
			Sprite[] p = new Sprite[pops[i].length];

			for (int j = 0; j < pops[i].length; j++) {
				int width = pops[i][j].getWidth();
				int height = pops[i][j].getHeight();

				int[] pixels = new int[width * height];
				pops[i][j].image.getPixels(pixels, 0, width, 0, 0, width, height);

				for (int k = 0; k < pixels.length; k++) {
					int clr = pixels[k] & 0xffffff;

					if (clr == 0xff0000) {
						int cc = (((pixels[k] >> 24) & 0xff) << 24) | color;
						pixels[k] = cc;
					} else if (clr == 0xffbfbf) {
						int cc = (((pixels[k] >> 24) & 0xff) << 24) | fade;
						pixels[k] = cc;
					}
				}

				p[j] = new Sprite(Bitmap.createBitmap(pixels, width, height, Config.ARGB_8888));
			}

			pops[i] = p;
		}
	}

	private void setColor(int color) {
		this.currentColor = color;

		sprites[0] = Resource.balloon;
		sprites[1] = Resource.balloon_small;
		sprites[2] = Resource.balloon_large;
		pops[0] = Resource.pop;
		pops[1] = Resource.pop_small;
		pops[2] = Resource.pop_large;

		int fade = 0;

		if (color == Player.COLOR_BLUE)
			fade = COLOR_FADE_BLUE;
		else if (color == Player.COLOR_GOLD)
			fade = COLOR_FADE_GOLD;
		else if (color == Player.COLOR_GREEN)
			fade = COLOR_FADE_GREEN;
		else if (color == Player.COLOR_PINK)
			fade = COLOR_FADE_PINK;
		else if (color == Player.COLOR_PURPLE)
			fade = COLOR_FADE_PURPLE;
		else if (color == Player.COLOR_RED)
			fade = COLOR_FADE_RED;
		else if (color == Player.COLOR_ORANGE)
			fade = COLOR_FADE_ORANGE;
		else if (color == Player.COLOR_LIGHT_BLUE)
			fade = COLOR_FADE_LIGHT_BLUE;
		else if (color == Player.COLOR_LIGHT_GREEN)
			fade = COLOR_FADE_LIGHT_GREEN;
		else if (color == Player.COLOR_LIGHT_PINK)
			fade = COLOR_FADE_LIGHT_PINK;
		else if (color == Player.COLOR_LIGHT_RED)
			fade = COLOR_FADE_LIGHT_RED;

		setPixels(color, fade);
	}

	public void changeSize(int size) {
		if (size == NORMAL_SIZE) {
			sprite = sprites[0];
			x -= 18;
			y -= 18;
		} else if (size == SMALL_SIZE) {
			sprite = sprites[1];
			x += 18;
			y += 18;
		} else if (size == LARGE_SIZE) {
			sprite = sprites[2];
		}

		width = sprite.getWidth();
		height = (sprite.getHeight() / 2) - 10;
		this.size = size;
	}

	public void pop() {
		powerTime = -1;

		new Thread(new Runnable() {
			public void run() {
				pop.play();

				for (int i = 0; i < pops[size].length; i++) {
					sprite = pops[size][i];

					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					y += (i + 15);
				}

				int k = 0;
				while (y < Game.getDisplayHeight()) {
					y += 30;

					if (k % 2 == 0)
						sprite = pops[size][pops[size].length - 2];
					else
						sprite = pops[size][pops[size].length - 1];

					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					k++;
				}

				if (hasShield) {
					long sleep = 500;

					if (level.getTutorial().inTutorial())
						sleep = 3000;

					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					sprite = sprites[0];
					x = Game.getDisplayWidth() / 2;
					hasShield = false;

					while (y > Game.getDisplayHeight() / 2 + 95) {
						y -= 1.5f;

						try {
							Thread.sleep(4);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else
					popped = true;
			}
		}).start();
	}

	private void movementLogic() {
		if (InputHandler.isScreenTouched()) {
			int mouseFactor = 30;

			if ((InputHandler.getX() < x - mouseFactor || InputHandler.getY() < y - mouseFactor || InputHandler.getX() >= x + width + mouseFactor || InputHandler.getY() >= y + height + mouseFactor)
					&& !grabTimeSet) {
				InputHandler.releaseFinger();
				return;
			}

			if (!grabTimeSet) {
				grabTime = System.currentTimeMillis();
				grabTimeSet = true;
			}

			if (tickCount % 3 == 0) {
				startX = x;
				startY = y;
			}

			int offset = width / 2;

			x = InputHandler.getX() - offset;
			y = InputHandler.getY() - offset;

			if (x < 0)
				x = 0;
			if (y < 0)
				y = 0;
			if (y + sprite.getHeight() / 2 >= level.getHeight())
				y = level.getHeight() - (sprite.getHeight() / 2);
			if (x + sprite.getWidth() >= level.getWidth())
				x = level.getWidth() - sprite.getWidth();

			velocitySet = false;
		} else if (!velocitySet) {
			releaseTime = System.currentTimeMillis();

			float deltaTime = releaseTime - grabTime;
			float deltaX = (x - startX);
			float deltaY = (y - startY);

			velocityX = deltaX / deltaTime;
			velocityY = deltaY / deltaTime;

			velocitySet = true;
			grabTimeSet = false;
		}

		float alter = 2000 / 60f;

		if (Math.abs(velocityX * alter) > 55)
			velocityX = 55 / alter;
		if (Math.abs(velocityY * alter) > 55)
			velocityY = 55 / alter;

		x += velocityX * alter;
		y += velocityY * alter;

		if (x + sprite.getWidth() >= level.getWidth() || x < 0)
			velocityX = -velocityX;
		if (y + sprite.getHeight() / 2 >= level.getHeight() || y < 0)
			velocityY = -velocityY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public PowerUp getPower() {
		return power;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean hasPower() {
		return hasPower;
	}

	public void setPower(PowerUp power) {
		this.power = power;

		if (power.isGood()) {
			collectedPower = true;
		} else if (!(power instanceof ScoreDown))
			badPowers++;
	}

	public boolean hasCollectedPower() {
		return collectedPower;
	}

	public void setShield(boolean shield) {
		hasShield = shield;
	}

	public int getBadPowers() {
		return badPowers;
	}

	public boolean isPopped() {
		return popped;
	}

	public boolean hasShield() {
		return hasShield;
	}

	public int getPowerTime() {
		return powerTime;
	}

	public int getColor() {
		return currentColor;
	}
}