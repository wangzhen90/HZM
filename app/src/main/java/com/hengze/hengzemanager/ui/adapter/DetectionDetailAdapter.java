package com.hengze.hengzemanager.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.DetectDetailItem;

import java.util.ArrayList;

/**
 * Created by dell on 2016/6/14.
 */
public class DetectionDetailAdapter extends BaseAdapter {
    public void setData(ArrayList<DetectDetailItem> data) {
        this.data = data;
    }

    ArrayList<DetectDetailItem> data;
    Context mContext;

    public DetectionDetailAdapter(Context context, ArrayList<DetectDetailItem> data) {
        mContext = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public DetectDetailItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_list_detection, null);

            holder = new ViewHolder();
            holder.key = (TextView) view.findViewById(R.id.key);
            holder.value = (TextView) view.findViewById(R.id.value);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if(position%2 == 0){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            view.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        }
        holder.key.setText(getItem(position).key);
        holder.value.setText(getItem(position).value);

        return view;
    }

    class ViewHolder {

        TextView key;
        TextView value;

    }

}
