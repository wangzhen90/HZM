package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.WellDetail;
import com.hengze.hengzemanager.ui.fragment.MaintainAddFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainDeleteFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainModifyFragment;
import com.hengze.hengzemanager.ui.fragment.MaintainQueryFragment;
import com.hengze.hengzemanager.ui.widget.SegmentLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

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
    WellDetail detail;
    @Bind(R.id.title_content)
    TextView titleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintain);
        ButterKnife.bind(this);
        titleContent.setText("维护");
        getData();
        manager = getSupportFragmentManager();
        initOnclick();
        viewpager.setOffscreenPageLimit(4);
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

    void getData() {
        detail = (WellDetail) getIntent().getSerializableExtra(Constant.MAINTAIN_QUERY_DATA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                viewpager.setCurrentItem(0, false);
                break;

            case R.id.add:
                viewpager.setCurrentItem(1, false);
                break;

            case R.id.modify:
                viewpager.setCurrentItem(2, false);
                break;

            case R.id.delete:
                viewpager.setCurrentItem(3, false);
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
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.MAINTAIN_QUERY_DATA, detail);
                        queryFragment.setArguments(bundle);
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
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.MAINTAIN_QUERY_DATA, detail);
                        modifyFragment.setArguments(bundle);
                    }
                    return modifyFragment;
                case 3:
                    if (deleteFragment == null) {
                        deleteFragment = new MaintainDeleteFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.WELL_ID, detail.wellID);
                        bundle.putString(Constant.WELL_NAME, detail.wellName);
                        deleteFragment.setArguments(bundle);
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

    @Override
    public void onBackPressed() {
        if (modifyFragment != null && modifyFragment.timePopupWindow != null
                && modifyFragment.timePopupWindow.isShowing()) {

            modifyFragment.timePopupWindow.dismiss();
            return;
        }

        super.onBackPressed();
    }

    SpotsDialog spotsDialog;

    void showDialog(String msg) {
        if (msg == null) msg = "加载中...";

        if (spotsDialog == null) {
            spotsDialog = new SpotsDialog(this, msg);
            spotsDialog.show();
        } else {
            spotsDialog.show();
        }
    }

    void hideDialog() {
        if (spotsDialog != null) {
            spotsDialog.dismiss();
            spotsDialog = null;
        }
    }

    public void updateQuery(WellDetail detail) {
        if (queryFragment != null) {
            queryFragment.update(detail);
        }

        if (deleteFragment != null) {
            deleteFragment.update(detail.wellName);
        }
        if (viewpager != null) {
            viewpager.setCurrentItem(0);
        }

    }
}
