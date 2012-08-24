package fat.fraddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Main extends Activity {

	Button pauseButton;

	@Override
	public void onBackPressed() {

		Intent lIntentObj = new Intent(this, StartActivity.class);
		startActivity(lIntentObj);
		stopService(new Intent(this, BackgroundMusic.class));
		finish();
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
		setContentView(R.layout.main);
		pauseButton = (Button) findViewById(R.id.button1);
		pauseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(Main.this, PauseActivity.class));
				// finish();
			}
		});
	}
}