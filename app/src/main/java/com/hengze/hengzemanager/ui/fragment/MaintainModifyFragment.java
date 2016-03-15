package com.hengze.hengzemanager.ui.fragment;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.DateUtil;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.activity.AddNewWellInfoActivity;
import com.hengze.hengzemanager.ui.activity.MaintainActivity;
import com.hengze.hengzemanager.ui.widget.pickview.TimePopupWindow;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MaintainModifyFragment extends Fragment {

    WellDetail wellInfo;
    @Bind(R.id.wellID)
    EditText wellID;
    @Bind(R.id.devID)
    EditText devID;
    @Bind(R.id.cezhanID)
    EditText cezhanID;
    @Bind(R.id.wellName)
    EditText wellName;
    @Bind(R.id.lon)
    EditText lon;
    @Bind(R.id.lat)
    EditText lat;
    @Bind(R.id.buildYear)
    TextView buildYear;
    @Bind(R.id.qsxkzh)
    EditText qsxkzh;
    @Bind(R.id.wellDeep)
    EditText wellDeep;
    @Bind(R.id.waterDeep)
    EditText waterDeep;
    @Bind(R.id.pumpPower)
    EditText pumpPower;
    @Bind(R.id.waterQuality)
    EditText waterQuality;
    @Bind(R.id.perWtOut)
    EditText perWtOut;
    @Bind(R.id.perEleOut)
    EditText perEleOut;
    @Bind(R.id.yearNumber)
    EditText yearNumber;
    @Bind(R.id.netType)
    EditText netType;
    @Bind(R.id.simID)
    EditText simID;
    @Bind(R.id.remark)
    EditText remark;
    @Bind(R.id.managerName)
    EditText managerName;
    @Bind(R.id.managerTel)
    EditText managerTel;
    @Bind(R.id.commit)
    TextView commit;
    static final String TAG = MaintainModifyFragment.class.getSimpleName();
    public TimePopupWindow timePopupWindow;
    @Bind(R.id.circle)
    EditText circle;
    @Bind(R.id.yc)
    EditText yc;
    private LocationManager lm;

    public MaintainModifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintain_modify, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wellInfo = (WellDetail) getArguments().getSerializable(Constant.MAINTAIN_QUERY_DATA);
        setWellInfo(wellInfo);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWellInfo();
                showDialog(null);
                ApiClient apiClient = ApiClient.get();

                if (getContext() instanceof AddNewWellInfoActivity) { //新建well
                    apiClient.api.addWellInfo(wellInfo.wellID,
                            wellInfo.devID,
                            wellInfo.cezhanID,
                            wellInfo.wellName,
                            wellInfo.lat,
                            wellInfo.lnt,
                            wellInfo.buildYear,
                            wellInfo.qsxkzh,
                            wellInfo.wellDeep,
                            wellInfo.waterDeep,
                            wellInfo.waterQuality,
                            wellInfo.pumpPower + "",
                            wellInfo.perWtOut,
                            wellInfo.perEleOut,
                            wellInfo.yearNumber,
                            wellInfo.managerName,
                            wellInfo.managerTel,
                            wellInfo.netType,
                            wellInfo.simID,
                            wellInfo.remark,
                            wellInfo.circle + "",
                            wellInfo.yc + "",
                            new Callback<WellDetail[]>() {
                                @Override
                                public void success(WellDetail[] wellDetail, Response response) {
                                    hideDialog();
                                    Log.e(TAG, "新更新成功,status:" + wellDetail);
                                    if (wellDetail != null && wellDetail.length > 0) {

                                        Intent intent = new Intent(MaintainModifyFragment.this.getContext(),
                                                MaintainActivity.class);
                                        intent.putExtra(Constant.MAINTAIN_QUERY_DATA, wellDetail[0]);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    hideDialog();
                                    Log.e(TAG, "新更新失败,error:" + error.getKind() + ",msg:" + error.getMessage());
                                }
                            });
                } else {
                    apiClient.api.updateWellInfo(wellInfo.wellID,
                            wellInfo.devID,
                            wellInfo.cezhanID,
                            wellInfo.wellName,
                            wellInfo.lat,
                            wellInfo.lnt,
                            wellInfo.buildYear,
                            wellInfo.qsxkzh,
                            wellInfo.wellDeep,
                            wellInfo.waterDeep,
                            wellInfo.waterQuality,
                            wellInfo.pumpPower + "",
                            wellInfo.perWtOut + "",
                            wellInfo.perEleOut,
                            wellInfo.yearNumber,
                            wellInfo.managerName,
                            wellInfo.managerTel,
                            wellInfo.netType,
                            wellInfo.simID,
                            wellInfo.remark,
                            wellInfo.circle + "",
                            wellInfo.yc + "",

                            new Callback<WellDetail[]>() {
                                @Override
                                public void success(WellDetail[] wellDetail, Response response) {
                                    hideDialog();
                                    //TODO 修改之后展示新的数据，这个需要在这个页面和查询页面都更新
                                    if (wellDetail != null && wellDetail.length > 0) {
                                        setWellInfo(wellDetail[0]);
                                        ((MaintainActivity) getContext()).updateQuery(wellDetail[0]);
                                    }
                                    Log.e(TAG, "更新成功,status:" + wellDetail);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    hideDialog();
                                    Log.e(TAG, "更新失败,error:" + error.getKind() + ",msg:" + error.getMessage());
                                }
                            });
                }


            }
        });

        timePopupWindow = new TimePopupWindow(getActivity(), TimePopupWindow.Type.ALL);
        timePopupWindow.setRange(2012, 2020);
        timePopupWindow.btnCancel.setText("即时");
        timePopupWindow.btnCancel.setBackgroundColor(getResources().getColor(R.color.blue));
        timePopupWindow.btnCancel.setTextColor(getResources().getColor(R.color.white));
        timePopupWindow.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                buildYear.setText(DateUtil.getTime(date));
            }

            @Override
            public void onCancel() {
                //即时任务

            }
        });

        buildYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePopupWindow.showAtLocation(buildYear, Gravity.BOTTOM, 0, 0, new Date());
            }
        });
    }

    void setWellInfo(WellDetail info) {
        wellID.setText(info.wellID);
        wellID.setFocusable(false);
        devID.setText(info.devID);
        cezhanID.setText(info.cezhanID);
        wellName.setText(info.wellName);
        lon.setText(info.lnt + "");//TODO 需要定位
        wellID.setFocusable(false);
        lat.setText(info.lat + "");
        wellID.setFocusable(false);
        buildYear.setText(info.buildYear);
        qsxkzh.setText(info.qsxkzh);
        wellDeep.setText(info.wellDeep + "");
        waterDeep.setText(info.waterDeep + "");
        pumpPower.setText(info.pumpPower + "");
        waterQuality.setText(info.waterQuality != null ? info.waterQuality : "");
        perWtOut.setText(info.perWtOut + "");
        perEleOut.setText(info.perEleOut + "");
        yearNumber.setText(info.yearNumber + "");
        netType.setText(TextUtils.isEmpty(info.netType) ? "" : info.netType + "");
        simID.setText(info.simID != null ? info.simID : "");
        remark.setText(info.remark);
        managerName.setText(info.managerName);
        managerTel.setText(info.managerTel);
        circle.setText(info.circle + "");
        yc.setText(info.yc + "");

        initLocation();
    }

    WellDetail getWellInfo() {
        wellInfo.devID = devID.getText().toString();
        wellInfo.cezhanID = cezhanID.getText().toString();
        wellInfo.wellName = wellName.getText().toString();
        wellInfo.wellID = wellID.getText().toString();
        wellInfo.lat = Double.valueOf(lat.getText().toString());
        wellInfo.lnt = Double.valueOf(lon.getText().toString());
        wellInfo.buildYear = buildYear.getText().toString();

        wellInfo.qsxkzh = qsxkzh.getText().toString();
        wellInfo.wellDeep = Double.valueOf(wellDeep.getText().toString());
        wellInfo.waterDeep = Double.valueOf(waterDeep.getText().toString());
        wellInfo.pumpPower = Integer.valueOf(pumpPower.getText().toString());
        wellInfo.waterQuality = waterQuality.getText().toString();
        wellInfo.perWtOut = Double.valueOf(perWtOut.getText().toString());
        wellInfo.yearNumber = Integer.valueOf(yearNumber.getText().toString());
        wellInfo.netType = netType.getText().toString();
        wellInfo.simID = simID.getText().toString();
        wellInfo.remark = remark.getText().toString();
        wellInfo.managerName = managerName.getText().toString();
        wellInfo.managerTel = managerTel.getText().toString();
        wellInfo.circle = Double.valueOf(circle.getText().toString());
        wellInfo.yc = Double.valueOf(yc.getText().toString());

        return wellInfo;
    }

    @Override
    public void onDestroyView() {
        if (timePopupWindow != null && timePopupWindow.isShowing()) {
            timePopupWindow.dismiss();
        }
        stopLocation();
        mLocationClient.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation.getErrorCode() == 0) {

                //定位成功回调信息，设置相关消息
                lat.setText(aMapLocation.getLatitude() + "");
                lon.setText(aMapLocation.getLongitude() + "");
                stopLocation();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    };

    public AMapLocationClientOption mLocationOption = null;

    void initLocation() {
        //声明AMapLocationClient类对象

        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        startLocation();
    }

    void startLocation() {

        //启动定位
        mLocationClient.startLocation();
    }

    void stopLocation() {
        //启动定位
        mLocationClient.stopLocation();
    }

    SpotsDialog spotsDialog;

    void showDialog(String msg) {
        if (msg == null) msg = "加载中...";


        if (spotsDialog == null) {
            spotsDialog = new SpotsDialog(getContext(), msg);
            spotsDialog.show();
        } else {
            spotsDialog.show();
        }


    }

    void hideDialog() {
        if (spotsDialog != null) {
            spotsDialog.dismiss();
            spotsDialog = null;
        }
    }
}
