package com.wise.soar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.wise.soar.menu.Button;
import com.wise.soar.menu.Image;
import com.wise.soar.res.Resource;

@SuppressLint("ViewConstructor")
public class WelcomeScreen extends SurfaceView implements Runnable, Callback {
	private Thread thread;
	private boolean running;

	private int width, height;

	private final SurfaceHolder holder = getHolder();
	public static final Paint paint = new Paint(Paint.DITHER_FLAG);
	public final Resources res = getResources();

	private String load = "Loading";
	private int loadColor = 0xdd343434;
	private int count;

	private Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.titlebg);
	//	private Bitmap button = BitmapFactory.decodeResource(getResources(), R.drawable.buttonreleased);
	//	private Bitmap title = BitmapFactory.decodeResource(getResources(), R.drawable.title);

	private Button[] buttons = new Button[5];
	private int[] buttonDests = new int[5];
	private Image title;

	private final Typeface fontFace = Typeface.createFromAsset(getContext().getAssets(), "font.TTF");
	private final Typeface face = Typeface.create(fontFace, Typeface.NORMAL);

	public static final TextPaint text = new TextPaint();

	public static WelcomeScreen instance;

	private LoadingBar bar;

	private boolean transition = true;

	public WelcomeScreen(Context context, int width, int height) {
		super(context);
		this.width = width;
		this.height = height;

		text.setTypeface(face);
		text.setFlags(Paint.ANTI_ALIAS_FLAG);

		Bitmap b = image;
		float scaleWidth = ((float) width) / b.getWidth();
		float scaleHeight = ((float) height) / b.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		image = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);

		instance = this;
		bar = new LoadingBar(width, height);

		start();
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			tick();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (!holder.getSurface().isValid()) {
				continue;
			}

			render();
		}
	}

	private void render() {
		Canvas canvas = holder.lockCanvas();

		canvas.drawBitmap(image, 0, 0, paint);
		text.setColor(loadColor);
		text.setTextSize(96);
		canvas.drawText(load, width / 2 - 150, height / 2 + 350, text);
		bar.render(canvas);

		if (title != null) {
			for (int i = 0; i < buttons.length; i++)
				buttons[i].render(canvas);

			title.render(canvas);
		}

		holder.unlockCanvasAndPost(canvas);
	}

	private void tick() {
		if (count == 15)
			load = "Loading.";
		else if (count == 30)
			load = "Loading..";
		else if (count == 45)
			load = "Loading...";
		else if (count == 60) {
			load = "Loading";
			count = 0;
		}

		bar.tick();

		if (bar.getRemainingResources() < 2) {
			doTransition();
		}

		count++;
	}

	private void doTransition() {
		int alpha = (loadColor >> 24) & 0xff;

		if (alpha == 0xdd) {
			buttons[0] = new Button(width + 5, height / 2 - 190, "Play!  ");
			buttons[1] = new Button(-buttons[0].getWidth() - 5, height / 2 - 60, "Options   ");
			buttons[2] = new Button(width + 5, height / 2 + 70, "Statistics  ");
			buttons[3] = new Button(-buttons[0].getWidth() - 5, height / 2 + 160, "Achievements      ");
			buttons[4] = new Button(width + 5, height / 2 + 300, "Help  ");

			for (int i = 0; i < buttons.length; i++)
				buttons[i].setType(Button.TYPE_LOAD);

			buttonDests[0] = width / 2 - 230;
			buttonDests[1] = width / 2 - 20;
			buttonDests[2] = width / 2 + 50;
			buttonDests[3] = width / 2 - 170;
			buttonDests[4] = width / 2 + 40;

			title = new Image(Resource.title, 0, 0);
			title.setX((width / 2) - (title.getWidth() / 2));
			title.setY(-title.getHeight() - 20);
		}

		if (alpha != 0) {
			int color = loadColor & 0xffffff;
			alpha -= 5;

			if (alpha < 5)
				alpha = 0;

			int out = (alpha << 24) | color;
			loadColor = out;
		}

		for (int i = 0; i < buttons.length; i++) {
			if (i % 2 == 0) {
				buttons[i].setX(buttons[i].getX() - 15);

				if (buttons[i].getX() < buttonDests[i])
					buttons[i].setX(buttonDests[i]);
			} else {
				buttons[i].setX(buttons[i].getX() + 15);

				if (buttons[i].getX() > buttonDests[i])
					buttons[i].setX(buttonDests[i]);
			}
		}

		title.setY(title.getY() + 10);

		if (title.getY() >= 150)
			title.setY(150);

		if (buttons[0].getX() == buttonDests[0] && buttons[1].getX() == buttonDests[1] && buttons[2].getX() == buttonDests[2] && buttons[3].getX() == buttonDests[3]
				&& buttons[4].getX() == buttonDests[4] && title.getY() == 150)
			transition = false;
	}

	public boolean inTransition() {
		return transition;
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
