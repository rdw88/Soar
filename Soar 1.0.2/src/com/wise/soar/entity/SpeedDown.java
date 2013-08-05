package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class SpeedDown extends PowerUp {
	public SpeedDown(Level level) {
		super(level);

		sprites = Resource.speedDown;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public SpeedDown(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.speedDown;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	protected synchronized void give() {
		level.setScrollSpeed(-3);
	}

	protected synchronized void take() {
		level.setScrollSpeed(3);
	}

	public String toString() {
		return "Speed Decrease";
	}

	public boolean isGood() {
		return true;
	}
}