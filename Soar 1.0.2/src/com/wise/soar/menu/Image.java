package com.wise.soar.menu;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.wise.soar.res.Sprite;

public class Image extends MenuItem {
	public Image(Sprite sprite, int x, int y) {
		super(x, y);

		this.sprite = sprite;
		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public void tick() {
	}

	public void scale(float scaleW, float scaleH) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);

		Bitmap b = sprite.image;

		sprite.image = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
	}
}