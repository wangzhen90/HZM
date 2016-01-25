package com.hengze.hengzemanager.ui.adapter;

import android.content.Context;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import java.util.ArrayList;
import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/1/16.
 */
public class CardDetailAdapter extends RecyclerView.Adapter<CardDetailAdapter.CardDetailHolder> {
  public ArrayList<CardConsumeInfo> mInfos;
  public Context mContext;

  public CardDetailAdapter(ArrayList<CardConsumeInfo> infos) {
    mInfos = infos;
  }

  @Override
  public CardDetailAdapter.CardDetailHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
    mContext = viewGroup.getContext();
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.card_detail_list_item, viewGroup, false);
    return new CardDetailHolder(view);
  }

  @Override
  public void onBindViewHolder(CardDetailAdapter.CardDetailHolder viewHolder, int position) {
    if (viewHolder != null) {
      CardConsumeInfo info = mInfos.get(position);
      viewHolder.startTime.setText("开始时间：" + info.startTime.substring(0,16));

      setSpannal(viewHolder.startWater, "开始水量: " + info.startWater + "m3");
      viewHolder.endTime.setText("结束时间：" + info.endTime.substring(0,16));
      setSpannal(viewHolder.endWater, "结束水量: " + info.endWater + "m3");
      viewHolder.userWater.setText("使用水量：" + info.useWater);
      viewHolder.wellInfo.setText("机井名称：" + info.wellName + "（" + info.wellID + ")");
    }
  }

  @Override public int getItemCount() {
    return mInfos != null ? mInfos.size() : 0;
  }

  SpannableStringBuilder setSpannal(TextView tv, String content) {
    SpannableStringBuilder ssb = new SpannableStringBuilder(content);

    Parcel parcel = Parcel.obtain();
    parcel.writeInt(3);

    ssb.setSpan(new SuperscriptSpan(parcel), content.length() - 1, content.length(),
        Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    ssb.setSpan(new RelativeSizeSpan(0.6f), content.length() - 1, content.length(),
        Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    tv.setText(ssb);
    parcel.recycle();
    return ssb;
  }

  class CardDetailHolder extends RecyclerView.ViewHolder {

    public CardDetailHolder(View itemView) {


      super(itemView);
      startTime = (TextView) itemView.findViewById(R.id.start_time);
      endTime = (TextView) itemView.findViewById(R.id.end_time);
      userWater = (TextView) itemView.findViewById(R.id.use_water);
      wellInfo = (TextView) itemView.findViewById(R.id.well_name);
      startWater = (TextView) itemView.findViewById(R.id.start_water);
      endWater = (TextView) itemView.findViewById(R.id.end_water);
    }

    public TextView startTime;
    public TextView endTime;
    public TextView userWater;
    public TextView wellInfo;
    public TextView startWater;
    public TextView endWater;
  }
}
