package com.wise.soar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class LoadingBar implements RenderableObject {
	private Bitmap bitmap = BitmapFactory.decodeResource(WelcomeScreen.instance.res, R.drawable.balloon_large);
	private int resourcesLeft = Resource.RESOURCE_COUNT - Sprite.SPRITE_COUNT;

	private int width;
	private float height;

	private int x, y;

	private Rect original, dest;

	public LoadingBar(int w, int h) {
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		x = w / 2 - (width / 2);
		y = h / 2 - ((int) height / 2);

		original = new Rect(x, y, width, (int) (height - (height * (resourcesLeft / 100.0))));
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(bitmap, dest, original, null);
	}

	public void tick() {
		resourcesLeft = Resource.RESOURCE_COUNT - Sprite.SPRITE_COUNT;

		//y--;

		if (resourcesLeft <= 0)
			resourcesLeft = 1;

		if (resourcesLeft == 1) {
			y -= 17;
		}

		original = new Rect(x, y, width + x, (int) (height - (height * (resourcesLeft / 100.0))) + y);
		dest = new Rect(0, 0, width, (int) (height - (height * (resourcesLeft / 100.0))));
	}

	public void dispose() {
	}

	public int getRemainingResources() {
		return resourcesLeft;
	}
}
