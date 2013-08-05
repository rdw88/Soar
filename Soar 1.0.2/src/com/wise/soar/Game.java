package com.wise.soar;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;
import com.wise.soar.res.InputHandler;

@SuppressLint("ViewConstructor")
public class Game extends SurfaceView implements Runnable, Callback {
	public static final boolean DEVELOPER_MODE = false;
	public static final int START_TIME = 0;

	private static final int TARGET_FRAME_RATE = 60;
	private static final int TARGET_TICK_RATE = 60;

	public static float widthScale;
	public static float heightScale;
	private static int gameWidth;
	private static int gameHeight;

	private final SurfaceHolder holder = getHolder();

	public final Resources res = getResources();

	public static File directory;

	public static final Paint paint = new Paint(Paint.DITHER_FLAG);

	private static final TextPaint text = new TextPaint();

	private final Typeface fontFace = Typeface.createFromAsset(getContext().getAssets(), "font.TTF");
	private final Typeface face = Typeface.create(fontFace, Typeface.NORMAL);

	//private int tick, fps;

	private Level level;

	private Thread thread;

	private volatile boolean running;

	public static Game instance;

	private boolean visible = true;

	public Game(Context context, int width, int height, int screenW, int screenH) {
		super(context);
		Game.gameWidth = width;
		Game.gameHeight = height;

		widthScale = (float) screenW / width;
		heightScale = (float) screenH / height;

		Game.text.setTypeface(face);
		Game.text.setFlags(Paint.ANTI_ALIAS_FLAG);

		directory = context.getFilesDir();

		text.setTypeface(face);
		text.setFlags(Paint.ANTI_ALIAS_FLAG);

		instance = this;

		Saves.init();
		level = new Level(instance);
		new InputHandler(instance);
	}

	public void resume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void pause() {
		running = false;
	}

	public void run() {
		long previous = System.nanoTime();
		double unprocessed = 0;
		long now = 0;
		//	int frames = 0;
		//	int ticks = 0;
		//	long time = System.currentTimeMillis();
		long lastRenderTime = 0;
		long lastTickTime = 0;

		double timePerRender = 1000000000.0 / TARGET_FRAME_RATE;
		double msPerTick = 1000000000.0 / TARGET_TICK_RATE;

		while (running) {
			if (!visible) {
				rest(100);
				continue;
			}

			now = System.nanoTime();
			unprocessed += (now - previous) / msPerTick;
			previous = now;

			while (unprocessed >= 1) {
				tick();
				lastTickTime = System.nanoTime();
				//	ticks++;
				unprocessed--;
			}

			if (!holder.getSurface().isValid()) {
				continue;
			}

			render();
			lastRenderTime = System.nanoTime();
			//	frames++;

			///	if (System.currentTimeMillis() - time >= 1000) {
			//		time = System.currentTimeMillis();
			//		System.out.println(ticks + " ticks, " + frames + " fps");
			//		frames = 0;
			//		ticks = 0;
			//	}

			while (now - lastRenderTime < timePerRender && now - lastTickTime < msPerTick) {
				Thread.yield();
				now = System.nanoTime();
			}
		}
	}

	private void rest(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void render() {
		Canvas canvas = null;

		try {
			canvas = holder.lockCanvas();

			synchronized (holder) {
				canvas.scale(widthScale, heightScale);

				canvas.drawColor(0xff000000);

				level.render(canvas);

				//renderText(canvas, tick + " ticks", 5, 85, 0xffdddddd, 32);

				//renderText(canvas, fps + " FPS", 5, 125, 0xffdddddd, 32);

				//renderText(canvas, "v1.0 Beta", 5, Game.getDisplayHeight() - 10, 0xff000000, 24);

				//if (Menu.menuIsOpen())
				//renderText(canvas, "created by Ryan Wise", Game.getDisplayWidth() - 220, Game.getDisplayHeight() - 10, 0xff000000, 24);
			}
		} finally {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public static void renderText(Canvas canvas, String text, float x, float y, int color, float size) {
		Game.text.setColor(color);
		Game.text.setTextSize(size);

		canvas.drawText(text, x, y, Game.text);
	}

	public void handleBackButton() {
		level.onBackButtonPressed();
	}

	private void tick() {
		level.tick();
	}

	public void setGameVisible(boolean visible) {
		this.visible = visible;
	}

	public static int getDisplayWidth() {
		return gameWidth;
	}

	public static int getDisplayHeight() {
		return gameHeight;
	}

	public Level getLevel() {
		return level;
	}

	public static void waittill(boolean b) {
		while (!b) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
