package wwlw.citic.com.a09_bitmap_drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/29 21:31
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class LikeView extends View {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mCurNum = 9;
    private int num = mCurNum;
    private float moveY =  Utils.dpToPixel(20);
    private ObjectAnimator mObjectAnimator;

    public LikeView(Context context) {
        super(context);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        mPaint.setTextSize(Utils.dpToPixel(14));
        mObjectAnimator = ObjectAnimator.ofFloat(this,"translationY",0,-moveY);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurNum = num;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator.ofFloat(this,"translationY",-moveY,0).setDuration(10).start();


            }
        });
        mObjectAnimator.setDuration(500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mCurNum+"",getWidth()/2,getHeight()/2,mPaint);
    }

    public void setNumber(int num){
        this.num = num;
        mObjectAnimator.start();
    }
}
