package com.wise.soar.particle;

import com.wise.soar.level.Level;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Sprite;

public abstract class AbilityButton extends Particle {
	private boolean enabled = true;
	protected Sprite on, off;

	public AbilityButton(Level level, float x, float y) {
		super(level);
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if (InputHandler.getX() > x && InputHandler.getY() > y && InputHandler.getX() < x + width && InputHandler.getY() < y + height && enabled) {
			onClickEvent();
		}

		float xx = InputHandler.getSecondX();
		float yy = InputHandler.getSecondY();

		if (xx > x && yy > y && xx < x + width && yy < y + height && enabled) {
			onClickEvent();
		}
	}

	public void setEnabled(boolean b) {
		enabled = b;

		sprite = b ? on : off;
	}

	abstract void onClickEvent();

	protected void animate() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public abstract String toString();
}
