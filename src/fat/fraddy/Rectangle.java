package fat.fraddy;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Rectangle {
	private Bitmap bmp;
	
	/**С лева на правл*/
	public int lx, ly;
	
	/**С права на лево*/
	public int rx, ry;
	
	/**С вепху вниз*/
	public int uy, ux;
	
	/**Снизу вверх*/
	public int dy, dx;
	
	Background gameView;
	
	public int mSpeed;
	
	public int width = 35;
	public int height = 35;
	
	public Rectangle(Background gameView, Bitmap bmp)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		Random rnd = new Random(System.currentTimeMillis());
		
		mSpeed = rnd.nextInt(20 - 15) + 15;
		
		this.lx = rnd.nextInt(1) - 300;
		this.ly = rnd.nextInt(800);
		
		this.rx = 1000;
		this.ry = rnd.nextInt(500);
		
		this.uy = rnd.nextInt(1) - 300;
		this.ux = rnd.nextInt(800);
		
		this.dx = rnd.nextInt(800);
		this.dy = 1000;
	}
    
  //с низу вверх
    private void updateDown() {       
        dy -= mSpeed;
        uy += mSpeed;
        rx -= mSpeed;
        lx += mSpeed;
    }

   /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) 
    {
    	updateDown();
    	
         canvas.drawBitmap(bmp, lx, ly, null);
         canvas.drawBitmap(bmp, rx, ry, null);
         canvas.drawBitmap(bmp, dx, dy, null);
         canvas.drawBitmap(bmp, ux, uy, null);
    }
}
