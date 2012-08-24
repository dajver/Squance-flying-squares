/**
 * 
 * Движение  по оси -У 
 * 
 * */

package boxes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fat.fraddy.GameView;

public class SpriteListMinusYBad
{
	private GameView gameView;
	
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	public int mSpeed;
	
	public int width;
	public int height;
	
	public SpriteListMinusYBad(GameView gameView, Bitmap bmp, int x, int y, int speed)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		this.x = x;
		this.y = y;
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
