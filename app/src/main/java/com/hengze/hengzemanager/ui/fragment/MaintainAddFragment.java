package com.hengze.hengzemanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.AddressNode;
import com.hengze.hengzemanager.modle.AddressTree;
import com.hengze.hengzemanager.net.ApiClient;
import com.hengze.hengzemanager.ui.widget.spinner.NiceSpinner;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MaintainAddFragment extends Fragment {
    @Bind(R.id.first_area)
    NiceSpinner firstArea;
    @Bind(R.id.second_area)
    NiceSpinner secondArea;
    @Bind(R.id.third_area)
    NiceSpinner thirdArea;

    public ArrayList<AddressNode> zhen;//镇
    public ArrayList<AddressNode> gq;//灌区
    public ArrayList<AddressNode> cun;//村
    String selectId;

    // TODO: Rename and change types of parameters


    public MaintainAddFragment() {
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
        View view = inflater.inflate(R.layout.fragment_maintain_add, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five","6","7","8","9","10","11","12","13"));
//        firstArea.attachDataSource(dataset);

        ApiClient client = ApiClient.get();
        client.api.queryAddress(new Callback<ArrayList<AddressNode>>() {
            @Override
            public void success(ArrayList<AddressNode> nodes, Response response) {

                if (nodes != null) {
                    AddressTree tree = new AddressTree(nodes);
                   setAddressInfo(tree);
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    void setAddressInfo(AddressTree tree){
        if (tree != null && tree.xianNode != null)
            zhen = (ArrayList<AddressNode>) tree.xianNode.children;
        gq = (ArrayList<AddressNode>) zhen.get(0).children;
        cun = (ArrayList<AddressNode>) gq.get(0).children;
        firstArea.attachDataSource(zhen);
        secondArea.attachDataSource(gq);
        thirdArea.attachDataSource(cun);
        selectId = cun.get(0).id;
        firstArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cun = (ArrayList<AddressNode>) gq.get(position).children;
                thirdArea.mSelectedIndex = 0;
                thirdArea.attachDataSource(cun);
                selectId = cun.get(0).id;
            }
        });


        thirdArea.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectId = cun.get(position).id;
            }
        });
    }

}
