package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class ScoreUp extends PowerUp {
	public ScoreUp(Level level) {
		super(level);

		sprites = Resource.scoreUp;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();

		length = 0;
	}

	public ScoreUp(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.scoreUp;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();

		length = 0;
	}

	protected synchronized void give() {
		level.addScore(1000);
	}

	protected synchronized void take() {
	}

	public String toString() {
		return "Score Up @ (" + x + ", " + y + ")";
	}

	public boolean isGood() {
		return true;
	}
}