package com.wise.soar.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wise.soar.Game;
import com.wise.soar.entity.Barrier;
import com.wise.soar.entity.PowerUp;
import com.wise.soar.particle.Cloud;
import com.wise.soar.particle.Star;
import com.wise.soar.res.Rand;

public class LevelGen {
	private static final Random random = new Random();

	public static void init(Level level) {
		int height = level.getHeight();

		for (int y = -height; y < 0; y++) {
			if (Rand.random.nextInt(100) == 23) {
				level.add(new Barrier(level, Rand.random.nextInt(5) * 100, y));
			}
		}
	}

	public static void addStars(Level level) {
		if (level.getTickCount() > 3000 && level.getTickCount() <= 3200) {
			for (int y = -110; y < -10; y++) {
				if (Rand.random.nextInt(1000) == 236) {
					level.add(new Star(level, y));
				}
			}
		} else if (level.getTickCount() > 3200 && level.getTickCount() <= 3400) {
			for (int y = -Game.getDisplayHeight(); y < -10; y++) {
				if (Rand.random.nextInt(800) == 236) {
					level.add(new Star(level, y));
				}
			}
		} else if (level.getTickCount() > 3400 && level.getTickCount() <= 3600) {
			for (int y = -Game.getDisplayHeight(); y < -10; y++) {
				if (Rand.random.nextInt(700) == 236) {
					level.add(new Star(level, y));
				}
			}
		} else if (level.getTickCount() > 3600 && level.getTickCount() <= 3800) {
			for (int y = -Game.getDisplayHeight(); y < -10; y++) {
				if (Rand.random.nextInt(600) == 236) {
					level.add(new Star(level, y));
				}
			}
		} else if (level.getTickCount() > 3800 && level.getTickCount() <= 4000) {
			for (int y = -Game.getDisplayHeight(); y < -10; y++) {
				if (Rand.random.nextInt(500) == 236) {
					level.add(new Star(level, y));
				}
			}
		} else if (level.getTickCount() > 4000) {
			for (int y = -Game.getDisplayHeight(); y < -10; y++) {
				if (Rand.random.nextInt(400) == 236) {
					level.add(new Star(level, y));
				}
			}
		}
	}

	public static void generate(Level level, float speed) {
		boolean powerUpAdded = level.hasPower();
		List<Integer> sections = new ArrayList<Integer>();

		int max = 20;
		if (max < 5)
			max = 5;

		for (int y = -550; y < -100; y++) {
			float xFactor = level.getShiftSpeed() * -200;

			int tickCount = level.getTickCount();

			if (tickCount > 300 && tickCount < 3000) {
				if (Rand.random.nextInt(Rand.random.nextInt(40) + 120) == 54) {
					level.add(new Cloud(level));
				}
			}

			int factor = (int) level.getScrollSpeed() * 3;
			if (Rand.random.nextInt(max) > max - factor) {
				int x = (int) ((Rand.random.nextInt(Game.getDisplayWidth() / 3)) + xFactor);
				int sec = Rand.random.nextInt(5) + 1;

				if (sec > 3)
					sec = 3;

				sections.add(sec);
				x *= sec;
				if (x > Game.getDisplayWidth() - 75) {
					x -= Rand.random.nextInt(75) + 75;
				}

				level.add(Barrier.random(level, x, y));
			}

			int r = 0;
			if (level.getScore() > 6000)
				r = 11;
			else
				r = 20;

			if (random.nextInt(r) == 2 && !level.getPlayer().hasPower() && !powerUpAdded && level.getScore() > 800 && (level.getScore() < 14500 || level.getScore() > 15000)) {
				level.add(PowerUp.random(level, Rand.random.nextInt(level.getWidth() - 100), y + 25));
				powerUpAdded = true;
			}

			y += 50;
		}

		int[] used = new int[3];
		for (int i = 0; i < sections.size(); i++) {
			used[sections.get(i) - 1]++;
		}

		for (int y = -550; y < -100; y++) {
			if (used[2] < used[0]) {
				level.add(Barrier.random(level, (int) ((Rand.random.nextInt(Game.getDisplayWidth() / 3)) * 3) + Rand.random.nextInt(Game.getDisplayWidth() / 3 - 75), y));
				used[2]++;
			} else {
				int rr = Rand.random.nextInt(2) + 1;
				level.add(Barrier.random(level, (int) ((Rand.random.nextInt(Game.getDisplayWidth() / 3)) * rr) + Rand.random.nextInt(Game.getDisplayWidth() / 3), y));
				used[rr - 1]++;
			}

			y += 50;
		}

		for (int y = -550; y < -100; y += 150) {
			int ran = Rand.random.nextInt(4);

			if (ran == 1)
				level.add(Barrier.random(level, 5, y));
			else if (ran == 3)
				level.add(new Barrier(level, Game.getDisplayWidth() - 50, y));
		}
	}
}