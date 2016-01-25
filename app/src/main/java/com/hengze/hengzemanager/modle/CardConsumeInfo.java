package com.hengze.hengzemanager.modle;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/12.
 */
public class CardConsumeInfo implements Serializable {
  public String cardID;//(卡号)
  public String startTime;//(启泵时间)
  public String endTime;//(关泵时间)
  public String startWater;//(开始水量)
  public String endWater;//(剩余水量)    做个饼状图
  public String useWater;//(用水量)
  public String wellID;//(所属机井编号)
  public String wellName;//(所属机井名称)
  public String userID;//(用户ID)
  public String userName;//(农户名)
  public String userTel;//(农户电话)
  public String userAddress;//(地址)
  public String phone;

}
