package com.wise.soar.particle;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class Star extends Particle {
	private static final Sprite[] stars = Resource.stars;

	public Star(Level level, float y) {
		super(level);

		sprite = stars[Rand.random.nextInt(3)];
		x = Rand.random.nextInt(Game.getDisplayWidth() - 5);
		this.y = y;

		collidable = false;
	}

	public void tick() {
		if (y > Game.getDisplayHeight())
			dispose();
	}

	public void scroll(float velocity) {
		y += 1.5f;
	}

	public void shift(float velocity) {
		//x += velocity;
	}

	protected void animate() {
	}

	public String toString() {
		return "Star";
	}
}
