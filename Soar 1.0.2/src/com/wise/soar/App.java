package com.wise.soar;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.wise.soar.res.Resource;

@SuppressLint("NewApi")
public class App extends Activity {
	private WelcomeScreen screen;

	private boolean DONT_ALLOW = false;

	public static Context context;

	public static Resources res;
	private Random random = new Random();

	private LicenseChecker checker;
	private LicenseCheckerCallback callback;

	private final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo3vqIhSxAj5lid4hxvkT1ZGpvVK02n7iqaFZMC3v3L5eSnSiIOPL24cv5+bbMev/GF9ocsmFx38VNRmWoAyIJ4AIBgH/eySc1SSgTViUzdcVU2ohCb+BTfbsqZbSAbrcUpM2/hKNXJOiYIFR8OH17iOr8lpTnc+IbeyRFgy0ENQydMzL76+5A+fRoptxuRtjHadJhlCZTr9Qrw6zKDvVswbGBukxM681XJq6jIcBXB+UBh1fDEfTiw90u6aZxQBNBfItEE52BejBLU7mQFUY5PZIkaTo3vLqgF9Z4YT/76SU5M2herQjcaC/Uj7n2E4XMOKIMDkzPI8i64pMT2QwiwIDAQAB";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

		String id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		byte[] buffer = new byte[128];
		random.nextBytes(buffer);

		callback = new LicenseCallbackHandler();
		checker = new LicenseChecker(getApplicationContext(), new ServerManagedPolicy(this, new AESObfuscator(buffer, getPackageName(), id)), BASE64_PUBLIC_KEY);

		checker.checkAccess(callback);

		if (DONT_ALLOW)
			return;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		screen = new WelcomeScreen(this, width, height);
		setContentView(screen);

		Resources r = getResources();
		res = r;

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Resource.init();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				while (screen.inTransition()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				doLogic();
			}
		}).start();
	}

	private void doLogic() {
		screen.stop();

		if (DONT_ALLOW)
			return;

		Intent intent = new Intent(App.this, Welcome.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(0, 0);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		checker.onDestroy();
	}

	private class LicenseCallbackHandler implements LicenseCheckerCallback {
		public void allow(int reason) {
			if (isFinishing())
				return;

			DONT_ALLOW = false;
		}

		public void dontAllow(int reason) {
			if (isFinishing())
				return;

			DONT_ALLOW = true;

			AlertDialog.Builder builder = new AlertDialog.Builder(context);

			builder.setMessage("The license to this copy of Soar is not valid! You must purchase Soar from Google Play to continue!");
			builder.setTitle("License Not Valid");
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});

			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});

			AlertDialog dialog = builder.create();
			dialog.show();
		}

		public void applicationError(int errorCode) {
			dontAllow(errorCode);
		}
	}
}
