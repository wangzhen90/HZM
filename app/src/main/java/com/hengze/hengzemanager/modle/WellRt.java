package com.hengze.hengzemanager.modle;

import java.io.Serializable;

public class WellRt implements Serializable {

//  abnomal: 0,
//  alarm: "正常",
//  circle: 0,
//  doolState: 0,
//  downLine: 0,
//  lat: 0,
//  lnt: 0,
//  overLine: 0,
//  perEleOut: 0,
//  perWtOut: 100,
//  showAlarm: "",
//  totalDistri: 0,
//  totalLeft: 1522,
//  totalPower: 0,
//  totalWell: 0,
//  useWaterTime: 0,
//  waterMeterState: 0,



  public int abnomal;//0/1
  public String alarm;//告警信息
  public double circle;//管径
  public int doolState;//箱门状态   0 关门  1开门
  public int downLine;
  public long lat;//经度
  public long lnt;//纬度
  public int overLine;
  public double perEleOut;//功率
  public double perWtOut;//流量

  public String showAlarm;
  public double totalDistri;//总配水
  public double totalLeft;//剩余配水量
  public double totalPower;//累计电量
  public double totalWell;
  public double useWaterTime;//放水用时
  public int waterMeterState;//水表状态 0正常 1异常


  public String wellID;//机井编号
  public String wellName;//机井名称
  public String devID;//设备号
  public String cezhanID; //DTU号
  public String collectTime;////采集时间
  public int yearNumber;
  public double curBase;//总累计
  public double leftWt;//用户余量
  public double totalUse;
  public int openState;//设备状态 0正常 1异常
  public int netState;//网络状态   0 离线  1在线



}
