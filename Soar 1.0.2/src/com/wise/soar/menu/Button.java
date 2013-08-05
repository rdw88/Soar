package com.wise.soar.menu;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.WelcomeScreen;
import com.wise.soar.res.InputHandler;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sound;
import com.wise.soar.res.Sprite;

public class Button extends MenuItem {
	public static final int TYPE_MENU = 0;
	public static final int TYPE_CONTAINER = 1;
	public static final int TYPE_BACK = 2;
	public static final int TYPE_NEXT = 3;
	public static final int TYPE_PREVIOUS = 4;
	public static final int TYPE_LOAD = 5;
	public static final int TYPE_FLASH = 6;

	private Sprite released, pressed;
	//	private final Random random = new Random();

	private ButtonEvent event;

	private String text;

	private int state;
	private boolean enabled = true;

	private int type;
	private boolean waiting;

	private final Sound click = Resource.click;

	public Button(int x, int y, String text) {
		super(x, y);
		this.text = text;

		setType(TYPE_MENU);
	}

	public void render(Canvas canvas) {
		super.render(canvas);

		if (visible)
			if (type == TYPE_LOAD) {
				WelcomeScreen.text.setColor(0xff303030);
				WelcomeScreen.text.setTextSize(32);
				canvas.drawText(text, (x + (width / 2) - (text.length() * 32 / 6)) + 5, y + 48, WelcomeScreen.text);
			} else {
				Game.renderText(canvas, text, (x + (width / 2) - (text.length() * 32 / 6)) + 5, y + 48, 0xff303030, 32);
			}
	}

	public void tick() {
		//	animate();

		visible = sprite != null;

		if (waiting)
			return;

		boolean bounds = InputHandler.getX() > x && InputHandler.getX() < x + width && InputHandler.getY() > y && InputHandler.getY() < y + height;

		if (!InputHandler.isScreenTouched())
			state = 0;

		if (InputHandler.isScreenTouched() && bounds)
			state = 1;

		if (state == 1) {
			sprite = pressed;
		} else {
			sprite = released;
		}

		if (state == 1)
			waitForRelease();
	}

	/*
	 * private void animate() { if (type != Button.TYPE_MENU) return;
	 * 
	 * if (count % 10 == 0) { int rand = random.nextInt(2);
	 * 
	 * if (rand == 0) x += 0.1f; else if (rand == 1) x -= 0.1f;
	 * 
	 * rand = random.nextInt(2);
	 * 
	 * if (rand == 0) y += 0.1f; else if (rand == 1) y -= 0.1f; }
	 * 
	 * count++; }
	 */

	private void waitForRelease() {
		waiting = true;

		new Thread(new Runnable() {
			public void run() {
				while (InputHandler.isScreenTouched()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (InputHandler.getX() > x && InputHandler.getX() < x + width && InputHandler.getY() > y && InputHandler.getY() < y + height) {
					click.play();
					fireEvent();
				}

				waiting = false;
			}
		}).start();
	}

	public void addButtonEvent(ButtonEvent event) {
		this.event = event;
	}

	public void setType(int type) {
		this.type = type;

		if (type == Button.TYPE_CONTAINER) {
			pressed = Resource.container_button_pressed;
			released = Resource.container_button_released;
		} else if (type == Button.TYPE_MENU || type == Button.TYPE_LOAD) {
			released = Resource.buttonReleased;
			pressed = Resource.buttonPressed;
		} else if (type == Button.TYPE_BACK) {
			pressed = Resource.back_pressed;
			released = Resource.back;
		} else if (type == Button.TYPE_NEXT) {
			pressed = Resource.nextPagePressed;
			released = Resource.nextPage;
		} else if (type == Button.TYPE_PREVIOUS) {
			pressed = Resource.previousPagePressed;
			released = Resource.previousPage;
		} else if (type == Button.TYPE_FLASH) {
			pressed = Resource.flash_pressed;
			released = Resource.flash_released;
		}

		sprite = released;

		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	public void fireEvent() {
		if (enabled) {
			event.onEvent();
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setEnabled(boolean b) {
		enabled = b;
	}
}