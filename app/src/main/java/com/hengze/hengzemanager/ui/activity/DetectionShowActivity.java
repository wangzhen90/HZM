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
import com.hengze.hengzemanager.modle.WellRt;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.adapter.DetectionAdapter;
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
    @Bind(R.id.well_id)
    TextView wellId;
    @Bind(R.id.well_name)
    TextView wellName;
    @Bind(R.id.year_number)
    TextView yearNumber;
    @Bind(R.id.left_wt)
    TextView leftWt;
    @Bind(R.id.total_use)
    TextView totalUse;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.net_state)
    TextView netState;
    @Bind(R.id.collect_time)
    TextView collectTime;
    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    String areaID;

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
    }

    private void initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(areaID != null){
                    swipeRefresh.setRefreshing(true);
                    ApiClient.get().api.queryInfoByArea(areaID, new Callback<ArrayList<WellRt>>() {
                        @Override
                        public void success(ArrayList<WellRt> wellRts, Response response) {
                            if(!wellRts.isEmpty()){
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
                }else {
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
        yearNumber.setText(info.yearNumber + "");
        leftWt.setText(info.leftWt + "");
        totalUse.setText(info.totalUse + "");
        status.setText(info.openState == 0 ? "关闭" : "开启");
        netState.setText(info.netState == 0 ? "在线" : "离线");
        collectTime.setText(info.collectTime);
    }


}
