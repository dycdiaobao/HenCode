package wwlw.citic.com.hencode.util;

import android.content.Context;
import android.content.res.Resources;


/**
 * Android中sp,dp和px之间进行转换,可以获取屏幕宽高像素点
 *
 * @author raoh
 * @data: 2014-1-2 下午5:06:00
 * @version: V1.0
 */
public class DensityUtil {

    private static int screenWidth, screenHeight;

    /**
     * 根据手机的分辨率从dp转换成px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从px(像素)转换成dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽分辨率
     */
    public static int getDisplayWidth(Context context) {
        if (screenWidth <= 0) {
            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    /**
     * 获取屏幕宽分辨率
     */

    /**
     * 获取屏幕高分辨率
     */
    public static int getDisplayHeight(Context context) {
        if (screenHeight <= 0) {
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取屏幕高分辨率
     */

}
