package com.wise.soar.res;

import android.media.MediaPlayer;

import com.wise.soar.App;
import com.wise.soar.level.Saves;

public class Sound {
	private MediaPlayer player;

	public Sound(int id) {
		player = MediaPlayer.create(App.context, id);
	}

	public void play() {
		if (!Saves.getSound())
			return;

		player.start();
	}

	public void stop() {
		player.stop();
	}

	public void pause() {
		player.pause();
	}
}