package wwlw.citic.com.a09_bitmap_drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/29 14:59
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class MeshDrawable extends Drawable {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final int INTERVAL = (int) Utils.dpToPixel(80);

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (int i = getBounds().left; i < getBounds().right; i += INTERVAL) {
            for (int j = getBounds().top; j < getBounds().bottom; j+= INTERVAL) {
                canvas.drawLine(getBounds().left,j,getBounds().right,j,mPaint);
                canvas.drawLine(i,getBounds().top,i,getBounds().bottom,mPaint);
            }
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public int getAlpha() {
        return mPaint.getAlpha();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return getAlpha() == 0 ? PixelFormat.TRANSPARENT :
                getAlpha() == 0xff ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
    }
}
