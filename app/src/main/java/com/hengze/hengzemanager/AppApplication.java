package com.hengze.hengzemanager;

import android.app.Application;

/**
 * Created by Administrator on 2016/1/13.
 */
public class AppApplication extends Application {

  private static AppApplication instance;


  public static AppApplication getInstance(){

    return instance;

  }
  @Override public void onCreate() {
    super.onCreate();
    instance = this;
  }
}
