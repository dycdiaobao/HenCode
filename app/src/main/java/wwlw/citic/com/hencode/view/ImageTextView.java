package wwlw.citic.com.hencode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wwlw.citic.com.hencode.R;
import wwlw.citic.com.hencode.util.DensityUtil;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/25 19:11
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class ImageTextView extends View {
    private static final float IMAGE_WIDTH = DensityUtil.dip2px(100);
    private static final float IMAGE_OFFSET = DensityUtil.dip2px(80);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor.";
    float[] cutWidth = new float[1];
    private float verticalHeight;
    private int mLength;
    private float maxWidth;


    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = getAvatar((int) DensityUtil.dip2px(100));
        paint.setTextSize(DensityUtil.dip2px(14));
        paint.getFontMetrics(fontMetrics);
        mLength = text.length();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, IMAGE_OFFSET, paint);
        verticalHeight = Math.abs(fontMetrics.top);
        for (int start = 0; start < mLength; ) {
            float top = verticalHeight + fontMetrics.top;
            float bottom = verticalHeight + fontMetrics.bottom;
            if (top > IMAGE_OFFSET + IMAGE_WIDTH || bottom < IMAGE_OFFSET) {
                maxWidth = getWidth();
            } else {
                maxWidth = getWidth() - IMAGE_WIDTH - 20;
            }
            int count = paint.breakText(text, start, mLength, true, maxWidth, cutWidth);
            canvas.drawText(text, start, start + count, 0, verticalHeight, paint);
            start += count;
            verticalHeight += paint.getFontSpacing();
        }

    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }
}
