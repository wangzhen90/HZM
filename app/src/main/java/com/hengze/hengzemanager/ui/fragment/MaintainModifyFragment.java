package com.hengze.hengzemanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.modle.WellInfo;
import java.util.Calendar;
import java.util.Date;

public class MaintainModifyFragment extends Fragment {

  WellDetail wellInfo;
  @Bind(R.id.wellID) EditText wellID;
  @Bind(R.id.devID) EditText devID;
  @Bind(R.id.cezhanID) EditText cezhanID;
  @Bind(R.id.wellName) EditText wellName;
  @Bind(R.id.lon) EditText lon;
  @Bind(R.id.lat) EditText lat;
  @Bind(R.id.buildYear) EditText buildYear;
  @Bind(R.id.qsxkzh) EditText qsxkzh;
  @Bind(R.id.wellDeep) EditText wellDeep;
  @Bind(R.id.waterDeep) EditText waterDeep;
  @Bind(R.id.pumpPower) EditText pumpPower;
  @Bind(R.id.waterQuality) EditText waterQuality;
  @Bind(R.id.perWtOut) EditText perWtOut;
  @Bind(R.id.perEleOut) EditText perEleOut;
  @Bind(R.id.yearNumber) EditText yearNumber;
  @Bind(R.id.netType) EditText netType;
  @Bind(R.id.simID) EditText simID;
  @Bind(R.id.remark) EditText remark;
  @Bind(R.id.managerName) EditText managerName;
  @Bind(R.id.managerTel) EditText managerTel;

  public MaintainModifyFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_maintain_modify, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    wellInfo = (WellDetail) getArguments().getSerializable(Constant.MAINTAIN_QUERY_DATA);
    setWellInfo(wellInfo);
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
    buildYear.setText(info.buildYear != null ? info.buildYear.getTime() + "" : "");
    qsxkzh.setText(info.qsxkzh);
    wellDeep.setText(info.wellDeep + "");
    waterDeep.setText(info.waterDeep + "");
    pumpPower.setText(info.pumpPower + "");
    waterQuality.setText(info.waterQuality + "");
    perWtOut.setText(info.perWtOut + "");
    perEleOut.setText(info.perEleOut + "");
    yearNumber.setText(info.yearNumber + "");
    netType.setText(info.netType + "");
    simID.setText(info.simID + "");
    remark.setText(info.remark);
    managerName.setText(info.managerName);
    managerTel.setText(info.managerTel);
  }


  WellDetail getWellInfo(){
    wellInfo.devID = waterDeep.getText().toString();
    wellInfo.cezhanID = wellName.getText().toString();
    wellInfo.wellName = wellName.getText().toString(); wellInfo.wellName = wellName.getText().toString();
    wellInfo.lat = Double.valueOf(wellName.getText().toString());
    wellInfo.lnt = Double.valueOf(wellName.getText().toString());
    //wellInfo.buildYear = wellName.getText().toString();
   

    wellInfo.qsxkzh = wellName.getText().toString();
    wellInfo.wellDeep = Double.valueOf(wellName.getText().toString());
    wellInfo.waterDeep = Double.valueOf(wellName.getText().toString());
    wellInfo.pumpPower = Integer.valueOf(wellName.getText().toString());
    wellInfo.perWtOut = Double.valueOf(wellName.getText().toString());
    wellInfo.yearNumber = Integer.valueOf(wellName.getText().toString());
    wellInfo.netType = wellName.getText().toString();
    wellInfo.simID = wellName.getText().toString();
    wellInfo.remark = wellName.getText().toString();
    wellInfo.managerName = wellName.getText().toString();
    wellInfo.managerTel = wellName.getText().toString();

    return wellInfo;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
