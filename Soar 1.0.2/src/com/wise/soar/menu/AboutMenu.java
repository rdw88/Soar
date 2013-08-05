package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;

public class AboutMenu extends SubMenu {
	private String[] message = { "Hello! My name is Ryan Wise and I am the sole creator", "and developer of Soar. This is the first game that", "I have fully completed and published so I hope",
			"you enjoy it!" };

	public AboutMenu(Menu menu, Level level) {
		super(menu, level);
	}

	protected void struct() {
	}

	public void render(Canvas canvas) {
		if (!isOpen())
			return;

		super.render(canvas);

		Game.renderText(canvas, "About Soar &", 197, 150, 0xff1c45ff, 72);
		Game.renderText(canvas, "The Developer", 190, 220, 0xff1c45ff, 72);

		for (int i = 0; i < message.length; i++) {
			Game.renderText(canvas, message[i], 30, 320 + (i * 50), 0xff3333ff, 32);
		}

		Game.renderText(canvas, "If you have any questions, concerns, or feedback", 30, 650, 0xff3333ff, 32);
		Game.renderText(canvas, "contact me at ryanw527@gmail.com", 30, 700, 0xff3333ff, 32);
	}

	@Override
	public void goBack() {
		close();
		parent.open();
	}
}
