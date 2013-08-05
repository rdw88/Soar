package com.wise.soar;

import android.graphics.Canvas;

public interface RenderableObject {
	public void render(Canvas canvas);

	public void tick();

	public void dispose();
}
