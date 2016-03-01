package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardVoucherInfo;
import com.hengze.hengzemanager.ui.adapter.VoucherAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardVorcherRecordActivity extends FragmentActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.vorcher_recyclerview)
    RecyclerView recyclerview;
    ArrayList<CardVoucherInfo> infos;
    VoucherAdapter adapter;
    @Bind(R.id.title_content)
    TextView titleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_vorcher_record);
        ButterKnife.bind(this);
        titleContent.setText("充值记录");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infos = (ArrayList<CardVoucherInfo>) getIntent().getSerializableExtra(Constant.CARD_VOUCHER_INFO);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        setInfos(infos);
    }

    void setInfos(ArrayList<CardVoucherInfo> infos) {

        if (adapter == null) {
            adapter = new VoucherAdapter(infos);
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setInfos(infos);
            adapter.notifyDataSetChanged();
        }
    }


    //void setTestDate(){
    //  CardVoucherInfo info = infos.get(0);
    //
    //
    //
    //
    //}
    //
    //
    //CardVoucherInfo copy(CardVoucherInfo target){
    //
    //  CardVoucherInfo info
    //
    //}
}
