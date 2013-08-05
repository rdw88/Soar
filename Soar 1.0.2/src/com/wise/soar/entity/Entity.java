package com.wise.soar.entity;

import android.graphics.Canvas;

import com.wise.soar.RenderableObject;
import com.wise.soar.level.Level;
import com.wise.soar.res.Sprite;

public class Entity implements RenderableObject {
	protected Level level;
	protected Sprite sprite;

	protected float x;
	protected float y;
	protected int width, height;

	public Entity(Level level) {
		this.level = level;
	}

	public void render(Canvas canvas) {
	}

	public void tick() {
	}

	public void dispose() {
		level.remove(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void scroll(float velocity) {
		if (!(this instanceof Player))
			y += velocity;
	}

	public void shift(float velocity) {
		if (!(this instanceof Player))
			x += velocity;
	}
}