package com.wise.soar.entity;

import java.util.Random;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sound;
import com.wise.soar.res.Sprite;

public class PowerUp extends Entity {
	public static final int POWER_TIME = 21 * 60;

	public static final int NO_SPECIALTY = 0;
	public static final int ATTRACT = 1;
	public static final int REPEL = 2;
	public static final int ATTRACT_AND_REPEL = 3;

	private static int SPECIALTY;

	protected Sprite[] sprites;

	protected Player player;
	protected int length;

	private Sound powerup = Resource.soundPowerUp;

	private int counter;
	private int index;

	private int specialty;
	private float powerDownSlope;
	private float diff;

	protected volatile boolean enabled;

	public PowerUp(Level level) {
		super(level);
		player = level.getPlayer();
		length = 20000;
		specialty = PowerUp.SPECIALTY;
	}

	public PowerUp(Level level, float x, float y) {
		this(level);
		this.x = x;
		this.y = y;
		powerDownSlope = (y - player.getY()) / (x - player.getX());
		diff = (player.getX() - x) / 50;

		if (diff == 0)
			diff = 1;
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(sprite.image, x, y, Game.paint);
	}

	public void tick() {
		if (player.hasPower())
			dispose();

		if (!isGood()) {
			x += diff;
			y += Math.abs((powerDownSlope * diff));
		}

		if ((specialty == ATTRACT || specialty == ATTRACT_AND_REPEL) && isGood()) {
			float xPlayer = player.getX();
			float yPlayer = player.getY();
			float xDiff = x - xPlayer;
			float yDiff = y - yPlayer;

			if (Math.abs(xDiff) < 350 && Math.abs(yDiff) < 350) {
				if (xDiff == 0)
					xDiff = 1;

				float slope = yDiff / xDiff;

				if (xDiff < 0) {
					x += 3;

					if (x == xPlayer)
						x += 3;
				} else if (xDiff > 0) {
					x -= 3;

					if (x == xPlayer)
						x -= 3;
				}

				if (slope > 15)
					slope = 15;

				if (yDiff > 0)
					y -= Math.abs(slope * 3);
				else
					y += Math.abs(slope * 3);
			}
		}

		if ((specialty == REPEL || specialty == ATTRACT_AND_REPEL) && !isGood()) {
			float xPlayer = player.getX();
			float yPlayer = player.getY();
			float xDiff = x - xPlayer;
			float yDiff = y - yPlayer;

			if (Math.abs(xDiff) < 250 && Math.abs(yDiff) < 250) {
				if (xDiff == 0)
					xDiff = 1;

				float slope = yDiff / xDiff;

				if (xDiff < 0) {
					x--;

					if (x == xPlayer)
						x--;
				} else if (xDiff > 0) {
					x++;

					if (x == xPlayer)
						x++;
				}

				if (slope > 5)
					slope = 5;

				if (yDiff > 0)
					y += Math.abs(slope);
				else
					y -= Math.abs(slope);
			}
		}

		int forgive = 13;
		if (player.getX() + forgive < x + width && player.getY() + forgive < y + height) {
			if (player.getX() + player.getWidth() - forgive > x && player.getY() + player.getHeight() - forgive > y) {
				initPowerUp();
				dispose();
			}
		}

		if (y > Game.getDisplayHeight()) {
			dispose();
		}

		animate();
	}

	protected void animate() {
		if (counter % 3 == 0) {
			if (index < sprites.length - 1)
				index++;
			else
				index = 0;

			sprite = sprites[index];
		}
		counter++;
	}

	private synchronized void initPowerUp() {
		if (isGood())
			powerup.play();

		enabled = true;
		player.setPower(this);
		level.addPowerUpCount();

		new Thread(new Runnable() {
			public void run() {
				give();

				while (player.getPowerTime() > 0) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				take();
				enabled = false;
			}
		}).start();
	}

	protected synchronized void take() {
	}

	protected synchronized void give() {
	}

	public static void setSpecialty(int specialty) {
		PowerUp.SPECIALTY = specialty;
	}

	public static PowerUp random(Level level, float x, float y) {
		Random random = new Random();

		int rand = 0;
		if (level.getScrollSpeed() < 5)
			rand = random.nextInt(12);
		else
			rand = random.nextInt(14);

		if (rand == 0 || rand == 6)
			return new Multiplier(level, x, y);
		else if (rand == 1 || rand == 7)
			return new ScoreUp(level, x, y);
		else if (rand == 2 || rand == 8)
			return new ScoreDown(level, x, y);
		else if (rand == 3 || rand == 9)
			return new LargeBall(level, x, y);
		else if (rand == 4 || rand == 10)
			return new SmallBall(level, x, y);
		else if (rand == 5 || rand == 11)
			return new SpeedUp(level, x, y);
		else if (rand == 12 || rand == 13)
			return new SpeedDown(level, x, y);

		return null;
	}

	public String toString() {
		return "Power Up";
	}

	public boolean isGood() {
		return false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public long getLength() {
		return length;
	}
}