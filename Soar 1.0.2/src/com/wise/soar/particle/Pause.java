package com.wise.soar.particle;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;

public class Pause extends Particle {
	public Pause(Level level) {
		super(level);
		sprite = Resource.pause;

		x = Game.getDisplayWidth() - sprite.getWidth() - 5;
		y = Game.getDisplayHeight() - sprite.getHeight() - 5;
	}

	public void tick() {
		if (InputHandler.getX() > x && InputHandler.getY() > y) {
			level.pause();
		}
	}

	protected void animate() {
	}

	public String toString() {
		return "Pause Button";
	}
}