package com.wise.soar.menu;

import com.wise.soar.level.Level;

public class PauseMenu extends Container {
	private Button resume, restart, exit;
	private final String title = "Paused  ";

	private Level level;

	public PauseMenu(Level level) {
		super();

		this.level = level;
	}

	protected void struct() {
		setTitle(title);

		resume = new Button(getX() + 50, getY() + 130, "Resume    ");
		resume.setType(Button.TYPE_CONTAINER);

		resume.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				resume();
			}
		});

		restart = new Button(getX() + 50, getY() + 240, "Restart   ");
		restart.setType(Button.TYPE_CONTAINER);

		restart.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				restart();
			}
		});

		exit = new Button(getX() + 50, getY() + 350, "Exit  ");
		exit.setType(Button.TYPE_CONTAINER);

		exit.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				open = false;
				exit();
			}
		});

		add(resume);
		add(restart);
		add(exit);
	}

	private void resume() {
		close();
		level.resume();
	}

	private void restart() {
		renderItems = false;
		open = false;
		level.init();
	}

	private void exit() {
		level.goToMainMenu();
	}
}