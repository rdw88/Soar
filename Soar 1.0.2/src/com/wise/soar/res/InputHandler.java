package com.wise.soar.res;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

import com.wise.soar.Game;

public class InputHandler implements OnTouchListener, OnKeyListener {
	private static float x, y;
	private static float x2, y2;
	private static boolean touched;

	public InputHandler(Game game) {
		game.setOnTouchListener(this);
	}

	public boolean onTouch(final View v, MotionEvent e) {
		x = (float) e.getX(0) / Game.widthScale;
		y = (float) e.getY(0) / Game.heightScale;

		if (e.getActionIndex() == 1) {
			x2 = (float) e.getX(1) / Game.widthScale;
			y2 = (float) e.getY(1) / Game.heightScale;
		}

		if (e.getAction() == MotionEvent.ACTION_DOWN)
			touched = true;
		else if (e.getAction() == MotionEvent.ACTION_UP)
			touched = false;

		return true;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}

	public static boolean isScreenTouched() {
		return touched;
	}

	public static void releaseFinger() {
		touched = false;
	}

	public static float getX() {
		return x;
	}

	public static float getY() {
		return y;
	}

	public static float getSecondX() {
		float xx = x2;
		x2 = -1;
		return xx;
	}

	public static float getSecondY() {
		float yy = y2;
		y2 = -1;
		return yy;
	}
}
