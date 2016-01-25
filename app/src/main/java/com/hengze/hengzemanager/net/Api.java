package com.hengze.hengzemanager.net;

import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.modle.UserWrapper;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.Body;
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

}
