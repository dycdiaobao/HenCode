package wwlw.citic.com.a09_bitmap_drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/29 15:44
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class DrawableView extends View {

    private MeshDrawable mMeshDrawable;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DrawableView(Context context) {
        super(context);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        mMeshDrawable = new MeshDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMeshDrawable.setBounds(100,100,getWidth(),getHeight());
        mMeshDrawable.draw(canvas);
    }
}
