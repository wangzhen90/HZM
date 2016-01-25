package com.hengze.hengzemanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.activity.CardConsumeActivity;
import com.hengze.hengzemanager.ui.activity.CardVorcherRecordActivity;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QueryRecordFragement extends Fragment implements View.OnClickListener {

  private static final String TAG = QueryRecordFragement.class.getSimpleName();
  @Bind(R.id.water_well_id) EditText waterWellId;
  @Bind(R.id.water_well_name) EditText waterWellName;
  @Bind(R.id.query_water) TextView queryWater;
  @Bind(R.id.vorcher_well_name) EditText vorcherWellName;
  @Bind(R.id.query_voucher) TextView queryVoucher;
  @Bind(R.id.voucher_card_id) EditText voucherCardId;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_query_record_fragement, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    queryWater.setOnClickListener(this);
    queryVoucher.setOnClickListener(this);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {

      case R.id.query_water:
        String card_id = waterWellId.getText().toString().trim();

        if (TextUtils.isEmpty(card_id)) {

          ToastUtils.showToast("请输入卡号");
          return;
        }

        ApiClient.get().api.queryWaterConsumeByCardID(card_id,
            new Callback<ArrayList<CardConsumeInfo>>() {
              @Override
              public void success(ArrayList<CardConsumeInfo> cardConsumeInfos, Response response) {
                Log.e(TAG, "查询用水量成功,status:" + cardConsumeInfos.size());
                waterWellId.setText("");
                toConsumeInfoDetail(cardConsumeInfos);
              }

              @Override public void failure(RetrofitError error) {
                Log.e(TAG, "查询用水量失败，error:" + error.getMessage() + ",kind:" + error.getKind());
              }
            });

        break;

      case R.id.query_voucher:
        String voucher_cardID = voucherCardId.getText().toString().trim();
        if (TextUtils.isEmpty(voucher_cardID)) {
          ToastUtils.showToast("请输入卡号");
          return;
        }

        ApiClient apiClient = ApiClient.get();

        apiClient.api.queryVorcherByCardID(voucher_cardID,
            new Callback<ArrayList<CardVoucherInfo>>() {
              @Override
              public void success(ArrayList<CardVoucherInfo> cardVoucherInfos, Response response) {
                Log.e(TAG, "查询充值记录成功,status:" + cardVoucherInfos.size());
                toVoucherInfoDetail(cardVoucherInfos);
              }

              @Override public void failure(RetrofitError error) {
                Log.e(TAG, "查询充值记录失败，error:" + error.getMessage() + ",kind:" + error.getKind());
              }
            });

        break;
    }
  }

  void toConsumeInfoDetail(ArrayList infos) {

    Intent intent = new Intent(getActivity(), CardConsumeActivity.class);
    intent.putExtra(Constant.CARD_CONSUME_INFO, infos);
    startActivity(intent);
  }

  void toVoucherInfoDetail(ArrayList infos) {

    Intent intent = new Intent(getActivity(), CardVorcherRecordActivity.class);
    intent.putExtra(Constant.CARD_VOUCHER_INFO, infos);
    startActivity(intent);
  }


}
