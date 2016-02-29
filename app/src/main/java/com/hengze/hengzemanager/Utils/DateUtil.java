package com.hengze.hengzemanager.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/28.
 */
public class DateUtil {
  public static String getTime(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    return format.format(date);
  }
}
