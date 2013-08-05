package com.wise.soar.particle;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.RenderableObject;
import com.wise.soar.level.Level;
import com.wise.soar.res.Sprite;

public abstract class Particle implements RenderableObject {
	protected Level level;
	protected Sprite sprite;
	protected float x;
	protected float y;
	protected float width, height;

	protected boolean collidable;

	public Particle(Level level) {
		this.level = level;
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(sprite.image, x, y, Game.paint);
	}

	public abstract void tick();

	protected abstract void animate();

	public void scroll(float velocity) {
		y += velocity;
	}

	public void shift(float velocity) {
		x += velocity;
	}

	public void dispose() {
		level.remove(this);
	}

	public boolean isCollidable() {
		return collidable;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public abstract String toString();
}