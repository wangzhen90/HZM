package com.hengze.hengzemanager.modle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/1/27.
 */
public class WellDetail implements Serializable {
    public String wellID;//机井名称
    public String devID;//设备号
    public String cezhanID;//DTU编号
    public String wellName;//机井名称
    public double lat;//经度
    public double lnt;//纬度
    public String buildYear;//创建时间
    public String qsxkzh;//
    public double wellDeep;//机井深度
    public double waterDeep;//
    public String waterQuality;//水质
    public int pumpPower;//水泵功率
    public double perWtOut;//单位出水量
    public double perEleOut;//单位功耗
    public int yearNumber;//年度水表底数
    public String managerName;//管理员
    public String managerTel;//电话
    public String netType;//网络状态

    public String simID;//sim卡号

    public String remark;//

    public double circle;//管径
    public double yc;//扬程

}
