package com.wise.soar.menu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.wise.soar.entity.Player;
import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;

public class TitleMenu extends Menu {
	private Button play, options, stats, achievements, help;
	private Image title;

	private List<Image> balloons = new ArrayList<Image>();

	private Level level;

	private int count;

	private SubMenu sub;
	private Menu instance;

	private int color;

	public TitleMenu(Level level) {
		this.level = level;

		instance = this;

		sub = new StatisticsMenu(instance, level);
		sub.close();
	}

	protected void struct() {
		setBackground();

		title = new Image(Resource.title, 0, 150);
		title.x = (width / 2) - (title.getWidth() / 2);

		play = new Button(width / 2 - 230, height / 2 - 190, "Play!  ");
		play.setType(Button.TYPE_MENU);
		play.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				sub = new BalloonMenu(instance, level);
				sub.open();
				close();
			}
		});

		options = new Button(width / 2 - 20, height / 2 - 60, "Options   ");
		options.setType(Button.TYPE_MENU);
		options.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				sub = new OptionsMenu(instance, level);
				sub.open();
				close();
			}
		});

		stats = new Button(width / 2 + 50, height / 2 + 70, "Statistics  ");
		stats.setType(Button.TYPE_MENU);
		stats.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				sub = new StatisticsMenu(instance, level);
				sub.open();
				close();
			}
		});

		achievements = new Button(width / 2 - 170, height / 2 + 160, "Achievements      ");
		achievements.setType(Button.TYPE_MENU);
		achievements.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				sub = new AchievementMenu(instance, level);
				sub.open();
				close();
			}
		});

		help = new Button(width / 2 + 40, height / 2 + 300, "Help  ");
		help.setType(Button.TYPE_MENU);
		help.addButtonEvent(new ButtonEvent() {
			public void onEvent() {
				sub = new HelpMenu(instance, level);
				sub.open();
				close();
			}
		});

		add(play);
		add(options);
		add(stats);
		add(achievements);
		add(help);
		add(title);
	}

	public void render(Canvas canvas) {
		super.render(canvas);

		//Game.renderText(canvas, "BETA", title.x + title.getWidth() - 60, title.y + title.getHeight() + 25, color, 26);

		sub.render(canvas);
	}

	public void tick() {
		super.tick();

		int alpha = (color >> 24) & 0xff;
		int clr = color & 0xffffff;
		alpha += 4;
		if (alpha > 0xaa)
			alpha = 0xaa;
		color = (alpha << 24) | clr;

		if (count % 100 == 0) {
			int rand = Rand.random.nextInt(11);
			Image image = null;

			if (rand == 0)
				image = new Image(Resource.balloon, Rand.random.nextInt(width - 50), height + 8);
			else if (rand == 1)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_BLUE, Player.COLOR_FADE_BLUE), Rand.random.nextInt(width - 50), height);
			else if (rand == 2)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_GOLD, Player.COLOR_FADE_GOLD), Rand.random.nextInt(width - 50), height);
			else if (rand == 3)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_GREEN, Player.COLOR_FADE_GREEN), Rand.random.nextInt(width - 50), height);
			else if (rand == 4)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_PINK, Player.COLOR_FADE_PINK), Rand.random.nextInt(width - 50), height);
			else if (rand == 5)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_PURPLE, Player.COLOR_FADE_PURPLE), Rand.random.nextInt(width - 50), height);
			else if (rand == 6)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_ORANGE, Player.COLOR_FADE_ORANGE), Rand.random.nextInt(width - 50), height);
			else if (rand == 7)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_LIGHT_BLUE, Player.COLOR_FADE_LIGHT_BLUE), Rand.random.nextInt(width - 50), height);
			else if (rand == 8)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_LIGHT_RED, Player.COLOR_FADE_LIGHT_RED), Rand.random.nextInt(width - 50), height);
			else if (rand == 9)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_LIGHT_GREEN, Player.COLOR_FADE_LIGHT_GREEN), Rand.random.nextInt(width - 50), height);
			else if (rand == 10)
				image = new Image(Resource.balloonColorSwitch(Resource.balloon, Player.COLOR_LIGHT_PINK, Player.COLOR_FADE_LIGHT_PINK), Rand.random.nextInt(width - 50), height);

			balloons.add(image);
			items.add(1, image);
		}

		for (int i = 0; i < balloons.size(); i++) {
			Image balloon = balloons.get(i);

			balloon.setY(balloon.getY() - 1);

			if (balloon.getY() + balloon.getHeight() < 0) {
				balloons.remove(i);
				items.remove(balloon);
			}
		}

		count++;
		sub.tick();
	}

	public void open() {
		color = 0;
		super.open();
	}

	public void onBackButtonPressed() {
		if (sub.isOpen())
			sub.goBack();
	}
}