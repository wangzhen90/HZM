package com.hengze.hengzemanager.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellRt;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/12.
 */
public class DetectionAdapter extends BaseAdapter {
  ArrayList<WellRt> infos;
  SlideUpListener listener;
  Context mContext;

  public DetectionAdapter(ArrayList<WellRt> infos, SlideUpListener listener, Context context) {
    this.infos = infos;
    listener = listener;
    mContext = context;
  }

  @Override public int getCount() {
    return infos != null ? infos.size() : 0;
  }

  @Override public WellRt getItem(int position) {
    return infos.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    View view;
    DetectionHolder holder;
    if (convertView == null) {
      view = View.inflate(mContext, R.layout.layout_detection_list_item, null);
      holder = new DetectionHolder(view);
    } else {
      view = convertView;
      holder = (DetectionHolder) view.getTag();
    }
    WellRt info = getItem(position);
    holder.time.setText(info.collectTime);
    holder.left_water.setText(info.yearNumber + "");
    holder.status.setText((info.openState == 0 ? "关机" : "开机"));
    holder.device_id.setText(info.wellID + "");

    if (position % 2 == 1) {
      view.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
    } else {
      view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }

    return view;
  }

  public interface SlideUpListener {
    void expand();

    void collapse();
  }

  class DetectionHolder {

    public DetectionHolder(View view) {

      device_id = (TextView) view.findViewById(R.id.device_id);
      left_water = (TextView) view.findViewById(R.id.left_water);
      status = (TextView) view.findViewById(R.id.status);
      time = (TextView) view.findViewById(R.id.time);

      view.setTag(this);
    }

    TextView device_id;
    TextView left_water;
    TextView status;
    TextView time;
  }
}
