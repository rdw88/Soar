package com.wise.soar.level;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.RenderableObject;
import com.wise.soar.achievements.Achievement;
import com.wise.soar.achievements.AllComplete;
import com.wise.soar.achievements.EveryPowerUp;
import com.wise.soar.achievements.FourPowerDowns;
import com.wise.soar.achievements.HighScore;
import com.wise.soar.achievements.IterableAchievement;
import com.wise.soar.achievements.NoPowerUps;
import com.wise.soar.achievements.PlayCount;
import com.wise.soar.achievements.PowerUpCount;
import com.wise.soar.achievements.ScoreMilestones;
import com.wise.soar.entity.Barrier;
import com.wise.soar.entity.Entity;
import com.wise.soar.entity.MobileBarrier;
import com.wise.soar.entity.Player;
import com.wise.soar.entity.PowerUp;
import com.wise.soar.menu.AchievementNotifier;
import com.wise.soar.menu.BalloonMenu;
import com.wise.soar.menu.Container;
import com.wise.soar.menu.GameOver;
import com.wise.soar.menu.Menu;
import com.wise.soar.menu.PauseMenu;
import com.wise.soar.menu.TitleMenu;
import com.wise.soar.particle.Flash;
import com.wise.soar.particle.Particle;
import com.wise.soar.particle.Pause;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sound;

public class Level implements RenderableObject {
	public Achievement highScore;
	public Achievement fivePowerDowns;
	public Achievement allComplete;
	public Achievement noPowerUps;
	public Achievement everyPowerUp;
	public IterableAchievement playCount;
	public IterableAchievement powerUpCount;
	public IterableAchievement scoreMilestones;

	private int width, height;
	private Game game;
	private Player player;
	private Level instance;
	private Tutorial tutorial;

	private TitleMenu menu;
	private Container container;
	private AchievementNotifier notifier;
	private Flash flash;
	//private Half half;

	private final List<Entity> entities = new ArrayList<Entity>();
	private final List<Particle> particles = new ArrayList<Particle>();
	private final List<Achievement> achievements = new ArrayList<Achievement>();

	private Background background;

	private float scrollSpeed;
	private float shiftSpeed;

	private int tickCount;
	private int score;
	private int generationCount;
	private int countdown = 3;
	private int multiplier = 1;
	private int powerCount;
	private int powerUpsCreated;
	private int flashCooldown;
	private int starCount;
	private int extraLifeColor = 0xff00AA3E;
	private int add = 0x11;

	private boolean gameEnded;
	private boolean paused;

	private long gameLength;

	private Sound lightning;

	public Level(Game game) {
		this.game = game;
		menu = new TitleMenu(this);
		menu.open();

		width = Game.getDisplayWidth();
		height = Game.getDisplayHeight();

		player = new Player(this, BalloonMenu.FINAL_COLOR);
		initAchievements();

		background = new Background(this);
		tutorial = new Tutorial(this);
		instance = this;
	}

	public void init() {
		dispose();

		add(new Pause(this));

		player = new Player(this, BalloonMenu.FINAL_COLOR);
		player.registerType(BalloonMenu.FINAL_TYPE);
		add(player);

		for (int i = 0; i < achievements.size(); i++)
			achievements.get(i).init();

		if (Game.DEVELOPER_MODE) {
			player.setInvincible(true);
			setTime(Game.START_TIME);
		}

		background.init();

		scrollSpeed = 3;
		shiftSpeed = 0;
		tickCount = 0;
		score = 0;
		generationCount = 0;
		powerCount = 0;
		countdown = 3;
		gameEnded = false;
		paused = false;
		gameLength = 0;
		powerUpsCreated = 0;
		flashCooldown = 0;
		starCount = 0;
		lightning = Resource.lightning;

		tutorial.start();

		resume();

		if (!tutorial.inTutorial())
			LevelGen.init(this);
	}

	public void initAchievements() {
		fivePowerDowns = new FourPowerDowns(this);
		highScore = new HighScore(this);
		allComplete = new AllComplete(this);
		noPowerUps = new NoPowerUps(this);
		playCount = new PlayCount(this);
		powerUpCount = new PowerUpCount(this);
		scoreMilestones = new ScoreMilestones(this);
		everyPowerUp = new EveryPowerUp(this);

		achievements.add(highScore);
		achievements.add(fivePowerDowns);
		achievements.add(noPowerUps);
		achievements.add(playCount);
		achievements.add(powerUpCount);
		achievements.add(scoreMilestones);
		achievements.add(everyPowerUp);
		achievements.add(allComplete);

		for (int i = 0; i < achievements.size(); i++)
			achievements.get(i).init();
	}

	public void render(Canvas canvas) {
		if (Menu.menuIsOpen()) {
			menu.render(canvas);
			return;
		}

		background.render(canvas);

		if (tutorial.inTutorial())
			tutorial.render(canvas);

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(canvas);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(canvas);
		}

		if (container != null)
			container.render(canvas);

		if (flash != null) {
			flash.render(canvas);

			if (!flash.isEnabled()) {
				int diff = 11;

				if (((flashCooldown - tickCount) / 60) < 10)
					diff = 25;
				else if (((flashCooldown - tickCount) / 60) < 20)
					diff = 20;

				Game.renderText(canvas, "" + ((flashCooldown - tickCount) / 60), flash.getX() + diff, flash.getY() + 56, 0xffff0000, 54);
			}
		}

		if (notifier != null)
			notifier.render(canvas);

		if (tickCount < 180 && container == null && !tutorial.inTutorial())
			Game.renderText(canvas, "" + countdown, width / 2 - 15, height / 2, 0xffff0000, 96);

		Game.renderText(canvas, "Score: " + score, 8, 45, 0xffef2323, 40);

		if (player.hasShield() && !tutorial.inTutorial())
			Game.renderText(canvas, "Extra-Life", 10, 78, extraLifeColor, 30);
	}

	public void tick() {
		menu.tick();

		if (Menu.menuIsOpen())
			return;

		if (container != null)
			container.tick();

		if (notifier != null)
			notifier.tick();

		for (int i = 0; i < achievements.size(); i++) {
			achievements.get(i).tick();
		}

		if (paused)
			return;

		if (tutorial.inTutorial()) {
			tutorial.tick();

			for (int i = 0; i < particles.size(); i++) {
				Particle p = particles.get(i);
				p.tick();

				if (!(p instanceof Pause))
					p.scroll(scrollSpeed);
			}

			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.scroll(scrollSpeed);

				if (!(e instanceof PowerUp))
					e.shift(shiftSpeed);

				e.tick();
			}

			return;
		}

		background.tick();

		if (flash != null) {
			if (!flash.isEnabled() && flashCooldown <= tickCount)
				flash.setEnabled(true);

			flash.tick();
		}

		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.tick();

			if (tickCount > 180 && !(p instanceof Pause))
				p.scroll(scrollSpeed);
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (tickCount > 180)
				e.scroll(scrollSpeed);

			if (!(e instanceof PowerUp))
				e.shift(shiftSpeed);

			e.tick();
		}

		if (tickCount % 8 == 0) {
			int green = (extraLifeColor >> 8) & 0xff;

			if (green == 0xcc || green == 0x88)
				add = -add;

			green += add;
			int clr = (((extraLifeColor >> 16) & 0xffff) << 16) | (green << 8) | (extraLifeColor & 0xff);
			extraLifeColor = clr;
		}

		if (container != null && !container.isOpen()) {
			container = null;
		}

		if (tickCount < 180) {
			if (tickCount % 60 == 0 && tickCount != 0) {
				countdown--;
			}
		}

		scrollLogic();
	}

	private void scrollLogic() {
		if (tickCount % 1500 == 0 && scrollSpeed < 8 && tickCount != 0)
			scrollSpeed++;

		if (tickCount >= 6000) {
			int mod = tickCount % 3000;

			if (mod >= 0 && mod < 100)
				shiftSpeed += .01f;

			if (mod >= 500 && mod < 600)
				shiftSpeed -= .02f;

			if (mod >= 1000 && mod < 1100)
				shiftSpeed += .02f;

			if (mod >= 1400 && mod < 1500)
				shiftSpeed -= .01f;
		}

		if (shiftSpeed < 0.001f && shiftSpeed > -0.001f)
			shiftSpeed = 0;

		if ((scrollSpeed * generationCount) > 500) {
			LevelGen.generate(this, scrollSpeed);
			generationCount = 0;
		}

		if ((scrollSpeed * starCount) > 100) {
			LevelGen.addStars(this);
			starCount = 0;
		}

		generationCount++;
		starCount++;
		tickCount++;

		if (!gameEnded)
			gameLength++;

		if (tickCount > 180)
			score += multiplier;
	}

	public void createAchievementNotifier(final String[] message) {
		new Thread(new Runnable() {
			public void run() {
				if (notifier != null && notifier.isOpen()) {
					while (true) {
						if (!notifier.isOpen()) {
							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break;
						}

						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				notifier = new AchievementNotifier(message);
				notifier.open();

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				notifier.close();
			}
		}).start();
	}

	public void add(RenderableObject obj) {
		if (obj instanceof PowerUp && ((PowerUp) obj).isGood())
			powerUpsCreated++;

		if (obj instanceof Entity) {
			Entity e = (Entity) obj;
			entities.add(e);
		} else if (obj instanceof Particle) {
			Particle p = (Particle) obj;
			particles.add(p);
		}
	}

	public void remove(RenderableObject obj) {
		if (obj instanceof Entity)
			entities.remove(obj);
		else if (obj instanceof Particle)
			particles.remove(obj);
	}

	public void pause() {
		if (paused || tutorial.inTutorial())
			return;

		paused = true;

		container = new PauseMenu(this);
		container.open();
	}

	public void resume() {
		if (!paused)
			return;

		container.close();
		paused = false;
	}

	public void goToMainMenu() {
		if (!gameEnded) {
			Saves.setTimePlayed(gameLength);

			if (gameLength > Saves.LONGEST_RUN)
				Saves.setLongestRun(gameLength);

			Saves.setPowerUpCount(powerCount);

			if (score > Saves.HIGH_SCORE)
				Saves.setHighScore(score + 1);

			Saves.writeCurrentStats();
		}

		menu.open();
	}

	public void onBackButtonPressed() {
		if (Menu.menuIsOpen()) {
			menu.onBackButtonPressed();
		}
	}

	public void endGame() {
		if (gameEnded)
			return;

		if (player.hasShield()) {
			new Thread(new Runnable() {
				public void run() {
					player.pop();
					paused = true;

					while (true) {
						boolean finished = false;

						for (int i = 0; i < entities.size(); i++) {
							Entity e = entities.get(i);

							if (e instanceof Barrier || e instanceof MobileBarrier || e instanceof PowerUp) {
								finished = true;
								e.scroll(-2);

								if (e.getY() < -50) {
									entities.remove(e);
								}
							}
						}

						if (!finished)
							break;

						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					paused = false;
				}
			}).start();

			return;
		}

		player.pop();

		if (tutorial.inTutorial()) {
			new Thread(new Runnable() {
				public void run() {
					while (player.getY() < Game.getDisplayHeight()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					remove(player);
					player = new Player(instance, BalloonMenu.FINAL_COLOR);
					player.registerType(BalloonMenu.FINAL_TYPE);
					add(player);
				}
			}).start();

			return;
		}

		new Thread(new Runnable() {
			public void run() {
				container = new GameOver(instance);

				while (!player.isPopped()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (container == null)
					container = new GameOver(instance);

				container.open();
			}
		}).start();

		gameEnded = true;

		InputHandler.releaseFinger();

		Saves.setDeaths(1);
		Saves.setTimePlayed(gameLength);
		Saves.setPowerUpCount(powerCount);

		if (score > Saves.HIGH_SCORE)
			Saves.setHighScore(score + 1);

		if (gameLength > Saves.LONGEST_RUN)
			Saves.setLongestRun(gameLength);

		Saves.writeCurrentStats();
	}

	public void dispose() {
		for (int i = entities.size() - 1; i >= 0; i--) {
			entities.remove(i);
		}

		for (int i = particles.size() - 1; i >= 0; i--) {
			particles.remove(i);
		}
	}

	public void setTime(int i) {
		tickCount = i;
		if (i <= 7500)
			scrollSpeed = 2 + (i / 1500);
		if (i > 4000)
			shiftSpeed = 3;
	}

	public void inFlashMode(boolean f) {
		if (f) {
			int y = 95;

			if (!player.hasShield())
				y = 60;

			flash = new Flash(this, 5, y);
		} else
			flash = null;
	}

	/*
	 * public void inHalfMode(boolean f) { if (f) { if (flash == null) { half = new Half(this, 5, 110); } else { half = new Half(this, 5, 160); } } else { half = null; } }
	 */

	public void doFlash() {
		player.setInvincible(true);
		flashCooldown = tickCount + (60 * Player.FLASH_COOLDOWN);
		flash.setEnabled(false);
		lightning.play();

		new Thread(new Runnable() {
			public void run() {
				for (int k = 0; k < 4; k++) {
					for (int i = 0; i < entities.size(); i++) {
						Entity e = entities.get(i);

						if (e instanceof Barrier) {
							Barrier b = (Barrier) e;
							b.flash();
						}
					}

					try {
						Thread.sleep(55);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}

				for (int i = entities.size() - 1; i >= 0; i--) {
					if (entities.get(i) instanceof Barrier) {
						entities.remove(i);
					}
				}

				player.setInvincible(false);
			}
		}).start();
	}

	public void doHalf() {

	}

	public void addScore(int amount) {
		score += amount;
	}

	public void removeScore(int amount) {
		score -= amount;
	}

	public boolean hasPower() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof PowerUp)
				return true;
		}

		return false;
	}

	public void setMultiplier(int mult) {
		if (mult < 1) {
			multiplier = 1;
			return;
		}

		multiplier = mult;
	}

	public int getTickCount() {
		return tickCount;
	}

	public int getScore() {
		return score;
	}

	public void setScrollSpeed(int increment) {
		scrollSpeed += increment;
	}

	public float getScrollSpeed() {
		return scrollSpeed;
	}

	public void setShiftSpeed(float shiftSpeed) {
		this.shiftSpeed = shiftSpeed;
	}

	public float getShiftSpeed() {
		return shiftSpeed;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Game getGame() {
		return game;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void addPowerUpCount() {
		powerCount++;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public int getPowerCount() {
		return powerCount;
	}

	public int getPowerUpsCreated() {
		return powerUpsCreated;
	}

	public boolean isGameOver() {
		return gameEnded;
	}

	public boolean isInSpace() {
		return background.getY() > Game.getDisplayHeight();
	}

	public Background getBackground() {
		return background;
	}

	public boolean isPaused() {
		return paused;
	}

	public Tutorial getTutorial() {
		return tutorial;
	}
}