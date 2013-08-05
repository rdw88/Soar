package com.wise.soar.particle;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class Flash extends AbilityButton {
	public Flash(Level level, float x, float y) {
		super(level, x, y);

		on = Resource.flash_released;
		off = Resource.flash_pressed;

		sprite = on;

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public String toString() {
		return "Flash Button";
	}

	public void tick() {
		super.tick();

		if (!level.getPlayer().hasShield())
			y = 60;
	}

	void onClickEvent() {
		level.doFlash();
	}
}
