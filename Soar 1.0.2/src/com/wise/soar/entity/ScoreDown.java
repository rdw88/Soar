package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class ScoreDown extends PowerUp {
	public ScoreDown(Level level) {
		super(level);

		sprites = Resource.scoreDown;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();

		length = 0;
	}

	public ScoreDown(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.scoreDown;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();

		length = 0;
	}

	protected synchronized void give() {
		level.removeScore(1000);
	}

	protected synchronized void take() {
	}

	public String toString() {
		return "Score Down @ (" + x + ", " + y + ")";
	}

	public boolean isGood() {
		return false;
	}
}