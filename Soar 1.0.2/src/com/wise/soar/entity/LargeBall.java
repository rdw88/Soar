package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class LargeBall extends PowerUp {
	public LargeBall(Level level) {
		super(level);

		sprites = Resource.powerLarge;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public LargeBall(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.powerLarge;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	protected synchronized void give() {
		player.changeSize(Player.LARGE_SIZE);
	}

	protected synchronized void take() {
		player.changeSize(Player.NORMAL_SIZE);
	}

	public String toString() {
		return "Inflate";
	}

	public boolean isGood() {
		return false;
	}
}