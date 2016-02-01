package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.ui.fragment.MaintainAddFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainDeleteFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainModifyFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainQueryFragment;
import com.hengze.hengzemanager.ui.widget.SegmentLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaintainActivity extends FragmentActivity implements View.OnClickListener {
    FragmentManager manager;
    MaintainAddFragment addFragment;
    MaintainModifyFragment modifyFragment;
    MaintainDeleteFragment deleteFragment;
    MaintainQueryFragment queryFragment;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.query)
    RadioButton query;
    @Bind(R.id.add)
    RadioButton add;
    @Bind(R.id.modify)
    RadioButton modify;
    @Bind(R.id.delete)
    RadioButton delete;
    @Bind(R.id.segment_layout)
    SegmentLayout segmentLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintain);
        ButterKnife.bind(this);

        manager = getSupportFragmentManager();
        initOnclick();
        viewpager.setAdapter(new MaintainAdapter(manager));
    }

    private void initOnclick() {

        back.setOnClickListener(this);
        query.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        modify.setOnClickListener(this);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                segmentLayout.setChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.query:
                viewpager.setCurrentItem(0,false);
                break;

            case R.id.add:
                viewpager.setCurrentItem(1,false);
                break;

            case R.id.modify:
                viewpager.setCurrentItem(2,false);
                break;

            case R.id.delete:
                viewpager.setCurrentItem(3,false);
                break;

            case R.id.back:
                finish();
                break;

        }



    }

    class MaintainAdapter extends FragmentPagerAdapter {


        public MaintainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    if (queryFragment == null) {
                        queryFragment = new MaintainQueryFragment();
                    }
                    return queryFragment;
                case 1:
                    if (addFragment == null) {
                        addFragment = new MaintainAddFragment();
                    }
                    return addFragment;
                case 2:
                    if (modifyFragment == null) {
                        modifyFragment = new MaintainModifyFragment();
                    }
                    return modifyFragment;
                case 3:
                    if (deleteFragment == null) {
                        deleteFragment = new MaintainDeleteFragment();
                    }
                    return deleteFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}
