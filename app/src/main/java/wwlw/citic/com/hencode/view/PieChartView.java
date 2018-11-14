package wwlw.citic.com.hencode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import wwlw.citic.com.hencode.util.DensityUtil;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/23 13:53
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class PieChartView extends View {
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();
    private int round = DensityUtil.dip2px(150);
    private int[] angles = {10,30,30,100,130,60};
    private int[] colors = {Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
            Color.parseColor("#009688"), Color.parseColor("#FF8F00"),Color.parseColor("#C2185B"),Color.parseColor("#009688")};
    private int cuttentAngles;
    private int clickAngles;
    private int selectIndex;
    private static final int LENGTH = DensityUtil.dip2px(20);
    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(getWidth() / 2 - round, getHeight() / 2 - round, getWidth() / 2 + round, getHeight() / 2 + round);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < angles.length; i++) {
            mPaint.setColor(colors[i]);
            canvas.save();
            if (i == selectIndex) {
                canvas.translate((float) Math.cos(Math.toRadians(cuttentAngles + angles[i] / 2)) * LENGTH,
                        (float) Math.sin(Math.toRadians(cuttentAngles + angles[i] / 2)) * LENGTH);
            }
            canvas.drawArc(mRectF, cuttentAngles, angles[i], true, mPaint);
            canvas.restore();

            cuttentAngles += angles[i];
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX() - getWidth() / 2;
            float y = event.getY() - getHeight() / 2;
            int lenght = (int) Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
            float touchAngle = 0;
            clickAngles = 0;
            if (lenght > round) {
                return super.onTouchEvent(event);
            }
            if (y > 0 && x < 0) {
                touchAngle += 180;
            } else if (y < 0 && x < 0) {
                touchAngle += 180;
            } else if (y < 0 && x > 0) {
                touchAngle += 360;
            }
            touchAngle += Math.toDegrees(Math.atan(y / x));
            for (int i = 0; i < angles.length; i++) {
                clickAngles += angles[i];
                if (touchAngle < clickAngles) {
                    selectIndex = i;
                    invalidate();
                    break;
                }
            }

        }

        return super.onTouchEvent(event);
    }
}
