package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class SpeedUp extends PowerUp {
	public SpeedUp(Level level) {
		super(level);

		sprites = Resource.speedUp;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public SpeedUp(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.speedUp;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	protected synchronized void give() {
		level.setScrollSpeed(2);
	}

	protected synchronized void take() {
		level.setScrollSpeed(-2);
	}

	public String toString() {
		return "Speed Increase";
	}

	public boolean isGood() {
		return false;
	}
}