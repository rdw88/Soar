package com.wise.soar.res;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.wise.soar.App;
import com.wise.soar.WelcomeScreen;

public class Sprite {
	public static int SPRITE_COUNT;

	private int width, height;
	public Bitmap image;

	public Sprite(int id) {
		image = Resource.getImage(WelcomeScreen.instance.res, id);
		width = image.getScaledWidth(App.res.getDisplayMetrics());
		height = image.getScaledHeight(App.res.getDisplayMetrics());

		SPRITE_COUNT++;
	}

	public Sprite(int id, int x, int y, int width, int height) {
		image = Resource.getImage(WelcomeScreen.instance.res, id);

		Bitmap section = Bitmap.createBitmap(image, x, y, width, height);
		image = section;

		this.width = image.getScaledWidth(App.res.getDisplayMetrics());
		this.height = image.getScaledHeight(App.res.getDisplayMetrics());

		SPRITE_COUNT++;
	}

	public Sprite(Bitmap b) {
		image = b;
		width = image.getScaledWidth(App.res.getDisplayMetrics());
		height = image.getScaledHeight(App.res.getDisplayMetrics());
	}

	public void scale(float scaleW, float scaleH) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);

		Bitmap b = image;

		image = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
