package fat.fraddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {

	// Отлавливаем переход назад
	@Override
	public void onBackPressed() {

		stopService(new Intent(this, BackgroundMusic.class));
		finish();
	}

	/** Обработка нажатия кнопок */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.button1: {
				Intent intent = new Intent();
				intent.setClass(this, Main.class);
				startActivity(intent);
				finish();
			}
				break;
			case R.id.button2: {
				stopService(new Intent(this, BackgroundMusic.class));
				finish();
			}
				break;
			default:
				break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// если хотим, чтобы приложение постоянно имело портретную ориентацию
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// если хотим, чтобы приложение было полноэкранным
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// и без заголовка
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
		// setContentView(new GameView(this,null));
		Button startButton = (Button) findViewById(R.id.button1);
		startButton.setOnClickListener(this);
		Button exitButton = (Button) findViewById(R.id.button2);
		exitButton.setOnClickListener(this);
		startService(new Intent(this, BackgroundMusic.class));
	}
}