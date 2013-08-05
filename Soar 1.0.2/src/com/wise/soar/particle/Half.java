package com.wise.soar.particle;

import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;

public class Half extends AbilityButton {
	public Half(Level level, float x, float y) {
		super(level, x, y);

		on = Resource.flash_released; // change
		off = Resource.flash_pressed; // change

		sprite = on;

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public String toString() {
		return "Half Button";
	}

	void onClickEvent() {
		level.doHalf();
	}
}
