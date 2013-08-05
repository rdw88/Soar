package com.wise.soar.particle;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;

public class Cloud extends Particle {
	private final int rand = Rand.random.nextInt(2);
	private final float speed = 1.5f;

	public Cloud(Level level) {
		super(level);

		int random = Rand.random.nextInt(3);

		if (random == 0)
			sprite = Resource.largeCloud;
		else if (random == 1)
			sprite = Resource.mediumCloud;
		else if (random == 2)
			sprite = Resource.smallCloud;

		if (rand == 0)
			x = -Rand.random.nextInt(200);
		else
			x = Game.getDisplayWidth() + Rand.random.nextInt(100);

		y = -Rand.random.nextInt(100) - Rand.random.nextInt(100);
	}

	public void tick() {
		if (rand == 0) {
			x += speed;

			if (x > Game.getDisplayWidth())
				dispose();
		} else {
			x -= speed;

			if (x < Game.getDisplayWidth())
				dispose();
		}
	}

	public void dispose() {
		level.remove(this);
	}

	protected void animate() {
	}

	public String toString() {
		return "Cloud @ (" + getX() + ", " + getY() + ")";
	}
}