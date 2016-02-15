package com.hengze.hengzemanager.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.activity.MaintainActivity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaintainFragment extends Fragment implements View.OnClickListener {

    EditText well_id;
    TextView query;

    public MaintainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        well_id = (EditText) view.findViewById(R.id.water_well_id);
        query = (TextView) view.findViewById(R.id.query);
        well_id.setText("0994010101002");
        query.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.query:


                String wellID = well_id.getText().toString().trim();
                if (TextUtils.isEmpty(wellID)) {
                    ToastUtils.showToast("请输入机井编号");
                    return;
                }
                ApiClient apiClient = ApiClient.get();
                apiClient.api.queryWellDetail(wellID, new Callback<ArrayList<WellDetail>>() {
                    @Override
                    public void success(ArrayList<WellDetail> wellDetails, Response response) {
                        Log.e("ApiClient", "queryWellDetail succ");
                        if(wellDetails != null && wellDetails.size() >0)
                        toDetailPage(wellDetails.get(0));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("ApiClient", "queryWellDetail failed,error:" + error.getKind() + "," + error.getMessage());
                    }
                });

                break;

        }
    }


    void toDetailPage(WellDetail detail) {
        Intent intent = new Intent(getActivity(), MaintainActivity.class);
        intent.putExtra(Constant.MAINTAIN_QUERY_DATA,detail);


        getActivity().startActivity(intent);

    }
}
