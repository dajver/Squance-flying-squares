/**
 * 
 * движение по оси Х
 * 
 * */

package circle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fat.fraddy.GameView;

public class CircleX
{
	private GameView gameView;
	
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	public int mSpeed;
	
	Runnable runnable;
	
	public int width = 35;
	public int height = 35;
	
	public CircleX(GameView gameView, Bitmap bmp, int x, int y, int speed)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		this.x = x;
		this.y = y;
		this.mSpeed = speed;
	}
	
	public CircleX(Runnable runnable, Bitmap circle, int x2, int nextInt, int speed) {
		this.runnable = runnable;
		this.bmp = circle;
		
		this.x = x2;
		this.y = nextInt;
		this.mSpeed = speed;
	}

	/**Перемещение объекта, его направление*/
    private void update() 
    {       
        x += mSpeed;
    }

   /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) 
    {
         update();
         canvas.drawBitmap(bmp, x, y, null);
    }
}