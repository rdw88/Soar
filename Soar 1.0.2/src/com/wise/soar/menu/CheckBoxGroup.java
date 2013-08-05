package com.wise.soar.menu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public class CheckBoxGroup extends MenuItem {
	private final List<CheckBox> boxes = new ArrayList<CheckBox>();

	private int boxesPerColumn = 5;

	public CheckBoxGroup(int x, int y) {
		super(x, y);
	}

	public void render(Canvas canvas) {
		for (int i = 0; i < boxes.size(); i++)
			boxes.get(i).render(canvas);
	}

	public void tick() {
		for (int i = 0; i < boxes.size(); i++)
			boxes.get(i).tick();
	}

	public void addCheckBox(String text, boolean checked, boolean enabled, ButtonEvent event) {
		int xx = x;

		xx += (400 * (boxes.size() / boxesPerColumn));

		CheckBox box = new CheckBox(text, xx, y + (55 * (boxes.size() % boxesPerColumn)), checked);
		box.setEnabled(enabled);
		box.addEvent(event);
		boxes.add(box);
	}

	public void setBoxesPerColumn(int boxes) {
		boxesPerColumn = boxes;
	}

	public int getBoxesPerColumn() {
		return boxesPerColumn;
	}

	public void setY(int y) {
		super.setY(y);

		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).y = y + (i * 55);
		}
	}

	public int getSize() {
		return boxes.size();
	}

	public class CheckBox {
		private static final int ENABLED_COLOR = 0xff0000ff;
		private static final int NON_ENABLED_COLOR = 0x340000ff;

		private Sprite uncheck, check;

		private ButtonEvent event;

		private boolean checked;
		private boolean enabled;

		private int color;

		private int x;
		private int y;

		private int width, height;

		private String text;

		public CheckBox(String text, int x, int y, boolean checked) {
			this.checked = checked;
			this.x = x;
			this.y = y;
			this.text = text;

			check = Resource.check;
			uncheck = Resource.uncheck;

			width = uncheck.getWidth();
			height = uncheck.getHeight();
		}

		public void render(Canvas canvas) {
			if (checked)
				canvas.drawBitmap(check.image, x, y, Game.paint);
			else
				canvas.drawBitmap(uncheck.image, x, y, Game.paint);

			Game.renderText(canvas, text, x + 75, y + 42, color, 34);
		}

		public void tick() {
			color = enabled ? ENABLED_COLOR : NON_ENABLED_COLOR;

			boolean bounds = InputHandler.getX() > x && InputHandler.getX() < x + width + (text.length() * 23) && InputHandler.getY() > y && InputHandler.getY() < y + height;

			if (InputHandler.isScreenTouched() && bounds && enabled) {
				for (int i = 0; i < boxes.size(); i++) {
					boxes.get(i).setChecked(false);
				}

				checked = true;
				event.onEvent();

				InputHandler.releaseFinger();
			}
		}

		public void addEvent(ButtonEvent event) {
			this.event = event;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public boolean isChecked() {
			return checked;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean b) {
			enabled = b;
		}
	}
}