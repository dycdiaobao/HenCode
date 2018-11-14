package wwlw.citic.com.hencode.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wwlw.citic.com.hencode.util.DensityUtil;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/25 16:48
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class SportsView extends View {
    private Paint mPaint = new Paint();
    private static final float RING_WIDTH = DensityUtil.dip2px(20);
    private static final float RADIUS = DensityUtil.dip2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private RectF mRectF;
    private String text = "abbf";
    private Rect mRect;
    private Paint.FontMetrics mFontMetrics;


    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {

        mRectF = new RectF();
        mRect = new Rect();
        mFontMetrics = new Paint.FontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPaint.setStrokeWidth(RING_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(CIRCLE_COLOR);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setColor(HIGHLIGHT_COLOR);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectF.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
        canvas.drawArc(mRectF, -90, 225, false, mPaint);
        canvas.restore();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(DensityUtil.dip2px(100));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getFontMetrics(mFontMetrics);
        System.out.println("mFontMetrics.ascent:"+mFontMetrics.ascent+"mFontMetrics.descent:"+mFontMetrics.descent+
                "mFontMetrics.top:"+mFontMetrics.top+"mFontMetrics.bottom:"+mFontMetrics.bottom+"mFontMetrics.leading:"+mFontMetrics.leading);
        float height = mFontMetrics.ascent + mFontMetrics.descent;
        float fontSpacing = mPaint.getFontSpacing();
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        int left = mRect.left;
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - (height / 2), mPaint);
    }
}
