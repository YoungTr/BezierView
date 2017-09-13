package com.youngtr.library.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/9/12.
 */

public class ScreenUtil {

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
