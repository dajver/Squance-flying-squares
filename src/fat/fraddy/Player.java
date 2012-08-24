package fat.fraddy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Handler;

public class Player 
{
	public GameView gm;
	
	public Bitmap bmp;
	public final Matrix transform = new Matrix();
	
	public float x;
	public float y;
	
	public Handler handler;
	
	int p;
	
	public int width = 45;
	public int height = 45;
	
	public Player(GameView gm, Bitmap bmp) {
		this.gm = gm;
		this.bmp = bmp;
	}
	
	public void rotate() {
		  transform.preRotate(1, bmp.getWidth() / 2, bmp.getHeight() / 2);
	}
	
	/**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas, float x, float y) {
    	rotate();
    	this.x = x;
    	this.y = y;
    	
    	canvas.save();
    	canvas.translate(x, y);
        canvas.drawBitmap(bmp, transform, null);
        canvas.restore();
    }
}
