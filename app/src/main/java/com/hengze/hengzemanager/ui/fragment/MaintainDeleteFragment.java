package com.hengze.hengzemanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.net.ApiClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MaintainDeleteFragment extends Fragment {

  @Bind(R.id.delete_well_id) EditText deleteWellId;
  @Bind(R.id.delete_well_name) EditText deleteWellName;
  @Bind(R.id.delete_well_info) TextView deleteWellInfo;

  public MaintainDeleteFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_maintain_delete, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

   final String wellID = getArguments().getString(Constant.WELL_ID);
   final String wellName = getArguments().getString(Constant.WELL_NAME);

    deleteWellId.setText(wellID != null ? wellID : "获取数据失败");
    deleteWellName.setText(wellID != null ? wellName : "获取数据失败");

    deleteWellInfo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if(TextUtils.isEmpty(wellID)){
          ToastUtils.showToast("未获得机井编号");
          return;
        }


        ApiClient client = ApiClient.get();
        client.api.deleteWell(wellID, new Callback<String>() {
          @Override public void success(String s, Response response) {
            ToastUtils.showToast(s);
            if(s.equals("删除成功")){
              getActivity().finish();
            }
          }

          @Override public void failure(RetrofitError error) {

          }
        });


      }
    });

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
}
