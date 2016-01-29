package com.hengze.hengzemanager.modle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/1/27.
 */
public class WellDetail implements Serializable{
    public String WellID;//机井名称
    public String DevID;//设备号
    public String CezhanID;//DTU编号
    public String WellName;//机井名称
    public Double Lat;//经度
    public Double Lnt;//纬度
    public Date BuildYear;//创建时间
    public String Qsxkzh;//
    public Double WellDeep;//机井深度
    public Double WaterDeep;//
    public String WaterQuality;//水质
    public int PumpPower;//水泵功率
    public Double PerWtOut;//单位出水量
    public Double PerEleOut;//单位功耗
    public int YearNumber;//年度水表底数
    public String ManagerName;//管理员
    public String ManagerTel;//电话
    public String NetType;//网络状态

    public String SimID;//sim卡号

    public String Remark;//


}
