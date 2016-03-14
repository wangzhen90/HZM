package com.hengze.hengzemanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.modle.AddressNode;
import com.hengze.hengzemanager.modle.AddressTree;
import com.hengze.hengzemanager.modle.WellRt;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.activity.DetectionShowActivity;
import com.hengze.hengzemanager.ui.widget.spinner.NiceSpinner;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetectionFragment extends Fragment {

  @Bind(R.id.first_area) NiceSpinner firstArea;
  @Bind(R.id.second_area) NiceSpinner secondArea;
  @Bind(R.id.third_area) NiceSpinner thirdArea;
  @Bind(R.id.query_cun) TextView queryCun;

  public ArrayList<AddressNode> zhen;//镇
  public ArrayList<AddressNode> gq;//灌区
  public ArrayList<AddressNode> cun;//村
  String selectId;
  @Bind(R.id.query_zhen) TextView queryZhen;
  @Bind(R.id.query_gq) TextView queryGq;

  public DetectionFragment() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_account, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ApiClient client = ApiClient.get();
    client.api.queryAddress(new Callback<ArrayList<AddressNode>>() {
      @Override public void success(ArrayList<AddressNode> nodes, Response response) {

        if (nodes != null) {
          AddressTree tree = new AddressTree(nodes);
          setAddressInfo(tree);
        }
      }

      @Override public void failure(RetrofitError error) {

      }
    });

    queryCun.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String newWellId = cun.get(thirdArea.getSelectedIndex()).id;
        if (newWellId == null) {
          ToastUtils.showToast("选择村子不能为空");
        }
        query(newWellId);
      }
    });

    queryGq.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String newWellId = gq.get(secondArea.getSelectedIndex()).id;
        if (newWellId == null) {
          ToastUtils.showToast("选择灌区不能为空");
        }
        query(newWellId);
      }
    });

    queryZhen.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        String newWellId = zhen.get(firstArea.getSelectedIndex()).id;
        if (newWellId == null) {
          ToastUtils.showToast("选择乡镇不能为空");
        }
        query(newWellId);
      }
    });
  }

  private void query(String newWellId) {
    ApiClient apiClient = ApiClient.get();

    apiClient.api.queryInfoByArea(newWellId, new Callback<ArrayList<WellRt>>() {
      @Override public void success(ArrayList<WellRt> infos, Response response) {
        Log.e("test", "监测查询成功,info:" + infos.size());
        Intent intent = new Intent(getActivity(), DetectionShowActivity.class);
        intent.putExtra(Constant.DETECTION_DATA, infos);
        getContext().startActivity(intent);
      }

      @Override public void failure(RetrofitError error) {
        Log.e("test", "监测查询失败" + error.getMessage() + ",kind:" + error.getKind());
      }
    });
  }

  void setAddressInfo(AddressTree tree) {
    if (tree != null && tree.xianNode != null) {
      zhen = (ArrayList<AddressNode>) tree.xianNode.children;
    }
    gq = (ArrayList<AddressNode>) zhen.get(0).children;
    cun = (ArrayList<AddressNode>) gq.get(0).children;
    if(firstArea == null) return;
    firstArea.attachDataSource(zhen);
    secondArea.attachDataSource(gq);
    thirdArea.attachDataSource(cun);
    selectId = cun.get(0).id;
    firstArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gq = (ArrayList<AddressNode>) zhen.get(position).children;
        cun = (ArrayList<AddressNode>) gq.get(0).children;
        secondArea.mSelectedIndex = 0;
        thirdArea.mSelectedIndex = 0;
        secondArea.attachDataSource(gq);
        thirdArea.attachDataSource(cun);
        selectId = cun.get(0).id;
      }
    });

    secondArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cun = (ArrayList<AddressNode>) gq.get(position).children;
        thirdArea.mSelectedIndex = 0;
        thirdArea.attachDataSource(cun);
        selectId = cun.get(0).id;
      }
    });

    thirdArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectId = cun.get(position).id;
      }
    });
  }
}
