package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.ui.fragment.MaintainModifyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNewWellInfoActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.layout_content)
    FrameLayout layoutContent;
    WellDetail wellDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_well_info);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleContent.setText("新建机井信息");


        wellDetail = (WellDetail) getIntent().getSerializableExtra(Constant.MAINTAIN_QUERY_DATA);


        FragmentManager manager = getSupportFragmentManager();
        MaintainModifyFragment modifyFragment = new MaintainModifyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MAINTAIN_QUERY_DATA, wellDetail);
        modifyFragment.setArguments(bundle);

        manager.beginTransaction().replace(R.id.layout_content, modifyFragment).commit();
    }

}
