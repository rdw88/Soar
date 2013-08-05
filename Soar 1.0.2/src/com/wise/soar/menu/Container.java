package com.wise.soar.menu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public abstract class Container {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_ACHIEVEMENT = 1;
	public static final int TYPE_LARGE = 2;

	protected Sprite container;

	protected Level level;

	protected final List<MenuItem> items = new ArrayList<MenuItem>();

	private String title = "";

	private Rect rect = new Rect();
	private int type;

	protected boolean renderItems;
	protected boolean open;

	private int x;
	private int y;

	private int color = 0xff0000ff;

	public Container() {
		setType(TYPE_NORMAL);
		struct();
	}

	public Container(Level level) {
		this.level = level;
		setType(TYPE_NORMAL);
		struct();
	}

	protected abstract void struct();

	public void render(Canvas canvas) {
		if (!open)
			return;

		canvas.drawBitmap(container.image, null, rect, Game.paint);

		Game.renderText(canvas, title, x + (container.getWidth() / 2) - (title.length() * 8), y + 50, color, 36);

		for (int i = 0; i < items.size(); i++)
			if (renderItems)
				items.get(i).render(canvas);
	}

	public void tick() {
		if (!open)
			return;

		for (int i = 0; i < items.size(); i++)
			items.get(i).tick();

		rect.left = x;
		rect.top = y;
		rect.right = x + container.getWidth();
		rect.bottom = y + container.getHeight();
	}

	public void open() {
		open = true;
		doOpenAnimation();
	}

	public void close() {
		doCloseAnimation();
	}

	private void doOpenAnimation() {
		new Thread(new Runnable() {
			public void run() {
				if (type == TYPE_NORMAL) {
					while (x < (Game.getDisplayWidth() - container.getWidth()) / 2) {
						x += 2;

						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					while (y > Game.getDisplayHeight() - 200) {
						y -= 2;

						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				renderItems = true;
			}
		}).start();
	}

	private void doCloseAnimation() {
		new Thread(new Runnable() {
			public void run() {
				renderItems = false;
				if (type == TYPE_NORMAL) {
					while (x > -container.getWidth()) {
						x--;

						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					while (y < Game.getDisplayHeight()) {
						y++;

						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				open = false;
			}
		}).start();
	}

	public void add(MenuItem item) {
		items.add(item);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getX() {
		if (type == TYPE_NORMAL)
			return (Game.getDisplayWidth() - container.getWidth()) / 2;

		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOpen() {
		return open;
	}

	public void setTitleColor(int color) {
		this.color = color;
	}

	public void setType(int type) {
		if (type == TYPE_NORMAL) {
			container = Resource.container;
			x = -container.getWidth();
			y = (Game.getDisplayHeight() / 2) - (container.getHeight() / 2);
		} else if (type == TYPE_ACHIEVEMENT) {
			container = Resource.achievement_container;
			x = (Game.getDisplayWidth() / 2) - (container.getWidth() / 2);
			y = Game.getDisplayHeight() + 10;
		} else if (type == TYPE_LARGE) {
			container = Resource.container_large;
		}

		this.type = type;
	}
}