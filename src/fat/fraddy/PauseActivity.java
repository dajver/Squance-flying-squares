package fat.fraddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class PauseActivity extends Activity {
	
	Button resumeButton;
	Button menuButton;
	Button retryButton;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.pause);
        
        resumeButton = (Button)findViewById(R.id.button1);
        retryButton = (Button)findViewById(R.id.button2);
        menuButton = (Button)findViewById(R.id.button3);
        
        
        resumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });
        
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PauseActivity.this,  Main.class));
            	finish();
            }
        });
        
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(PauseActivity.this,  StartActivity.class));
            	finish();
            }
        });
    }
}
