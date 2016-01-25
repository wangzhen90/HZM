package com.hengze.hengzemanager.Utils;

import android.view.Gravity;
import com.hengze.hengzemanager.AppApplication;
import com.hengze.hengzemanager.ui.widget.XToast;

public class ToastUtils {

  public static void showToast(String msg) {
    XToast.create(AppApplication.getInstance().getApplicationContext(), msg)
        .withGravity(Gravity.CENTER, 0, 0).withCover(true)
        // 覆盖之前的
        .withAnimation(XToast.Anim.FLY).withDuration(XToast.Duration.SHORT).show();
  }

  public static void showToast(int resId) {
    String message = AppApplication.getInstance().getResources().getString(resId);
    XToast.create(AppApplication.getInstance().getApplicationContext(), message)
        .withGravity(Gravity.CENTER, 0, 0).withCover(true)
        // 覆盖之前的
        .withAnimation(XToast.Anim.FLY).withDuration(XToast.Duration.SHORT).show();
  }
}
