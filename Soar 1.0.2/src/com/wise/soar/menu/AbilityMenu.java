package com.wise.soar.menu;

import com.wise.soar.Game;
import com.wise.soar.entity.Player;
import com.wise.soar.level.Level;

public class AbilityMenu extends Container {
	private CheckBoxGroup group;
	private Button play;
	private BalloonMenu men;
	private int type;

	public AbilityMenu(BalloonMenu menu, Level level) {
		super(level);
		this.men = menu;
		setType(TYPE_LARGE);
		setX((Game.getDisplayWidth() - container.getWidth()) / 2);
		setY(600);
		open = true;
		renderItems = true;

		play.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				men.onPlayEvent(type);
				open = false;
				renderItems = false;
			}
		});
	}

	protected void struct() {
		setTitle("Choose an Ability");

		group = new CheckBoxGroup(60, 700);

		group.addCheckBox("No Ability", true, true, new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_NORMAL;
			}
		});

		group.addCheckBox("Extra Life", false, level.noPowerUps.isComplete(), new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_EXTRA_LIFE;
			}
		});

		group.addCheckBox("Flash", false, level.fivePowerDowns.isComplete(), new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_FLASH;
			}
		});

		group.addCheckBox("Pulse", false, level.powerUpCount.isComplete(), new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_REPEL_POWER_DOWN;
			}
		});

		group.addCheckBox("Magnet", false, level.everyPowerUp.isComplete(), new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_ATTRACT_POWER_UP;
			}
		});

		group.addCheckBox("All Abilities!", false, level.allComplete.isComplete(), new ButtonEvent() {
			public void onEvent() {
				type = Player.TYPE_ALL_ABILITIES;
			}
		});

		play = new Button(0, 0, "Play!   ");
		play.setType(Button.TYPE_CONTAINER);
		play.setX((Game.getDisplayWidth() / 2 - play.getWidth() / 2) + 10);
		play.setY(990);

		add(play);
		add(group);
	}
}
