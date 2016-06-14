package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.modle.DetectDetailItem;
import com.hengze.hengzemanager.modle.WellRt;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.adapter.DetectionAdapter;
import com.hengze.hengzemanager.ui.adapter.DetectionDetailAdapter;
import com.hengze.hengzemanager.ui.widget.slideupview.SlidingUpPanelLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetectionShowActivity extends FragmentActivity {
    ArrayList<WellRt> infos;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.listview)
    ListView listview;

    DetectionAdapter adapter;
    @Bind(R.id.expand_view)
    ImageView expandView;
    @Bind(R.id.collapse_view)
    ImageView collapseView;
//    @Bind(R.id.well_id)
//    TextView wellId;
//    @Bind(R.id.well_name)
//    TextView wellName;
//    @Bind(R.id.year_number)
//    TextView yearNumber;
//    @Bind(R.id.left_wt)
//    TextView leftWt;
//    @Bind(R.id.total_use)
//    TextView totalUse;
//    @Bind(R.id.status)
//    TextView status;
//    @Bind(R.id.net_state)
//    TextView netState;
//    @Bind(R.id.collect_time)
//    TextView collectTime;
    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    String areaID;
    @Bind(R.id.listview_detail)
    ListView listViewDetail;
    DetectionDetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detetion_show_fragment);
        ButterKnife.bind(this);

        infos = (ArrayList<WellRt>) getIntent().getSerializableExtra(Constant.DETECTION_DATA);
        areaID = getIntent().getStringExtra(Constant.AREA_ID);
        initTitle();
        initList();
        initSlidingLayout();
        initSwipeRefresh();
        initDetail();
    }

    private void initDetail() {

        detailAdapter = new DetectionDetailAdapter(this, null);
        listViewDetail.setAdapter(detailAdapter);

    }

    private void initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (areaID != null) {
                    swipeRefresh.setRefreshing(true);
                    ApiClient.get().api.queryInfoByArea(areaID, new Callback<ArrayList<WellRt>>() {
                        @Override
                        public void success(ArrayList<WellRt> wellRts, Response response) {
                            if (!wellRts.isEmpty()) {
                                adapter.setInfos(wellRts);
                                adapter.notifyDataSetChanged();
                            }

                            swipeRefresh.setRefreshing(false);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            swipeRefresh.setRefreshing(false);
                            ToastUtils.showToast("刷新失败，请稍后重试");
                        }
                    });
                } else {
                    ToastUtils.showToast("刷新失败，请稍后重试");
                }
            }
        });

    }

    private void initSlidingLayout() {
        expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        collapseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });
    }

    private void initList() {
        adapter = new DetectionAdapter(infos, null, this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setDetail(adapter.getItem(position));
//                if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED
//                        || slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
//                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
//                } else if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
//                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
//                }

                if (slidingLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });
    }

    private void initTitle() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleContent.setText("实时信息");
    }

    ArrayList<DetectDetailItem> details = new ArrayList<>();

    void setDetail(WellRt info) {
        //@Bind(R.id.well_id) TextView wellId;
        //@Bind(R.id.well_name) TextView wellName;
        //@Bind(R.id.year_number) TextView yearNumber;
        //@Bind(R.id.left_wt) TextView leftWt;
        //@Bind(R.id.total_use) TextView totalUse;
        //@Bind(R.id.status) TextView status;
        //@Bind(R.id.net_state) TextView netState;
        //@Bind(R.id.collect_time) TextView collectTime;

        details.clear();

        details.add(new DetectDetailItem("机井名称:", info.wellName));
        details.add(new DetectDetailItem("机井编号:", info.wellID));
        details.add(new DetectDetailItem("箱门状态:", info.doolState == 0 ? "关闭" : "开启"));
        details.add(new DetectDetailItem("网络状态:", info.netState == 0 ? "在线" : "离线"));
        details.add(new DetectDetailItem("水表状态:", info.waterMeterState == 0 ? "关闭" : "开启"));
        details.add(new DetectDetailItem("设备状态:", info.openState == 0 ? "关闭" : "开启"));
        details.add(new DetectDetailItem("用户余量:", info.leftWt + ""));
        details.add(new DetectDetailItem("总  配  水:", info.totalDistri + ""));
        details.add(new DetectDetailItem("总  累  计:", info.curBase + ""));
        details.add(new DetectDetailItem("剩余配水量:", info.totalLeft + ""));
        details.add(new DetectDetailItem("功     率:", info.perEleOut + ""));
        details.add(new DetectDetailItem("流     量:", info.perWtOut + ""));
        details.add(new DetectDetailItem("管     径:", info.circle + ""));
        details.add(new DetectDetailItem("放水用时:", info.useWaterTime + ""));
        details.add(new DetectDetailItem("累计电量:", info.totalPower + ""));
        details.add(new DetectDetailItem("告警信息:", info.alarm));
        details.add(new DetectDetailItem("设  备  号:", info.devID));
        details.add(new DetectDetailItem("收集时间:", info.collectTime));
        details.add(new DetectDetailItem("经      度:", info.lat + ""));
        details.add(new DetectDetailItem("纬      度:", info.lnt + ""));
        details.add(new DetectDetailItem("DTU   号:", info.cezhanID));

        detailAdapter.setData(details);
        detailAdapter.notifyDataSetChanged();
//        wellId.setText(info.wellID);
//        wellName.setText(info.wellName);
//        yearNumber.setText(info.yearNumber + "");
//        leftWt.setText(info.leftWt + "");
//        totalUse.setText(info.totalUse + "");
//        status.setText(info.openState == 0 ? "关闭" : "开启");
//        netState.setText(info.netState == 0 ? "在线" : "离线");
//        collectTime.setText(info.collectTime);


    }


}
