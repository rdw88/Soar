package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;
import com.wise.soar.res.Resource;

public class OptionsMenu extends SubMenu {
	private Button reset, about, sound;
	private AboutMenu aboutMenu;

	private Container container;

	public OptionsMenu(Menu menu, Level level) {
		super(menu, level);
		aboutMenu = new AboutMenu(this, level);
	}

	public void render(Canvas canvas) {
		aboutMenu.render(canvas);

		if (!isOpen())
			return;

		super.render(canvas);

		Game.renderText(canvas, "Options", (Game.getDisplayWidth() / 2) - 138, 180, 0xff3434ef, 92);

		if (container != null)
			container.render(canvas);
	}

	public void tick() {
		super.tick();

		if (container != null)
			container.tick();

		aboutMenu.tick();
	}

	protected void struct() {
		reset = new Button(Game.getDisplayWidth() / 2 - 100, 650, "Reset Game     ");
		reset.setType(Button.TYPE_MENU);
		reset.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (container != null && container.isOpen())
					return;

				container = new Container() {
					public void struct() {
						setTitle("Are you sure?");

						Button button = new Button(getX() + 50, getY() + 130, "Yes, Erase Everything      ");
						Button no = new Button(getX() + 50, getY() + 250, "No, Keep Everything      ");
						button.setType(Button.TYPE_CONTAINER);
						no.setType(Button.TYPE_CONTAINER);

						button.addButtonEvent(new ButtonEvent() {
							public void onEvent() {
								reset();
								close();
							}
						});

						no.addButtonEvent(new ButtonEvent() {
							public void onEvent() {
								close();
							}
						});

						add(button);
						add(no);
					}
				};

				container.open();
			}
		});

		about = new Button(Game.getDisplayWidth() / 2 - 100, 500, "About    ");
		about.setType(Button.TYPE_MENU);
		about.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (container != null && container.isOpen())
					return;

				aboutMenu.open();
				close();
			}
		});

		String text = "Sound: ";

		text += Saves.getSound() ? "ON     " : "OFF    ";

		sound = new Button(Game.getDisplayWidth() / 2 - 100, 350, text);
		sound.setType(Button.TYPE_MENU);
		sound.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				if (container != null && container.isOpen())
					return;

				if (Saves.getSound()) {
					Saves.setSound(false);
					sound.setText("Sound: OFF    ");
				} else {
					Saves.setSound(true);
					sound.setText("Sound: ON     ");
				}
			}
		});

		add(reset);
		add(about);
		add(sound);
	}

	private void reset() {
		Resource.resetStatistics(level);
	}

	@Override
	public void goBack() {
		if (isOpen())
			super.goBack();
	}
}