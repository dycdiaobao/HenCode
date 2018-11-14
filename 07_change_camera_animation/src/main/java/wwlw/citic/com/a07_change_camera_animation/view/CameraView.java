package wwlw.citic.com.a07_change_camera_animation.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wwlw.citic.com.a07_change_camera_animation.Utils;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/26 18:09
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class CameraView extends View {

    private Camera mCamera;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mCamera = new Camera();
        mCamera.rotateX(45);
        mCamera.setLocation(0,0, Utils.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(300,300);
        canvas.rotate(-20);
        canvas.clipRect(-400,-400,400,0);
        canvas.rotate(20);
        canvas.translate(-300,-300);
        canvas.drawBitmap(Utils.getAvatar(getResources(), 400), 100, 100, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(300,300);
        canvas.rotate(-20);
        mCamera.applyToCanvas(canvas);
        canvas.clipRect(-400,0,400,400);
        canvas.rotate(20);
        canvas.translate(-300,-300);
        canvas.drawBitmap(Utils.getAvatar(getResources(), 400), 100, 100, mPaint);
        canvas.restore();
    }
}
