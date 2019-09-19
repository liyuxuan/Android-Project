package cn.com.core.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import cn.com.core.R;
import cn.com.core.data.model.PointTrackEntity;

/**
 * 自定义手写板 集成 surfaceView
 */
public class PaintSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //标志手势抬起
    public static final int POINT_NEW_START = 0x001;
    //标志手势没有抬起
    public static final int POINT_FOLLOWING = 0x002;
    //线的粗度
    private static final int HAND_WRITE_STROKE_WIDTH = 3;
    private Paint mPaint;
    private Canvas mCanvas;
    //为了保存bitmap的
    private Canvas mCanvasTwo;
    private Bitmap mBitmap;
    private Bitmap mPreviousBitmap;

    private SurfaceHolder mHolder;
    //是否绘制
    private boolean isDrawing;
    private boolean isShowing;
    //绘画轨迹
    private Path mPath;
    //绘画轨迹集合
    private List<PointTrackEntity> mPointTracks;

    public PaintSurfaceView(Context context) {
        super(context);
        initView(context);
    }

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PaintSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PaintSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        float density = displayMetrics.density;
        LogUtils.d("===initView==density=" + density);

        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(HAND_WRITE_STROKE_WIDTH * density);
        mPaint.setColor(Color.BLACK);

        //绘画轨迹
        mPath = new Path();
        mPointTracks = new ArrayList<>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //避免SurfaceView初始化的时候黑屏
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_white);
        Canvas canvas = mHolder.lockCanvas();
        //创建缓存
        mBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvasTwo = new Canvas(mBitmap);
        if (canvas != null) {
            RectF rectF = new RectF(0, 0, this.getWidth(), getHeight());
            canvas.drawBitmap(bitmap, null, rectF, null);
            mHolder.unlockCanvasAndPost(canvas);
        }
        isShowing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i("MyLog","写字板进行变化");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        clearAreaImage();
//        //  绘制界面的部分;
//        isDrawing = false;
//        mCanvasTwo.drawColor(Color.WHITE);
        //  界面显示的部分;
        isShowing = false;
        Log.i("MyLog","写字板进行销毁");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                saveTouchPoint(x, y, POINT_NEW_START);
                mPath.moveTo(x, y);
                LogUtils.d("===onTouchEvent==ACTION_DOWN=mPath.moveTo-x" + x + "-y-" + y);
                return true;
            case MotionEvent.ACTION_MOVE:
                isDrawing = true;
                saveTouchPoint(x, y, POINT_FOLLOWING);
                mPath.lineTo(x, y);
                LogUtils.d("===onTouchEvent==ACTION_MOVE=mPath.moveTo-x" + x + "-y-" + y);
                return true;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                return true;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    //实现Runnable
    @Override
    public void run() {
        while (isShowing) {
            if (isDrawing) {
                try {
                    mCanvas = mHolder.lockCanvas();
                    doDraw();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (mCanvas != null) {
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
        }
    }

    private void doDraw() {
//        mCanvasTwo.drawColor(Color.WHITE);
        mCanvasTwo.drawColor(Color.WHITE);
        mCanvasTwo.drawPath(mPath, mPaint);
        //神奇的语句 有了它 bitmap 就可以也能同步的缓存SurfaceView。
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        LogUtils.d("===doDraw==");
    }

    public void clearAreaImage() {
        mPath.reset();
        cleanTouchPoint();
        isDrawing = false;
        mCanvas = mHolder.lockCanvas();
        doDraw();
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    public Bitmap getAreaImage() {
        return mBitmap;
    }

    private void saveTouchPoint(float x, float y, int pointFlag) {
        PointTrackEntity pointTrackEntity = new PointTrackEntity();
        pointTrackEntity.setPointX(x);
        pointTrackEntity.setPointY(y);
        pointTrackEntity.setPointFlag(pointFlag);
        mPointTracks.add(pointTrackEntity);
    }

    private void cleanTouchPoint() {
        if (mPointTracks.size() != 0) {
            mPointTracks.clear();
        }
    }

    public void doReplay() {
        //释放掉上次Bitmap
        if (mPreviousBitmap != null) {
            mPreviousBitmap.recycle();
            mPreviousBitmap = null;
        }
        //新建绘画路径
        Path path = new Path();
        //新建Bitmap 缓存
        Bitmap bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        //新建画布
        Canvas canvas = new Canvas(bitmap);
        //遍历每个经过的坐标点
        for (int i = 0; i < mPointTracks.size(); i++) {

            float localX = mPointTracks.get(i).getPointX();
            float localY = mPointTracks.get(i).getPointY();
            int pointFlag = mPointTracks.get(i).getPointFlag();

            if (pointFlag == POINT_NEW_START) {
                //如果需要另起 则move TO
                path.moveTo(localX, localY);
            } else if (pointFlag == POINT_FOLLOWING) {
                //如果是连贯的 则 lineTo
                path.lineTo(localX, localY);
            }
            mCanvas = mHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(path, mPaint);

            mCanvas.drawBitmap(bitmap, 0, 0, null);
            mHolder.unlockCanvasAndPost(mCanvas);
        }
        path.reset();
        //清理点的缓存
        cleanTouchPoint();
        mPreviousBitmap = bitmap;
    }
}
