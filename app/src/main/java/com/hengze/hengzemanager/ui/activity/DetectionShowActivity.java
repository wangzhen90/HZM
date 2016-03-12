package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellRt;
import com.hengze.hengzemanager.ui.adapter.DetectionAdapter;
import com.hengze.hengzemanager.ui.widget.slideupview.SlidingUpPanelLayout;
import java.util.ArrayList;

public class DetectionShowActivity extends FragmentActivity {
  ArrayList<WellRt> infos;
  @Bind(R.id.back) ImageView back;
  @Bind(R.id.title_content) TextView titleContent;
  @Bind(R.id.listview) ListView listview;
  @Bind(R.id.detail) LinearLayout detail;
  DetectionAdapter adapter;
  @Bind(R.id.expand_view) ImageView expandView;
  @Bind(R.id.collapse_view) ImageView collapseView;
  @Bind(R.id.well_id) TextView wellId;
  @Bind(R.id.well_name) TextView wellName;
  @Bind(R.id.year_number) TextView yearNumber;
  @Bind(R.id.left_wt) TextView leftWt;
  @Bind(R.id.total_use) TextView totalUse;
  @Bind(R.id.status) TextView status;
  @Bind(R.id.net_state) TextView netState;
  @Bind(R.id.collect_time) TextView collectTime;
  @Bind(R.id.sliding_layout) SlidingUpPanelLayout slidingLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detetion_show_fragment);
    ButterKnife.bind(this);

    infos = (ArrayList<WellRt>) getIntent().getSerializableExtra(Constant.DETECTION_DATA);
    initTitle();
    initList();
    initSlidingLayout();
  }

  private void initSlidingLayout() {
    expandView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
      }
    });

    collapseView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
      }
    });
  }

  private void initList() {
    adapter = new DetectionAdapter(infos, null, this);
    listview.setAdapter(adapter);
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setDetail(adapter.getItem(position));
        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED
            || slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
          slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
          slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
      }
    });
  }

  private void initTitle() {
    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    titleContent.setText("实时信息");
  }

  void setDetail(WellRt info) {
    //@Bind(R.id.well_id) TextView wellId;
    //@Bind(R.id.well_name) TextView wellName;
    //@Bind(R.id.year_number) TextView yearNumber;
    //@Bind(R.id.left_wt) TextView leftWt;
    //@Bind(R.id.total_use) TextView totalUse;
    //@Bind(R.id.status) TextView status;
    //@Bind(R.id.net_state) TextView netState;
    //@Bind(R.id.collect_time) TextView collectTime;

    wellId.setText(info.wellID);
    wellName.setText(info.wellName);
    yearNumber.setText(info.yearNumber+"");
    leftWt.setText(info.leftWt + "");
    totalUse.setText(info.totalUse + "");
    status.setText(info.openState == 0 ? "关闭" : "开启");
    netState.setText(info.netState == 0 ? "关闭" : "开启");
    collectTime.setText(info.collectTime);
  }
}
