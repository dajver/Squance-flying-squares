/**
 * 
 * Движение по оси У 
 * 
 * */

package boxes;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import fat.fraddy.GameView;

public class SpriteListYBad
{
	private GameView gameView;
	
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	public int mSpeed;
	
	public int width = 35;
	public int height = 35;
	
	public SpriteListYBad(GameView gameView, Bitmap bmp, int x, int y, int speed)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		Random rnd = new Random(System.currentTimeMillis());
		
		this.x = x;
		this.y = y;
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
