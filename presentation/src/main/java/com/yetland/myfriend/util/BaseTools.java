package com.yetland.myfriend.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by 亮 on 2016/3/4.
 */
public class BaseTools {
    public static final int getWindowsWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static final int getWindowsHeight(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
