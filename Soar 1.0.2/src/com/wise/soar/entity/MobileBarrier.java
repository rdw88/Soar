package com.wise.soar.entity;

import com.wise.soar.level.Level;
import com.wise.soar.res.Rand;
import com.wise.soar.res.Resource;

public class MobileBarrier extends Barrier {
	private float velocity = 1.5f;

	public MobileBarrier(Level level) {
		super(level);
		int rand = Rand.random.nextInt(2);
		if (rand == 0) {
			sprite = Resource.mobileSmall;
			width = 50;
			height = 13;
		} else {
			sprite = Resource.mobileLarge;
			width = 75;
			height = 17;
		}

		int initialDirection = Rand.random.nextInt(2);

		velocity = initialDirection == 0 ? 1.5f : -1.5f;
	}

	public MobileBarrier(Level level, float x, float y) {
		super(level, x, y);
		int rand = Rand.random.nextInt(2);
		if (rand == 0) {
			sprite = Resource.mobileSmall;
			width = 50;
			height = 13;
		} else {
			sprite = Resource.mobileLarge;
			width = 75;
			height = 17;
		}

		int initialDirection = Rand.random.nextInt(2);

		velocity = initialDirection == 0 ? 1.5f : -1.5f;
	}

	public void tick() {
		super.tick();

		if ((x + width > level.getWidth() && velocity > 0) || (x <= 0 && velocity < 0))
			velocity = -velocity;

		x += velocity;
	}

	public String toString() {
		return "Mobile Barrier @ (" + x + ", " + y + ")";
	}
}