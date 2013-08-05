package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;

public class Multiplier extends PowerUp {
	private int factor;

	public Multiplier(Level level) {
		super(level);

		if (Rand.random.nextInt(2) == 0) {
			sprites = Resource.x2mult;
			factor = 2;
		} else {
			sprites = Resource.x4mult;
			factor = 4;
		}

		sprite = sprites[0];
		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public Multiplier(Level level, float x, float y) {
		super(level, x, y);

		if (Rand.random.nextInt(2) == 0) {
			sprites = Resource.x2mult;
			factor = 2;
		} else {
			sprites = Resource.x4mult;
			factor = 4;
		}

		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	protected synchronized void give() {
		level.setMultiplier(factor);
	}

	protected synchronized void take() {
		level.setMultiplier(1);
	}

	public String toString() {
		return "" + factor + "x" + " Score";
	}

	public boolean isGood() {
		return true;
	}
}