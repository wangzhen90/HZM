package com.hengze.hengzemanager.ui.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
    public void setInfos(ArrayList<WellRt> infos) {
        this.infos = infos;
    }

    ArrayList<WellRt> infos;
    SlideUpListener listener;
    Context mContext;

    public DetectionAdapter(ArrayList<WellRt> infos, SlideUpListener listener, Context context) {
        this.infos = infos;
        listener = listener;
        mContext = context;
    }

    @Override
    public int getCount() {
        return infos != null ? infos.size() : 0;
    }

    @Override
    public WellRt getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
//        holder.status.setText((info.openState == 0 ? "关机" : "开机") + "/" + (info.netState == 0 ? "在线" : "离线"));
        holder.status.setText(getSpannal(info.openState, info.netState));
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

    SpannableStringBuilder getSpannal(int openStatus, int netStatus) {

        String content = (openStatus == 0 ? "关闭" : "开启")+   "/" +  (netStatus == 0 ? "离线" : "在线");
        int color1 = openStatus == 0 ? 0xffff0000 : 0xff00ff00;
        int color2 = netStatus == 0 ? 0xffff0000 : 0xff00ff00;
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        ssb.setSpan(new ForegroundColorSpan(color1), 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(color2), 3, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return ssb;

    }
}
