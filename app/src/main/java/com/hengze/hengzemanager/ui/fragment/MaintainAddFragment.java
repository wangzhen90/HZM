package com.hengze.hengzemanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.activity.AddNewWellInfoActivity;
import com.hengze.hengzemanager.ui.widget.spinner.NiceSpinner;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MaintainAddFragment extends Fragment {
  @Bind(R.id.first_area) NiceSpinner firstArea;
  @Bind(R.id.second_area) NiceSpinner secondArea;
  @Bind(R.id.third_area) NiceSpinner thirdArea;

  public ArrayList<AddressNode> zhen;//镇
  public ArrayList<AddressNode> gq;//灌区
  public ArrayList<AddressNode> cun;//村
  String selectId;
  @Bind(R.id.add_well) TextView addWell;

  // TODO: Rename and change types of parameters

  public MaintainAddFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_maintain_add, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five","6","7","8","9","10","11","12","13"));
    //        firstArea.attachDataSource(dataset);

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

    addWell.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String newWellId = cun.get(thirdArea.getSelectedIndex()).id;
        if (newWellId == null) {
          ToastUtils.showToast("选择村子不能为空");
        }
        ApiClient apiClient = ApiClient.get();
        apiClient.api.addWell(newWellId, new Callback<String>() {
          @Override
          public void success(String s, Response response) {
            Log.e("test", "获取新增id成功" + s);


            if (!TextUtils.isEmpty(s)) {
              WellDetail detail = new WellDetail();
              detail.wellID = s;
              Intent intent = new Intent(getActivity(), AddNewWellInfoActivity.class);
              intent.putExtra(Constant.MAINTAIN_QUERY_DATA, detail);
              startActivity(intent);
            }


          }

          @Override
          public void failure(RetrofitError error) {
            Log.e("test", "获取新增id失败" + error.getMessage() + ",kind:" + error.getKind());
          }
        });
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  void setAddressInfo(AddressTree tree) {
    if (tree != null && tree.xianNode != null) {
      zhen = (ArrayList<AddressNode>) tree.xianNode.children;
    }
    gq = (ArrayList<AddressNode>) zhen.get(0).children;
    cun = (ArrayList<AddressNode>) gq.get(0).children;
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
