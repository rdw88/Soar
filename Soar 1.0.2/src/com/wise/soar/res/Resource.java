package com.wise.soar.res;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.wise.soar.Game;
import com.wise.soar.R;
import com.wise.soar.entity.Player;
import com.wise.soar.level.Level;
import com.wise.soar.level.Saves;
import com.wise.soar.menu.BalloonMenu;

public class Resource {
	public static final int RESOURCE_COUNT = 130;

	public static Sprite background;
	public static Sprite titleBg;
	public static Sprite balloon;
	public static Sprite balloon_small;
	public static Sprite balloon_large;
	public static Sprite barrierSmall;
	public static Sprite barrierLarge;
	public static Sprite mobileSmall;
	public static Sprite mobileLarge;
	public static Sprite pause;
	public static Sprite largeCloud;
	public static Sprite mediumCloud;
	public static Sprite smallCloud;
	public static Sprite title;
	public static Sprite container;
	public static Sprite buttonPressed;
	public static Sprite buttonReleased;
	public static Sprite container_button_pressed;
	public static Sprite container_button_released;
	public static Sprite back;
	public static Sprite achievement_container;
	public static Sprite nextPage;
	public static Sprite previousPage;
	public static Sprite container_large;
	public static Sprite back_pressed;
	public static Sprite nextPagePressed;
	public static Sprite previousPagePressed;

	public static Sprite color_box;
	public static Sprite check;
	public static Sprite uncheck;
	public static Sprite flash_pressed;
	public static Sprite flash_released;
	public static Sprite barrier_flash_large;
	public static Sprite barrier_flash_small;

	public static Sprite[] powerLarge = new Sprite[12];
	public static Sprite[] powerSmall = new Sprite[12];
	public static Sprite[] speedDown = new Sprite[12];
	public static Sprite[] speedUp = new Sprite[12];
	public static Sprite[] scoreUp = new Sprite[12];
	public static Sprite[] scoreDown = new Sprite[12];
	public static Sprite[] x2mult = new Sprite[10];
	public static Sprite[] x4mult = new Sprite[10];
	public static Sprite[] pop = new Sprite[18];
	public static Sprite[] pop_large = new Sprite[18];
	public static Sprite[] pop_small = new Sprite[18];
	public static Sprite[] stars = new Sprite[3];

	public static Sound soundPowerUp;
	public static Sound soundPop;
	public static Sound lightning;
	public static Sound click;

	public static void init() {
		titleBg = new Sprite(R.drawable.titlebg);
		balloon = new Sprite(R.drawable.balloon);
		balloon_small = new Sprite(R.drawable.balloon_small);
		balloon_large = new Sprite(R.drawable.balloon_large);
		title = new Sprite(R.drawable.title);
		buttonPressed = new Sprite(R.drawable.buttonpressed);
		buttonReleased = new Sprite(R.drawable.buttonreleased);
		container_button_pressed = new Sprite(R.drawable.container_button_pressed);
		container_button_released = new Sprite(R.drawable.container_button_released);
		back = new Sprite(R.drawable.back);
		check = new Sprite(R.drawable.checked);
		uncheck = new Sprite(R.drawable.unchecked);
		nextPage = new Sprite(R.drawable.next_page);
		previousPage = new Sprite(R.drawable.previous_page);
		container_large = new Sprite(R.drawable.container_large);
		color_box = new Sprite(R.drawable.color_box);
		flash_pressed = new Sprite(R.drawable.flash, 75, 0, 75, 75);
		flash_released = new Sprite(R.drawable.flash, 0, 0, 75, 75);
		largeCloud = new Sprite(R.drawable.cloud1);
		mediumCloud = new Sprite(R.drawable.cloud2);
		smallCloud = new Sprite(R.drawable.cloud3);
		pause = new Sprite(R.drawable.pause);
		container = new Sprite(R.drawable.container);
		barrierSmall = new Sprite(R.drawable.platformsmallblue);
		barrierLarge = new Sprite(R.drawable.platformlargeblue);
		background = new Sprite(R.drawable.background);
		click = new Sound(R.raw.button);
		back_pressed = new Sprite(R.drawable.back_pressed);
		nextPagePressed = new Sprite(R.drawable.next_page_pressed);
		previousPagePressed = new Sprite(R.drawable.previous_page_pressed);

		for (int i = 0; i < pop.length; i++) {
			pop[i] = new Sprite(R.drawable.pop, i * 55, 0, 55, 137);
			pop_large[i] = new Sprite(R.drawable.pop_large, i * 100, 0, 100, 250);
			pop_small[i] = new Sprite(R.drawable.pop_small, i * 28, 0, 28, 69);
		}

		for (int i = 0; i < x2mult.length; i++) {
			x2mult[i] = new Sprite(R.drawable.powersprite, i * 50, 300, 50, 50);
			x4mult[i] = new Sprite(R.drawable.powersprite, i * 50, 350, 50, 50);
		}

		for (int i = 0; i < powerLarge.length; i++) {
			speedDown[i] = new Sprite(R.drawable.powersprite, i * 50, 0, 50, 50);
			speedUp[i] = new Sprite(R.drawable.powersprite, i * 50, 50, 50, 50);
			powerSmall[i] = new Sprite(R.drawable.powersprite, i * 50, 100, 50, 50);
			powerLarge[i] = new Sprite(R.drawable.powersprite, i * 50, 150, 50, 50);
			scoreUp[i] = new Sprite(R.drawable.powersprite, i * 50, 200, 50, 50);
			scoreDown[i] = new Sprite(R.drawable.powersprite, i * 50, 250, 50, 50);
		}

		mobileSmall = new Sprite(R.drawable.platformsmallyellow);
		mobileLarge = new Sprite(R.drawable.platformlargeyellow);
		achievement_container = new Sprite(R.drawable.achievement);
		barrier_flash_large = new Sprite(R.drawable.platformlargeflash);
		barrier_flash_small = new Sprite(R.drawable.platformsmallflash);

		soundPowerUp = new Sound(R.raw.powerup);
		soundPop = new Sound(R.raw.pop);
		lightning = new Sound(R.raw.lightning);

		for (int i = 0; i < stars.length; i++)
			stars[i] = new Sprite(R.drawable.stars, i * 4, 0, 4, 4);
	}

	public static Bitmap getImage(Resources res, int id) {
		return BitmapFactory.decodeResource(res, id);
	}

	public static Bitmap scaleBitmapToWidth(Bitmap bitmap) {
		float xScale = ((float) Game.getDisplayWidth()) / bitmap.getWidth();
		return Resource.scaleBitmap(bitmap, xScale, 1);
	}

	public static Bitmap scaleBitmapToHeight(Bitmap bitmap) {
		float yScale = ((float) Game.getDisplayHeight()) / bitmap.getHeight();
		return Resource.scaleBitmap(bitmap, 1, yScale);
	}

	public static Bitmap scaleBitmapToScreen(Bitmap bitmap) {
		float xScale = ((float) Game.getDisplayWidth()) / bitmap.getWidth();
		float yScale = ((float) Game.getDisplayHeight()) / bitmap.getHeight();

		if (xScale == 1 && yScale == 1)
			return bitmap;

		return Resource.scaleBitmap(bitmap, xScale, yScale);
	}

	private static Bitmap scaleBitmap(Bitmap bitmap, float xScale, float yScale) {
		Matrix matrix = new Matrix();
		matrix.postScale(xScale, yScale);

		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	}

	public static String[] readFromFile(File file) {
		List<String> list = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String message;
			while ((message = reader.readLine()) != null) {
				list.add(message);
			}

			reader.close();
		} catch (IOException e) {
			System.err.println("Failed to read file!");
			e.printStackTrace();
		}

		String[] values = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i);
		}

		return values;
	}

	public static void writeToFile(File file, String[] values) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

			for (int i = 0; i < values.length; i++) {
				writer.write(values[i]);
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			System.err.println("Failed to write to file!");
			e.printStackTrace();
		}
	}

	public static void resetStatistics(Level level) {
		File file = new File(Game.directory, "saves.txt");

		String[] values = readFromFile(file);

		for (int i = 0; i < values.length; i++)
			if (i == values.length - 1)
				values[i] = "1";
			else
				values[i] = "0";

		Saves.DEATHS = 0;
		Saves.HIGH_SCORE = 0;
		Saves.LONGEST_RUN = 0;
		Saves.POWER_UPS = 0;
		Saves.TIME_PLAYED = 0;
		Saves.ACHIEVEMENT_EVERY_POWER_UP = false;
		Saves.ACHIEVEMENT_FIVE_POWER_DOWNS = false;
		Saves.ACHIEVEMENT_NO_POWER_UPS = false;
		Saves.setSound(true);

		BalloonMenu.FINAL_COLOR = Player.COLOR_RED;
		BalloonMenu.FINAL_TYPE = Player.TYPE_NORMAL;

		level.initAchievements();

		Resource.writeToFile(file, values);
	}

	public static String[] divideString(String message, double charsPerLine) {
		List<String> output = new ArrayList<String>();

		String string = "";
		int count = 0;
		for (int i = 0; i < message.length(); i++) {
			string += Character.toString(message.charAt(i));

			if (((i - count) % charsPerLine == 0 && i != 0) || i == message.length() - 1) {
				if (message.charAt(i) != ' ' && i != message.length() - 1) {
					int k = i + 1;
					while (message.charAt(k - 1) != ' ') {
						string += Character.toString(message.charAt(k));
						k++;
						i++;
						count++;

						if (k == message.length())
							break;
					}
				}

				output.add(string);
				string = "";
			}
		}

		String[] o = new String[output.size()];
		for (int i = 0; i < output.size(); i++)
			o[i] = output.get(i);

		return o;
	}

	public static Sprite balloonColorSwitch(Sprite sprite, int color, int fade) {
		int width = sprite.getWidth();
		int height = sprite.getHeight();

		int[] pixels = new int[width * height];
		sprite.image.getPixels(pixels, 0, width, 0, 0, width, height);

		for (int k = 0; k < pixels.length; k++) {
			int clr = pixels[k] & 0xffffff;

			if (clr == 0xff0000) {
				int cc = (((pixels[k] >> 24) & 0xff) << 24) | color;
				pixels[k] = cc;
			} else if (clr == 0xffbfbf) {
				int cc = (((pixels[k] >> 24) & 0xff) << 24) | fade;
				pixels[k] = cc;
			}
		}

		sprite = new Sprite(Bitmap.createBitmap(pixels, width, height, Config.ARGB_8888));
		return sprite;
	}
}
