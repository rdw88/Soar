package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;

public class AchievementNotifier extends Container {
	private final String title = "Achievement Success!";

	private String[] message;

	public AchievementNotifier(String[] message) {
		this.message = message;
	}

	protected void struct() {
		setTitle(title);
		setType(TYPE_ACHIEVEMENT);
	}

	public void render(Canvas canvas) {
		if (!open)
			return;

		super.render(canvas);

		if (renderItems)
			for (int i = 0; i < message.length; i++)
				Game.renderText(canvas, message[i], getX() + 40, (getY() + 85) + (i * 35), 0xff30C637, 32);
	}
}