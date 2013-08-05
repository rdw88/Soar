package com.wise.soar.level;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wise.soar.Game;
import com.wise.soar.RenderableObject;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class Background implements RenderableObject {
	private int height;

	private final Sprite background = Resource.background;

	private float backgroundY;

	private Rect bg;

	public Background(Level level) {
		height = level.getHeight();

		backgroundY = -(background.getHeight()) + height;
		bg = new Rect(0, (int) backgroundY, Game.getDisplayWidth(), (int) backgroundY + background.getHeight());
	}

	public void init() {
		backgroundY = -(background.getHeight()) + height;
	}

	public void render(Canvas canvas) {
		if (backgroundY < height)
			canvas.drawBitmap(background.image, null, bg, Game.paint);
	}

	public void tick() {
		backgroundY += 1.5f;

		bg.top = (int) backgroundY;
		bg.bottom = (int) backgroundY + background.getHeight();
	}

	public void dispose() {
	}

	public float getY() {
		return backgroundY;
	}
}