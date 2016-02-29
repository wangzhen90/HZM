package com.hengze.hengzemanager.net;

import com.hengze.hengzemanager.modle.AddressNode;
import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.modle.UserWrapper;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import com.hengze.hengzemanager.modle.WellDetail;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/1/10.
 */
public interface Api {

  //登陆
  @POST("/login.an") void login(@Body UserWrapper user, Callback<String> callback);

  //http://118.183.190.176:8082/HzmoFrame/card.an?cardID=123   查询用水记录
  @GET("/card.an") void queryWaterConsumeByCardID(@Query("cardID") String wellID,
      Callback<ArrayList<CardConsumeInfo>> callback);

  //http://118.183.190.176:8082/HzmoFrame/cardID.an?cardID=123  查询充值记录
  @GET("/cardID.an") void queryVorcherByCardID(@Query("cardID") String wellID,
      Callback<ArrayList<CardVoucherInfo>> callback);

  //    http://118.183.190.176:8082/HzmoFrame/find.wellan?wellID=0994010101002

  @GET("/find.wellan") void queryWellDetail(@Query("wellID") String wellID,
      Callback<ArrayList<WellDetail>> callback);

  //http://118.183.190.176:8082/HzmoFrame/org.wellan
  @GET("/org.wellan") void queryAddress(Callback<ArrayList<AddressNode>> callback);

  //http提交地址:118.183.190.176:8082/HzmoFrame/del.wellan
  @GET("/del.wellan") void deleteWell(@Query("wellID") String wellID, Callback<String> callback);

  //http提交地址:118.183.190.176:8082/HzmoFrame/add.wellan
  @GET("/add.wellan") void addWell(@Query("wellID") String wellID, Callback<String> callback);

  @POST("/add.wellan") void addWellInfo(@Body() WellDetail wellDetail,
      Callback<WellDetail> callback);
}
