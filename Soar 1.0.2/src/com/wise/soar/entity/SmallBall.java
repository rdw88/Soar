package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class SmallBall extends PowerUp {
	public SmallBall(Level level) {
		super(level);

		sprites = Resource.powerSmall;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public SmallBall(Level level, float x, float y) {
		super(level, x, y);

		sprites = Resource.powerSmall;
		sprite = sprites[0];

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	protected synchronized void give() {
		player.changeSize(Player.SMALL_SIZE);
	}

	protected synchronized void take() {
		player.changeSize(Player.NORMAL_SIZE);
	}

	public String toString() {
		return "Deflate";
	}

	public boolean isGood() {
		return true;
	}
}