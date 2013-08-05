package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.RenderableObject;
import com.wise.soar.res.Sprite;

public abstract class MenuItem implements RenderableObject {
	protected Sprite sprite;
	protected int x, y;

	protected boolean visible = true;

	protected int width, height;

	public MenuItem(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Canvas canvas) {
		if (sprite == null || !visible)
			return;

		canvas.drawBitmap(sprite.image, x, y, Game.paint);
	}

	public abstract void tick();

	public void dispose() {
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setVisible(boolean b) {
		visible = b;
	}
}