package com.wise.soar.menu;

import com.wise.soar.level.Level;

public class GameOver extends Container {
	private Button playAgain, exit;

	private final String title = "Game Over!  ";
	private Level level;

	public GameOver(Level level) {
		this.level = level;

		level.pause();
	}

	protected void struct() {
		setTitle(title);
		setTitleColor(0xffff0000);

		playAgain = new Button(getX() + 50, getY() + 120, "Go Again!     ");
		playAgain.setType(Button.TYPE_CONTAINER);
		playAgain.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				renderItems = false;
				open = false;
				level.init();
			}
		});

		exit = new Button(getX() + 50, getY() + 240, "Exit   ");
		exit.setType(Button.TYPE_CONTAINER);
		exit.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				close();
				level.goToMainMenu();
			}
		});

		add(playAgain);
		add(exit);
	}
}