package wwlw.citic.com.a10_layout.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 DingDing
 * @创建时间 2018/11/1 14:52
 * @描述 ${TODO}
 * @更新者 $Auther$
 * @更新时间 $Date$
 * 更新描述   ${TODO}
 */
public class TagLayout extends ViewGroup {
    List<Rect> childrenBounds = new ArrayList<>();

    private boolean isLineVerticalCenter = false;

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int maxHLineHeight = 0;
        int lineWidthUsed = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > widthSize) {
                heightUsed += maxHLineHeight;
                maxHLineHeight = 0;
                lineWidthUsed = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            } else {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(lineWidthUsed, widthUsed);
            maxHLineHeight = Math.max(maxHLineHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(widthUsed, heightUsed + maxHLineHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childrenBounds.get(i);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet p) {
        return new MarginLayoutParams(getContext(), p);
    }


}
