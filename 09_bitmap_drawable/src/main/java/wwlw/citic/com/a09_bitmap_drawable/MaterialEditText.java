package wwlw.citic.com.a09_bitmap_drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * @创建者 DingDing
 * @创建时间 2018/10/29 14:47
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class MaterialEditText extends android.support.v7.widget.AppCompatEditText {
    private static final float TEXT_SIZE = Utils.dpToPixel(12);
    private static final float TEXT_MARGIN = Utils.dpToPixel(8);
    private static final int TEXT_VERTICAL_OFFSET = (int) Utils.dpToPixel(22);
    private static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dpToPixel(5);
    private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dpToPixel(16);
    float floatingLabelFraction;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect backgroundPadding = new Rect();
    private ObjectAnimator mObjectAnimator;
    private boolean floatingLabelShown;
    private boolean mUseFloatingLabel;

    public MaterialEditText(Context context) {
        super(context);
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        mUseFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true);
        typedArray.recycle();
        onUseFloatingLabelChanged();
        paint.setTextSize(TEXT_SIZE);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUseFloatingLabel) {
                    if (floatingLabelShown && TextUtils.isEmpty(s)) {
                        floatingLabelShown = false;
                        getObjectAnimator().reverse();
                    } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                        floatingLabelShown = true;
                        getObjectAnimator().start();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void onUseFloatingLabelChanged() {
        getBackground().getPadding(backgroundPadding);
        if (mUseFloatingLabel) {
            setPadding(backgroundPadding.left, (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), backgroundPadding.right, backgroundPadding.bottom);
        }else {
            setPadding(backgroundPadding.left, backgroundPadding.top, backgroundPadding.right, backgroundPadding.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET - extraOffset, paint);
    }

    private ObjectAnimator getObjectAnimator() {
        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0f, 1f);
        }
        return mObjectAnimator;
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }
}
