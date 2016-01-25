package com.hengze.hengzemanager.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/17.
 */
public class Time implements Serializable {
  public String year;
  public String month;
  public String day;
  public String hour;
  public String minute;

  public String getTimeStr() {

    return day + month + "\n" + year;
  }

  @Override public String toString() {
    return "time:"+year+" " + month + " "+ day;
  }
}
