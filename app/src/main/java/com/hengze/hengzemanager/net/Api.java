package com.hengze.hengzemanager.net;

import com.hengze.hengzemanager.modle.AddressNode;
import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.modle.UserWrapper;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import com.hengze.hengzemanager.modle.WellDetail;

import java.util.ArrayList;

import java.util.List;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/1/10.
 */
public interface Api {

    //登陆
    @POST("/login.an")
    void login(@Body UserWrapper user, Callback<String> callback);

    //http://118.183.190.176:8082/HzmoFrame/card.an?cardID=123   查询用水记录
    @GET("/card.an")
    void queryWaterConsumeByCardID(@Query("cardID") String wellID,
                                   Callback<ArrayList<CardConsumeInfo>> callback);

    //http://118.183.190.176:8082/HzmoFrame/cardID.an?cardID=123  查询充值记录
    @GET("/cardID.an")
    void queryVorcherByCardID(@Query("cardID") String wellID,
                              Callback<ArrayList<CardVoucherInfo>> callback);

    //    http://118.183.190.176:8082/HzmoFrame/find.wellan?wellID=0994010101002

    @GET("/find.wellan")
    void queryWellDetail(@Query("wellID") String wellID,
                         Callback<ArrayList<WellDetail>> callback);

    //http://118.183.190.176:8082/HzmoFrame/org.wellan
    @GET("/org.wellan")
    void queryAddress(Callback<ArrayList<AddressNode>> callback);

    //http提交地址:118.183.190.176:8082/HzmoFrame/del.wellan
    @GET("/del.wellan")
    void deleteWell(@Query("wellID") String wellID, Callback<String> callback);

    //http提交地址:118.183.190.176:8082/HzmoFrame/add.wellan
    @GET("/add.wellan")
    void addWell(@Query("wellID") String wellID, Callback<String> callback);

    @FormUrlEncoded
    @POST("/add.wellan")
    void addWellInfo(@Field("wellID") String wellID,
                     @Field("devID") String devID,
                     //@Field("cezhanID") String cezhanID,
                     @Field("wellName") String wellName,
                     @Field("lat") double lat,
                     @Field("lnt") double lnt,
                     @Field("buildYear") String buildYear,
                     //@Field("qsxkzh") String qsxkzh,
                     //@Field("wellDeep") double wellDeep,
                     //@Field("waterDeep") double waterDeep,
                     //@Field("waterQuality") String waterQuality,
                     //@Field("pumpPower") double pumpPower,
                     //@Field("perWtOut") double perWtOut,
                     //@Field("perEleOut") double perEleOut,
                     //@Field("yearNumber") int yearNumber,
                     //@Field("managerName") String managerName,
                     //@Field("managerTel") String managerTel,
                     //@Field("netType") String netType,
                     //@Field("simID") String simID,
                     //@Field("remark") String remark,
                     Callback<String[]> callback);

    //118.183.190.176:8082/HzmoFrame/modify.wellan
    @POST("/modify.wellan")
    void testUpdate(@Body WellDetail wellDetail,
                    Callback<WellDetail> callback);

    @FormUrlEncoded
    @POST("/modify.wellan")
    void testUpdate2(@Field("wellName") String wellName,
                     @Field("wellID") String wellID,
                     @Field("devID") String devID,
                     Callback<WellDetail> callback);
}
