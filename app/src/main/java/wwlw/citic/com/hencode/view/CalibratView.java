package wwlw.citic.com.hencode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wwlw.citic.com.hencode.util.DensityUtil;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/22 13:49
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class CalibratView extends View {
    private Paint mPaint = new Paint();
    private int radius = DensityUtil.dip2px(150);
    private int angle = 120;
    private Path mPath = new Path();
    private static final float lenght = DensityUtil.dip2px(100);
    private PathDashPathEffect mPathDashPathEffect;

    public CalibratView(Context context) {
        super(context);
    }

    public CalibratView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalibratView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DensityUtil.dip2px(2));
        mPath.addRect(0, 0, DensityUtil.dip2px(2), DensityUtil.dip2px(10), Path.Direction.CW);
        Path arc = new Path();
        RectF rectF = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
        arc.addArc(rectF, 90 + angle / 2, 360 - angle);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        float length = pathMeasure.getLength();
        mPathDashPathEffect = new PathDashPathEffect(mPath, (length - DensityUtil.dip2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasArc(canvas);
        canvasDegree(canvas);
        canvasLine(canvas);
    }

    private void canvasLine(Canvas canvas) {

        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * lenght + getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * lenght + getHeight() / 2,
                mPaint
        );
    }


    private void canvasArc(Canvas canvas) {
        canvas.save();
        RectF rectF = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
        canvas.drawArc(rectF, 90 + angle / 2, 360 - angle, false, mPaint);
        canvas.restore();
    }

    private void canvasDegree(Canvas canvas) {
        mPaint.setPathEffect(mPathDashPathEffect);
        RectF rectF = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
        canvas.drawArc(rectF, 90 + angle / 2, 360 - angle, false, mPaint);
        mPaint.setPathEffect(null);
    }

    float getAngleFromMark(int mark) {
        return (90 + (float) angle / 2 + (360 - (float) angle) / 20 * mark);
    }
}
