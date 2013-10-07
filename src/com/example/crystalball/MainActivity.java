package com.example.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();

	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	private CrystalBall mCrystalBall = new CrystalBall();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {
				handleNewAnswer();
			}
		});

		Log.d(TAG, "onCreate()");

	}

	@Override
	public void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	private void handleNewAnswer() {
		String answer = mCrystalBall.getAnAnswer();

		// Update the label with our dynamic answer
		mAnswerLabel.setText(answer);

		animateCrystalBall();
		animateAnswer();
		playSound();
	}

	public void animateCrystalBall() {

		mCrystalBallImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage
				.getDrawable();
		if (ballAnimation.isRunning()) {
			ballAnimation.stop();
		}
		ballAnimation.start();

		/*
		 * Animation translateAnim = new TranslateAnimation(0, 320, 0, 0); //
		 * Set as 1 second translateAnim.setDuration(1000); // Repeat
		 * indefinitely translateAnim.setRepeatCount(Animation.RESTART); //
		 * Reverse animation each time
		 * translateAnim.setRepeatMode(Animation.REVERSE);
		 * mCrystalBallImage.startAnimation(translateAnim);
		 */

		/*
		 * Animation scaleAnimation = new
		 * ScaleAnimation(Animation.RELATIVE_TO_SELF, 0.5f,
		 * Animation.RELATIVE_TO_SELF, 0.5f); scaleAnimation.setDuration(3000);
		 * scaleAnimation.setRepeatCount(1);
		 * scaleAnimation.setRepeatMode(Animation.REVERSE);
		 * mCrystalBallImage.setAnimation(scaleAnimation);
		 */

	}

	private void animateAnswer() {
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
		fadeInAnimation.setDuration(1500);
		fadeInAnimation.setFillAfter(true);

		mAnswerLabel.setAnimation(fadeInAnimation);
	}

	private void playSound() {
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.crystal_ball);
		mediaPlayer.start();
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	public void initialize() {
		mAnswerLabel = (TextView) findViewById(R.id.tvAnswer);
		mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
