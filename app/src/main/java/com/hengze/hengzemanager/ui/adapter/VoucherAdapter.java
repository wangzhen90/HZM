package com.hengze.hengzemanager.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.modle.Time;
import com.hengze.hengzemanager.ui.activity.VoucherDetailActivity;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/1/17.
 */
public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherHolder> {

  public void setInfos(ArrayList<CardVoucherInfo> infos) {
    mInfos = infos;
  }

  ArrayList<CardVoucherInfo> mInfos;
  Context mContext;

  public VoucherAdapter(ArrayList<CardVoucherInfo> infos) {
    mInfos = infos;
  }

  @Override public VoucherAdapter.VoucherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mContext == null) mContext = parent.getContext();

    View view = LayoutInflater.from(mContext).inflate(R.layout.vorcher_list_item, parent, false);

    return new VoucherHolder(view);
  }

  @Override public void onBindViewHolder(VoucherAdapter.VoucherHolder holder, final int position) {

    CardVoucherInfo info = mInfos.get(position);
    Time preTime = null;

    if (info.time == null) {
      info.time = new Time();
      info.time.year = info.operaTime.substring(0, 4);
      info.time.month = info.operaTime.substring(5, 7);
      info.time.day = info.operaTime.substring(8, 10);
    }

    if (position > 0) {
      preTime = mInfos.get(position - 1).time;
    }

    holder.cardId.setText("卡  号:" + (TextUtils.isEmpty(info.cardID) ? "未获得" : info.cardID));
    holder.operator.setText("操作员:" + (TextUtils.isEmpty(info.operator) ? "未获得" : info.operator));
    holder.operateTime.setText(
        "充值时间:" + (TextUtils.isEmpty(info.operaTime) ? "未获得" : info.operaTime.substring(0, 16)));
    holder.totalWater.setText(
        "总计水量:" + (TextUtils.isEmpty(info.totalWater) ? "未获得" : info.totalWater));
    holder.totalFee.setText("总计费用:" + (TextUtils.isEmpty(info.totalFee) ? "未获得" : info.totalFee));
    holder.state.setText("状  态:" + (TextUtils.isEmpty(info.state) ? "未获得" : info.state));

    if (preTime != null) {
      if (preTime.getTimeStr().equals(info.time.getTimeStr())) {
        holder.timeLine.setVisibility(View.INVISIBLE);
      } else {
        holder.timeLine.setVisibility(View.VISIBLE);
        holder.timeLine.setText(setSpannableString(info.time.getTimeStr()));
      }
    } else {
      if (position == 0) {
        holder.timeLine.setVisibility(View.VISIBLE);
        Log.e("test", info.time.toString());
        holder.timeLine.setText(setSpannableString(info.time.getTimeStr()));
      }
    }

    holder.contentView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, VoucherDetailActivity.class);
        intent.putExtra(Constant.CARD_VOUCHER_INFO, mInfos.get(position));
        mContext.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return mInfos == null ? 0 : mInfos.size();
  }

  class VoucherHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.card_id) TextView cardId;
    @Bind(R.id.operor) TextView operator;
    @Bind(R.id.operate_time) TextView operateTime;
    @Bind(R.id.total_water) TextView totalWater;
    @Bind(R.id.total_fee) TextView totalFee;
    @Bind(R.id.state) TextView state;
    @Bind(R.id.time_line) TextView timeLine;
    @Bind(R.id.content_view) CardView contentView;

    public VoucherHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  SpannableStringBuilder setSpannableString(String time) {
    SpannableStringBuilder ssb = new SpannableStringBuilder(time);

    ssb.setSpan(new RelativeSizeSpan(2.0f), 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    ssb.setSpan(new ForegroundColorSpan(0x70000000), 5, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    ssb.setSpan(new RelativeSizeSpan(0.7f), 5, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    return ssb;
  }
}
