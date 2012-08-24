/**
 * 
 * Движение  по оси -У 
 * 
 * */

package circle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fat.fraddy.GameView;

public class CircleMinusYBad
{
	private GameView gameView;
	
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	public int mSpeed;
	
	public int width;
	public int height;
	
	public CircleMinusYBad(GameView gameView, Bitmap bmp, int x, int y, int speed)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		this.x = x;
		this.y = y;
		this.mSpeed = speed;
	}
	
	Runnable runnable;
	public CircleMinusYBad(Runnable runnable, Bitmap circle, int x2, int nextInt, int speed) {
		this.runnable = runnable;
		this.bmp = circle;
		
		this.x = x2;
		this.y = nextInt;
		this.mSpeed = speed;
	}

	/**Перемещение объекта, его направление*/
    private void update() 
    {       
        y -= mSpeed;
    }

   /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) 
    {
         update();
         canvas.drawBitmap(bmp, x, y, null);
    }
}
