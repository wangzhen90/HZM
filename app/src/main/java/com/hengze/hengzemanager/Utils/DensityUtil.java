package com.hengze.hengzemanager.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DensityUtil {

  public static int dp2px(Context context, float dpValue) {
    float scale = 1.5f;
    if (context != null)
     scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  public static int getScreenW(Context context) {
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }

  public static int getScreenH(Context context) {
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(dm);
    return dm.heightPixels;
  }

  public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }

  public static int px2sp(Context context, float value) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (value / fontScale + 0.5f);
  }

  public static int dp2px(float density, float value) {
    return (int) (0.5F + value * density);
  }

  public static int px2dp(float density, float value) {
    return (int) (0.5F + value / density);
  }

  public static int sp2px(float scaledDensity, float value) {
    return (int) (0.5F + value * scaledDensity);
  }

  public static int px2sp(float fontScale /** scaledDensity */, float value) {
    return (int) (value / fontScale + 0.5f);
  }

  public static final int getStatusHeightByDensity(Context context) {
    int h = 38;
    int density = context.getResources().getDisplayMetrics().densityDpi;

    switch (density) {
      case 120:
        h = 19;
        break;
      case 160:
        h = 25;
        break;
      case 240:
        h = 38;
        break;
      case 320:
        h = 50;
        break;
      case 400:
        h = 63;
        break;
      case 480:
        h = 75;
        break;
      default:
        break;
    }

    return h;
  }

  private static int displayWidth, displayHeight;

  private static void initDisplay(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    ((WindowManager) context.getApplicationContext()
        .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

    displayWidth = dm.widthPixels;

    displayHeight = dm.heightPixels;
  }

  public static final int getDisplayWidth(Context context) {
    if (displayWidth == 0) {
      initDisplay(context);
    }
    return displayWidth;
  }

  public static final int getDisplayHeight(Context context) {
    if (displayHeight == 0) {
      initDisplay(context);
    }
    return displayHeight;
  }

  public static int getStatusBarHeight() {
    return Resources.getSystem()
        .getDimensionPixelSize(
            Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
  }
}