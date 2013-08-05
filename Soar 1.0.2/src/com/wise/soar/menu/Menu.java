package com.wise.soar.menu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

import com.wise.soar.Game;
import com.wise.soar.level.Level;
import com.wise.soar.res.Resource;
import com.wise.soar.res.Sprite;

public abstract class Menu {
	public static final List<Menu> menus = new ArrayList<Menu>();

	protected final List<MenuItem> items = new ArrayList<MenuItem>();

	private Image bg;

	protected int width;
	protected int height;

	protected Level level;

	private Sprite background;

	private boolean open;

	public Menu() {
		width = Game.getDisplayWidth();
		height = Game.getDisplayHeight();

		if (!(this instanceof TitleMenu))
			setBackground();

		struct();
		menus.add(this);
	}

	public Menu(Level level) {
		this.level = level;

		width = Game.getDisplayWidth();
		height = Game.getDisplayHeight();

		if (!(this instanceof TitleMenu))
			setBackground();

		struct();
		menus.add(this);
	}

	protected abstract void struct();

	public void render(Canvas canvas) {
		if (!open)
			return;

		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(canvas);
		}
	}

	public void tick() {
		if (!open)
			return;

		for (int i = 0; i < items.size(); i++) {
			items.get(i).tick();
		}
	}

	protected void setBackground() {
		background = Resource.titleBg;

		bg = new Image(background, 0, 0);
		add(bg);
	}

	public void open() {
		open = true;
	}

	public void close() {
		open = false;
	}

	public boolean isOpen() {
		return open;
	}

	public void add(MenuItem e) {
		items.add(e);
	}

	public void remove(MenuItem e) {
		items.remove(e);
	}

	protected void destroyElements() {
		for (int i = items.size() - 1; i >= 0; i--)
			items.remove(i);
	}

	public static boolean menuIsOpen() {
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).isOpen()) {
				return true;
			}
		}

		return false;
	}
}