package com.wise.soar.entity;

import java.util.List;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;

public class Barrier extends Entity {
	private Player player;

	private boolean flashed, small;

	public Barrier(Level level) {
		super(level);

		small = Rand.random.nextBoolean();

		if (small) {
			sprite = Resource.barrierSmall;
			width = 50;
			height = 15;
		} else {
			sprite = Resource.barrierLarge;
			width = 75;
			height = 17;
		}

		player = level.getPlayer();
	}

	public Barrier(Level level, float x, float y) {
		this(level);
		this.x = x;
		this.y = y;

		if (y > -100)
			dispose();

		for (int i = 0; i < level.getEntities().size(); i++) {
			List<Entity> entities = level.getEntities();
			Entity e = entities.get(i);
			if (e.y + e.height < y - 30 || e.y > y + height + 30)
				continue;

			if (e.x - (x + width) < 24 || (e.x + width) > x - 24)
				if (e instanceof Barrier)
					e.dispose();
		}
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(sprite.image, x, y, Game.paint);
	}

	public void tick() {
		int forgive = 0;
		if (player.getX() + forgive < x + width && player.getY() + forgive < y + height) {
			if (player.getX() + player.getWidth() - forgive > x && player.getY() + player.getHeight() - forgive > y) {
				if (level.getTutorial().inTutorial())
					player.pop();
				else if (!player.isInvincible())
					level.endGame();
			}
		}

		if (y > Game.getDisplayHeight())
			dispose();
	}

	public void flash() {
		if (small) {
			if (flashed) {
				if (this instanceof MobileBarrier) {
					sprite = Resource.mobileSmall;
				} else {
					sprite = Resource.barrierSmall;
				}

				flashed = false;
			} else {
				sprite = Resource.barrier_flash_small;
				flashed = true;
			}
		} else {
			if (flashed) {
				if (this instanceof MobileBarrier) {
					sprite = Resource.mobileLarge;
				} else {
					sprite = Resource.barrierLarge;
				}

				flashed = false;
			} else {
				sprite = Resource.barrier_flash_large;
				flashed = true;
			}
		}
	}

	public static Barrier random(Level level, float x, float y) {
		int r = 0;
		if (!level.isInSpace())
			r = Rand.random.nextInt(1);
		else
			r = Rand.random.nextInt(2);

		if (r == 0)
			return new Barrier(level, x, y);
		else if (r == 1)
			return new MobileBarrier(level, x, y);

		return null;
	}

	public String toString() {
		return "Barrier @ (" + x + ", " + y + ")";
	}
}