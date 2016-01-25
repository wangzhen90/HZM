package com.hengze.hengzemanager.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/17.
 */
public class CardVoucherInfo implements Serializable {

  public String autoID;
  public String cardID;
  public String cdeFee;//超定额费用
  public String cdeWater;//超定额水量
  public String denFee;//定额内费用
  public String denLeftWater;//剩余定额水量
  public String denWater;//
  public String operaTime;
  public String operator;
  public String power;
  public String state;//
  public String totalFee;
  public String totalWater;
  public String usedWater;
  public String wellID;
  public String wellName;

  public Time time;
}
