package fat.fraddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class FiledActivity extends Activity implements OnClickListener {

	public static final String EXT_COLS = "cols";
	private GameView gm;

	/** Обработка нажатия кнопок */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.button1: {
				Intent intent = new Intent();
				intent.setClass(this, Main.class);
				startActivity(intent);
				finish();
				break;
			}
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
		setContentView(R.layout.filed);
		Button startButton = (Button) findViewById(R.id.button1);
		startButton.setOnClickListener(this);
		Bundle extras = getIntent().getExtras();
		int cols = extras.getInt(EXT_COLS);
		TextView message = (TextView) findViewById(R.id.textView3);
		message.setText(cols + "");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
			Intent lIntentObj = new Intent(this, StartActivity.class);
			startActivity(lIntentObj);
			finish();
			stopService(new Intent(this, BackgroundMusic.class));
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
