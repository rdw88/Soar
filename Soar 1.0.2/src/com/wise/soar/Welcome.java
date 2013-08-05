package com.wise.soar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class Welcome extends Activity {
	private int width, height;
	private Game game;
	public static Activity activity;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		activity = this;

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		game = new Game(this, 800, 1205, width, height);
		setContentView(game);
		game.resume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			game.handleBackButton();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	protected void onResume() {
		super.onResume();
		game.setGameVisible(true);
	}

	protected void onPause() {
		game.getLevel().pause();
		game.setGameVisible(false);
		super.onPause();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
