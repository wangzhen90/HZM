package com.hengze.hengzemanager.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.net.ApiClient;

import android.util.Log;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaintainQueryFragment extends Fragment {


    WellDetail detail;
    @Bind(R.id.result_tag)
    TextView resultTag;
    @Bind(R.id.result)
    TextView result;

    public MaintainQueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintain_query, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StringBuilder stringBuilder = new StringBuilder();
        detail = (WellDetail) getArguments().getSerializable(Constant.MAINTAIN_QUERY_DATA);


        if (detail != null) {
            stringBuilder.append(detail.managerName + "\n");
            stringBuilder.append(detail.managerTel + "\n");
            stringBuilder.append(detail.wellID + "\n");//机井名称
            stringBuilder.append(detail.devID + "\n");//设备号
            stringBuilder.append(detail.cezhanID + "\n");//DTU编号
            stringBuilder.append(detail.wellName + "\n");//机井名称
            stringBuilder.append(detail.lat + "\n");//经度
            stringBuilder.append(detail.lnt + "\n");//纬度
            stringBuilder.append(detail.buildYear + "\n");//创建时间
            stringBuilder.append(detail.qsxkzh + "\n");//
            stringBuilder.append(detail.wellDeep + "\n");//机井深度
            stringBuilder.append(detail.waterDeep + "\n");//水深度
            stringBuilder.append(detail.waterQuality + "\n");//水质
            stringBuilder.append(detail.pumpPower + "\n");//水泵功率
            stringBuilder.append(detail.perWtOut + "\n");//单位出水量
            stringBuilder.append(detail.perEleOut + "\n");////单位功耗
            stringBuilder.append(detail.yearNumber + "\n");
            stringBuilder.append(detail.netType + "\n");
            stringBuilder.append(detail.simID + "\n");
            stringBuilder.append(detail.remark + "\n");

            result.setText(stringBuilder);
//            resultTag.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    testWellUpdate(detail);
//                    testWellUpdate2(detail);
//                }
//            });

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //void testWellUpdate(WellDetail wellDetail) {
    //    wellDetail.wellName = "这是测试";
    //    ApiClient apiClient = ApiClient.get();
    //    apiClient.api.testUpdate(detail, new Callback<WellDetail>() {
    //        @Override
    //        public void success(WellDetail wellDetail, Response response) {
    //            Log.e("update", "update succ" + wellDetail.wellName);
    //        }
    //
    //        @Override
    //        public void failure(RetrofitError error) {
    //            Log.e("update", "update failed,error" + error.getMessage() + ",kind:" + error.getKind());
    //        }
    //    });
    //
    //}
    //
    //void testWellUpdate2(WellDetail wellDetail) {
    //
    //    wellDetail.wellName = "这是测试啊啊啊";
    //    wellDetail.wellID = "0994310101988";
    //    wellDetail.devID="9999";
    //    ApiClient apiClient = ApiClient.get();
    //    apiClient.api.testUpdate2(wellDetail.wellName,wellDetail.wellID,wellDetail.devID,new Callback<WellDetail>() {
    //        @Override
    //        public void success(WellDetail wellDetail, Response response) {
    //            Log.e("update2", "update2 succ" + wellDetail.wellName);
    //        }
    //
    //        @Override
    //        public void failure(RetrofitError error) {
    //            Log.e("update2", "update2 failed,error" + error.getMessage() + ",kind:" + error.getKind());
    //        }
    //    });
    //
    //}
}
