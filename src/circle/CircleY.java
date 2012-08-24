/**
 * 
 * Движение по оси У 
 * 
 * */

package circle;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fat.fraddy.GameView;

public class CircleY
{
	private GameView gameView;
	
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	public int mSpeed;
	
	public int width = 35;
	public int height = 35;
	
	public CircleY(GameView gameView, Bitmap bmp, int x, int y, int speed)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		Random rnd = new Random(System.currentTimeMillis());
		x = rnd.nextInt(800);
		y = rnd.nextInt(1) - 300;
		
		this.x = x;
		this.y = y;
		this.mSpeed = speed;
	}
	
	Runnable runnable;
	public CircleY(Runnable runnable, Bitmap circle, int x2, int nextInt, int speed) {
		this.runnable = runnable;
		this.bmp = circle;
		
		this.x = x2;
		this.y = nextInt;
		this.mSpeed = speed;
	}

	/**Перемещение объекта, его направление*/
    private void update() 
    {       
        y += mSpeed;
    }

   /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) 
    {
         update();
         canvas.drawBitmap(bmp, x, y, null);
    }
}
