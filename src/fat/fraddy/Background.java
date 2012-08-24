package fat.fraddy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fat.fraddy.GameView.GameThread;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Background extends SurfaceView implements Runnable {
	 
	 private GameThread thread;
	 private Bitmap layouts;
	 private Bitmap blue_layouts;
	 private Context c;
	 
	 private Thread tr = new Thread(this);
	 
	 private List<Rectangle> rectangle = new ArrayList<Rectangle>();
	 
	 /**Переменная запускающая поток рисования*/
	  private boolean running = false;
	 
	 //--------------------------------------------------START OF THREAD CLASS-------------------------------------------------------------	 
	public class GameThread extends Thread {
		/**Объект класса*/
	    private Background view;	 	    
	    
	    /**Конструктор класса*/
	    public GameThread(Background background) {
	          this.view = background;
	    }

	    public GameThread(Callback callback, SurfaceHolder holder, Context c) {
	    	super();
		}

		/**Задание состояния потока*/
	    public void setRunning(boolean run) {
	          running = run;
	    }

	    /** Действия, выполняемые в потоке */
	    public void run() {
	        while (running) {
	            Canvas canvas = null;
	            try {
	                // подготовка Canvas-а
	                canvas = view.getHolder().lockCanvas();
	                synchronized (view.getHolder()) {
	                    // собственно рисование
	                    onDraw(canvas);
	                }
	            }
	            catch (Exception e) { }
	            finally {
	                if (canvas != null) {
	                	view.getHolder().unlockCanvasAndPost(canvas);
	                }
	            }
	        }
	    }
}
	//----------------------------------------------------End of GameThread--------------------------------------------------\\
	
	public Background(Context context, AttributeSet attrs) {
		super(context);
		
		thread = new GameThread(this);
        
        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback() {
      	  	 /*** Уничтожение области рисования */
               public void surfaceDestroyed(SurfaceHolder holder) {
            	   boolean retry = true;
	           	    thread.setRunning(false);
	           	    while (retry) {
	           	        try {
	           	            // ожидание завершение потока
	           	        	thread.join();
	           	            retry = false;
	           	        }
	           	        catch (InterruptedException e) { }
	           	    }
               }

               /** Создание области рисования */
               public void surfaceCreated(SurfaceHolder holder) {          	   
            	   if (thread .getState() == Thread.State.TERMINATED)  {
            		   thread = new GameThread(this, getHolder(), c);  
            		   thread.setRunning(true);
            		   thread.start();
            	   } else {
            		   thread.setRunning(true);
            		   thread.start();
            	       tr.start();
            	   }
               }

               /** Изменение области рисования */
               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
               }
        });
	
        layouts = BitmapFactory.decodeResource(getResources(), fat.fraddy.R.drawable.big_rect);
        blue_layouts = BitmapFactory.decodeResource(getResources(), fat.fraddy.R.drawable.big_rect_blue);
	}


	 protected void onDraw(Canvas canvas) {
		 canvas.drawColor(Color.WHITE);
		 
		 for(Rectangle rec : rectangle) {
			 rec.onDraw(canvas);
		 }
	 }

	public void run() {
		while(true) {
			Random rnd = new Random(System.currentTimeMillis());				
			try {
				Thread.sleep(rnd.nextInt(500));					
				rectangle.add(new Rectangle(this, layouts));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
			try {
				Thread.sleep(rnd.nextInt(500));	
				rectangle.add(new Rectangle(this, blue_layouts));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
