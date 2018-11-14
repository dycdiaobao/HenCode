package wwlw.citic.com.scalable.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import wwlw.citic.com.scalable.Utils;

/**
 * @创建者 DingDing
 * @创建时间 2018/11/13 17:00
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;
    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;
    boolean big;
    ObjectAnimator scaleAnimator;
    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private HenGestureListener mHenGestureListener = new HenGestureListener();
    private HenScaleListener mHenScaleListener = new HenScaleListener();
    private GestureDetectorCompat mGestureDetectorCompat;
    private ScaleGestureDetector mScaleGestureDetector;
    private HenFlingRunner mHenFlingRunner = new HenFlingRunner();
    private float currentScale;
    private float smallScale;
    private float bigScale;
    private OverScroller mOverScroller;

    public ScalableImageView(Context context) {
        super(context);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        mGestureDetectorCompat = new GestureDetectorCompat(context, mHenGestureListener);
        mOverScroller = new OverScroller(context);
        mScaleGestureDetector = new ScaleGestureDetector(context, mHenScaleListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touchEvent = mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress()) {
            touchEvent = mGestureDetectorCompat.onTouchEvent(event);
        }
        return touchEvent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = ((float) getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = ((float) getHeight() - bitmap.getHeight()) / 2;
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private float getCurrentScale() {
        return currentScale;
    }

    private void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    class HenGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big) {
                mOverScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);
                postOnAnimation(mHenFlingRunner);
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
                fixOffsets();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }
    }

    private void fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    class HenFlingRunner implements Runnable {
        @Override
        public void run() {
            if (mOverScroller.computeScrollOffset()) {
                offsetX = mOverScroller.getCurrX();
                offsetY = mOverScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    class HenScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float mScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = mScale * detector.getScaleFactor();
            if (currentScale < smallScale) {
                currentScale = smallScale;
            }
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
        }
    }
}
