package fat.fraddy;

import boxes.SpriteListMinusX;
import boxes.SpriteListMinusXBad;
import boxes.SpriteListMinusY;
import boxes.SpriteListMinusYBad;
import boxes.SpriteListX;
import boxes.SpriteListXBad;
import boxes.SpriteListY;
import boxes.SpriteListYBad;
import circle.CircleMinusX;
import circle.CircleMinusXBad;
import circle.CircleMinusY;
import circle.CircleMinusYBad;
import circle.CircleX;
import circle.CircleXBad;
import circle.CircleY;
import circle.CircleYBad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

	// позиция нажатия палцем по экрану
	public float posX;
	public float posY;
	// количество очков
	public int score = 0;
	// картинка круга
	private final Bitmap circle;
	private final Bitmap circle_blue;
	/** Боольшой квадрат по координате У */
	private final List<CircleMinusXBad> cmxb = new ArrayList<CircleMinusXBad>();
	/** Боольшой квадрат по координате Х */
	private final List<CircleMinusY> cmy = new ArrayList<CircleMinusY>();
	/** Боольшой квадрат по координате У */
	private final List<CircleMinusYBad> cmyb = new ArrayList<CircleMinusYBad>();
	private Context context;
	/** Боольшой квадрат по координате Х */
	private final List<CircleMinusX> crx = new ArrayList<CircleMinusX>();
	/** Боольшой квадрат по координате Х */
	private final List<CircleX> cx = new ArrayList<CircleX>();
	/** Боольшой квадрат по координате У */
	private final List<CircleXBad> cxb = new ArrayList<CircleXBad>();
	/** Боольшой квадрат по координате Х */
	private final List<CircleY> cy = new ArrayList<CircleY>();
	/** Боольшой квадрат по координате У */
	private final List<CircleYBad> cyb = new ArrayList<CircleYBad>();
	private final int filed;
	/** Массив размеров игрового квадрата */
	private final Bitmap[] masPlayer = new Bitmap[] {
			BitmapFactory.decodeResource(getResources(), R.drawable.player_6),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_7),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_8),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_9),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_10),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_11),
			BitmapFactory.decodeResource(getResources(), R.drawable.player_max) };
	// вывод очков игрока
	private final Paint mScorePaint;
	/** Объект класса GameLoopThread */
	private GameThread mThread;
	// переменная в которую заносим воспроизводимый звук
	private final int norm;
	// объект класса игрок
	private final Player p;
	// картинка игрока
	private Bitmap player;
	// картинка черного квадрата
	private final Bitmap rect_big;
	// картинка синего квадрата
	private final Bitmap rect_big_bad;
	// ---------------------------------------------------------------------------------------------------------------------
	/** Боольшой квадрат по координате Х */
	private final List<SpriteListMinusX> rmx = new ArrayList<SpriteListMinusX>();
	/** Боольшой квадрат по координате Х */
	private final List<SpriteListMinusXBad> rmxb = new ArrayList<SpriteListMinusXBad>();
	/** Боольшой квадрат по координате У */
	private final List<SpriteListMinusY> rmy = new ArrayList<SpriteListMinusY>();
	/** Боольшой квадрат по координате У */
	private final List<SpriteListMinusYBad> rmyb = new ArrayList<SpriteListMinusYBad>();
	/************************************************************************************************************************/
	// --------------------------------------------------CIRCLES-------------------------------------------------------------/
	/**********************************************************************************************************************/
	/** Переменная запускающая поток рисования */
	private boolean running = false;
	/***********************************************************************************************************************/
	// --------------------------------------------------RECTANGLES-----------------------------------------------------------/
	/** Боольшой квадрат по координате Х */
	private final List<SpriteListX> rx = new ArrayList<SpriteListX>();
	/** Боольшой квадрат по координате Х */
	private final List<SpriteListXBad> rxb = new ArrayList<SpriteListXBad>();
	/** Боольшой квадрат по координате У */
	private final List<SpriteListY> ry = new ArrayList<SpriteListY>();
	// ---------------------------------------------------------------------------------------------------------------------
	/** Боольшой квадрат по координате У */
	private final List<SpriteListYBad> ryb = new ArrayList<SpriteListYBad>();
	// объекта класса создающего звуки
	private final SoundPool sounds;
	private SurfaceView view;
	Context c;
	/************************************************************************************************************************/
	// начальный размер квадрата
	int sizeRect = 0;
	/** Проигрываем музыку в фоне */
	Intent svc;
	// поток создающий квадраты
	Thread tr = new Thread(this);

	/** Конструктор */
	public GameView(Context context, AttributeSet attrs) {

		super(context, attrs);
		mThread = new GameThread(this);
		/* Рисуем все наши объекты и все все все */
		getHolder().addCallback(new SurfaceHolder.Callback() {

			/** Изменение области рисования */
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

			}

			/** Создание области рисования */
			@Override
			public void surfaceCreated(SurfaceHolder holder) {

				if (mThread.getState() == Thread.State.TERMINATED) {
					mThread = new GameThread(this, getHolder(), c);
					mThread.setRunning(true);
					mThread.start();
				} else {
					mThread.setRunning(true);
					mThread.start();
					tr.start();
					circleBackgroundPlayer();
				}
			}

			/*** Уничтожение области рисования */
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {

				boolean retry = true;
				mThread.setRunning(false);
				while (retry) {
					try {
						// ожидание завершение потока
						mThread.join();
						retry = false;
					} catch (InterruptedException e) {}
				}
			}
		});
		// загружаем картинку игрока
		player = masPlayer[sizeRect];
		p = new Player(this, player);
		// big
		rect_big = BitmapFactory.decodeResource(getResources(), R.drawable.big_rect);
		// big and bad
		rect_big_bad = BitmapFactory.decodeResource(getResources(), R.drawable.big_rect_blue);
		// circle sprite
		circle = BitmapFactory.decodeResource(getResources(), R.drawable.middle_circle);
		// secorn circle
		circle_blue = BitmapFactory.decodeResource(getResources(), R.drawable.middle_circle_blue);
		// стили для вывода счета
		mScorePaint = new Paint();
		mScorePaint.setTextSize(30);
		mScorePaint.setStrokeWidth(1);
		mScorePaint.setStyle(Style.FILL);
		mScorePaint.setColor(Color.GRAY);
		// загружаем звук который проигрывается при сталкновении
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		norm = sounds.load(context, R.raw.norm, 1);
		filed = sounds.load(context, R.raw.filed, 1);
	}

	/* Поток цирклов */
	public void circleBackgroundPlayer() {

		Thread myThread = new Thread(new Runnable() {

			@Override
			public void run() {

				Random rnd = new Random(System.currentTimeMillis());
				int speed = rnd.nextInt(4 - 1) + 1;
				int loop = rnd.nextInt(5000 - 3000) + 3000;
				// CX
				try {
					Thread.sleep(loop);
					cx.add(new CircleX(this, circle, rnd.nextInt(1) - 300, rnd.nextInt(800), speed));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// CY
				try {
					Thread.sleep(loop);
					cy.add(new CircleY(this, circle, rnd.nextInt(800), rnd.nextInt(1) - 300, speed));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Minus X
				try {
					Thread.sleep(loop);
					crx.add(new CircleMinusX(this, circle, 1000, rnd.nextInt(500), speed));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Minus Y
				try {
					Thread.sleep(loop);
					cmy.add(new CircleMinusY(this, circle, rnd.nextInt(800), 1000, speed));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// X Black
				while (true) {
					try {
						Thread.sleep(loop);
						cxb.add(new CircleXBad(this, circle_blue, rnd.nextInt(1) - 300, 800, speed));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Y Black
					try {
						Thread.sleep(loop);
						cyb.add(new CircleYBad(this, circle_blue, 1000, rnd.nextInt(1) - 300, speed));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Minus X Black
					try {
						Thread.sleep(loop);
						cmxb.add(new CircleMinusXBad(this, circle_blue, 1000, rnd.nextInt(500), speed));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Minus Y Black
					try {
						Thread.sleep(loop);
						cmyb.add(new CircleMinusYBad(this, circle_blue, rnd.nextInt(300), 600, speed));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		myThread.start();
	}

	/** Метод создающий столкновения с кругами */
	public void isCircleCollision() {

		/* Боольшой квадрат по координате У */
		Iterator<CircleMinusXBad> qw = cmxb.iterator();
		while (qw.hasNext()) {
			CircleMinusXBad sp = qw.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				qw.remove();
				// при столкновении переходим на следющий спрайт
				player = masPlayer[0];
				p.bmp = player;
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(1500 - 500) + 500;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате У */
		Iterator<CircleXBad> re = cxb.iterator();
		while (re.hasNext()) {
			CircleXBad sp = re.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				re.remove();
				// при столкновении переходим на следющий спрайт
				player = masPlayer[0];
				p.bmp = player;
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(500 - 100) + 100;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате У */
		Iterator<CircleMinusYBad> we = cmyb.iterator();
		while (we.hasNext()) {
			CircleMinusYBad sp = we.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				we.remove();
				// при столкновении переходим на следющий спрайт
				player = masPlayer[0];
				p.bmp = player;
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(1500 - 500) + 500;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате У */
		Iterator<CircleYBad> cv = cyb.iterator();
		while (cv.hasNext()) {
			CircleYBad sp = cv.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				cv.remove();
				// при столкновении переходим на следющий спрайт
				player = masPlayer[0];
				p.bmp = player;
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(1500 - 500) + 500;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате Х */
		Iterator<CircleMinusX> e = crx.iterator();
		while (e.hasNext()) {
			CircleMinusX sp = e.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				e.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате Х */
		Iterator<CircleMinusY> as = cmy.iterator();
		while (as.hasNext()) {
			CircleMinusY sp = as.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				as.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате Х */
		Iterator<CircleX> cxa = cx.iterator();
		while (cxa.hasNext()) {
			CircleX sp = cxa.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				cxa.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Боольшой квадрат по координате Х */
		Iterator<CircleY> te = cy.iterator();
		while (te.hasNext()) {
			CircleY sp = te.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				te.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
	}

	/** Проверка на косание к экрану */
	@Override
	public boolean onTouchEvent(MotionEvent e) {

		posX = e.getX();
		posY = e.getY();
		return true;
	}

	/** Поток рисования обектов */
	@Override
	public void run() {

		while (true) {
			Random rnd = new Random(System.currentTimeMillis());
			// ---------------------------------------------------------------
			float density = getResources().getDisplayMetrics().density;
			int rxd = (int) (rnd.nextInt(1) - (300 * density));
			int ryd = (int) (rnd.nextInt(1) - (300 * density));
			int rmxd = (int) (1000 * density);
			int rmyd = (int) (1000 * density);
			int rxbd = (int) (rnd.nextInt(1) - (300 * density));
			int rybd = (int) (rnd.nextInt(1) - (300 * density));
			int rmxbd = (int) (1000 * density);
			int rmybd = (int) (600 * density);
			// -------------------------------------------------------------
			// скорость летящего предмета
			int speed = rnd.nextInt(3 - 1) + 1;
			// rxd
			try {
				Thread.sleep(rnd.nextInt(500));
				rx.add(new SpriteListX(this, rect_big, rxd, rnd.nextInt(800), speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// ryd
			try {
				Thread.sleep(rnd.nextInt(500));
				ry.add(new SpriteListY(this, rect_big, rnd.nextInt(800), ryd, speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// rmxd
			try {
				Thread.sleep(rnd.nextInt(500));
				rmx.add(new SpriteListMinusX(this, rect_big, rmxd, rnd.nextInt(500), speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// rmyd
			try {
				Thread.sleep(rnd.nextInt(500));
				rmy.add(new SpriteListMinusY(this, rect_big, rnd.nextInt(800), rmyd, speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// **-----------------------------------------------------------------------------------------------------------------**
			// rxbd
			try {
				Thread.sleep(rnd.nextInt(500));
				rxb.add(new SpriteListXBad(this, rect_big_bad, rxbd, 800, speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// rybd
			try {
				Thread.sleep(rnd.nextInt(500));
				ryb.add(new SpriteListYBad(this, rect_big_bad, 1000, rybd, speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// rmxbd
			try {
				Thread.sleep(rnd.nextInt(500));
				rmxb.add(new SpriteListMinusXBad(this, rect_big_bad, rmxbd, rnd.nextInt(500), speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// rmybd
			try {
				Thread.sleep(rnd.nextInt(500));
				rmyb.add(new SpriteListMinusYBad(this, rect_big_bad, rnd.nextInt(300), rmybd, speed));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/** Проверка на столкновения */
	private void testCollision() {

		/* Движение с лево на право по Х */
		Iterator<SpriteListX> i = rx.iterator();
		while (i.hasNext()) {
			SpriteListX sp = i.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				i.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение с верху вниз по У */
		Iterator<SpriteListY> k = ry.iterator();
		while (k.hasNext()) {
			SpriteListY sp = k.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				k.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение с право на лево по Х */
		Iterator<SpriteListMinusX> j = rmx.iterator();
		while (j.hasNext()) {
			SpriteListMinusX sp = j.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				j.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение с низу в верх по У */
		Iterator<SpriteListMinusY> n = rmy.iterator();
		while (n.hasNext()) {
			SpriteListMinusY sp = n.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				n.remove();
				running = false;
				Context context = getContext();
				Intent intent = new Intent(context, FiledActivity.class);
				intent.putExtra(FiledActivity.EXT_COLS, score);
				context.startActivity(intent);
				((Activity) context).finish();
				// проигрыш звука
				sounds.play(filed, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение синего блока с лево на право по оси Х */
		Iterator<SpriteListXBad> q = rxb.iterator();
		while (q.hasNext()) {
			SpriteListXBad sp = q.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				q.remove();
				// при столкновении переходим на следющий спрайт
				if (player == masPlayer[6]) {
					player = masPlayer[sizeRect--];
					p.bmp = player;
				} else {
					player = masPlayer[sizeRect++];
					p.bmp = player;
				}
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(150 - 50) + 50;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение синего блока с верху вниз по оси У */
		Iterator<SpriteListYBad> w = ryb.iterator();
		while (w.hasNext()) {
			SpriteListYBad sp = w.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				w.remove();
				// при столкновении переходим на следющий спрайт
				if (player == masPlayer[6]) {
					player = masPlayer[sizeRect--];
					p.bmp = player;
				} else {
					player = masPlayer[sizeRect++];
					p.bmp = player;
				}
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(150 - 50) + 50;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// звук врезания
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение синего блока с право на лево по оси Х */
		Iterator<SpriteListMinusXBad> u = rmxb.iterator();
		while (u.hasNext()) {
			SpriteListMinusXBad sp = u.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				u.remove();
				// при столкновении переходим на следющий спрайт
				if (player == masPlayer[6]) {
					player = masPlayer[sizeRect--];
					p.bmp = player;
				} else {
					player = masPlayer[sizeRect++];
					p.bmp = player;
				}
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(150 - 50) + 50;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// звук врезания
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		/* Движение синего блока снизу вверх по оси У */
		Iterator<SpriteListMinusYBad> r = rmyb.iterator();
		while (r.hasNext()) {
			SpriteListMinusYBad sp = r.next();
			if ((Math.abs(sp.x - p.x) <= ((sp.width + p.width) / 2f))
					&& (Math.abs(sp.y - p.y) <= ((sp.height + p.height) / 2f))) {
				r.remove();
				// при столкновении переходим на следющий спрайт
				if (player == masPlayer[6]) {
					player = masPlayer[sizeRect--];
					p.bmp = player;
				} else {
					player = masPlayer[sizeRect++];
					p.bmp = player;
				}
				// увеличиваем скоре на 1
				Random rnd = new Random(System.currentTimeMillis());
				score += rnd.nextInt(150 - 50) + 50;
				// увеличиваем размер картинки
				sp.height += 5;
				sp.width += 5;
				// проигрыш звука
				sounds.play(norm, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		}
		// определили что цирклы сталкиваются
		isCircleCollision();
	}

	/** Функция рисующая все спрайты и фон */
	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(Color.WHITE);
		/*
		 * Timer myTimer = new Timer(); // Создаем таймер myTimer.schedule(new TimerTask() { // Определяем
		 * задачу public void run() { new Thread(new Runnable() { public void run() { score++; } }); }; }, 0,
		 * 60 * 10);
		 */// интервал - 60000 миллисекунд, 0 миллисекунд до первого запуска.
		int scoreX = getWidth() / 2;
		int scoreY = getHeight() / 2;
		canvas.drawText("" + score, scoreX, scoreY, mScorePaint);
		Iterator<SpriteListX> i = rx.iterator();
		while (i.hasNext()) {
			SpriteListX e = i.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListY> k = ry.iterator();
		while (k.hasNext()) {
			SpriteListY e = k.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListMinusX> j = rmx.iterator();
		while (j.hasNext()) {
			SpriteListMinusX e = j.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListMinusY> n = rmy.iterator();
		while (n.hasNext()) {
			SpriteListMinusY e = n.next();
			e.onDraw(canvas);
		}
		// ------------------------------------------------------
		Iterator<SpriteListXBad> q = rxb.iterator();
		while (q.hasNext()) {
			SpriteListXBad e = q.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListYBad> w = ryb.iterator();
		while (w.hasNext()) {
			SpriteListYBad e = w.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListMinusXBad> u = rmxb.iterator();
		while (u.hasNext()) {
			SpriteListMinusXBad e = u.next();
			e.onDraw(canvas);
		}
		Iterator<SpriteListMinusYBad> r = rmyb.iterator();
		while (r.hasNext()) {
			SpriteListMinusYBad e = r.next();
			e.onDraw(canvas);
		}
		// *******************************CIRCLES***********************************************
		Iterator<CircleMinusX> a = crx.iterator();
		while (a.hasNext()) {
			CircleMinusX e = a.next();
			e.onDraw(canvas);
		}
		Iterator<CircleMinusXBad> s = cmxb.iterator();
		while (s.hasNext()) {
			CircleMinusXBad e = s.next();
			e.onDraw(canvas);
		}
		Iterator<CircleMinusY> d = cmy.iterator();
		while (d.hasNext()) {
			CircleMinusY e = d.next();
			e.onDraw(canvas);
		}
		Iterator<CircleMinusYBad> f = cmyb.iterator();
		while (f.hasNext()) {
			CircleMinusYBad e = f.next();
			e.onDraw(canvas);
		}
		Iterator<CircleX> g = cx.iterator();
		while (r.hasNext()) {
			CircleX e = g.next();
			e.onDraw(canvas);
		}
		Iterator<CircleXBad> h = cxb.iterator();
		while (h.hasNext()) {
			CircleXBad e = h.next();
			e.onDraw(canvas);
		}
		Iterator<CircleY> l = cy.iterator();
		while (l.hasNext()) {
			CircleY e = l.next();
			e.onDraw(canvas);
		}
		Iterator<CircleYBad> z = cyb.iterator();
		while (z.hasNext()) {
			CircleYBad e = z.next();
			e.onDraw(canvas);
		}
		// ***********************************PLAYER*********************************************
		p.onDraw(canvas, posX, posY);
	}

	// -------------Start of GameThread------------------------------------------------\\
	public class GameThread extends Thread {

		/** Объект класса */
		private GameView view;

		public GameThread(Callback callback, SurfaceHolder holder, Context context) {

			super();
		}

		/** Конструктор класса */
		public GameThread(GameView view) {

			this.view = view;
		}

		/** Действия, выполняемые в потоке */
		@Override
		public void run() {

			while (running) {
				Canvas canvas = null;
				try {
					// подготовка Canvas-а
					canvas = view.getHolder().lockCanvas();
					synchronized (view.getHolder()) {
						// собственно рисование
						onDraw(canvas);
						testCollision();
					}
				} catch (Exception e) {} finally {
					if (canvas != null) {
						view.getHolder().unlockCanvasAndPost(canvas);
					}
				}
			}
		}

		/** Задание состояния потока */
		public void setRunning(boolean run) {

			running = run;
		}
	}
	// -------------End of GameThread--------------------------------------------------\\
}
