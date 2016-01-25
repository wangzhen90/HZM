package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewConfiguration;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.ui.fragment.AccountFragment;
import com.hengze.hengzemanager.ui.fragment.QueryRecordFragement;
import com.hengze.hengzemanager.ui.fragment.VoucherFragment;
import com.hengze.hengzemanager.ui.widget.ChangeColorIconWithTextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

  @Bind(R.id.viewpager) ViewPager viewpager;
  @Bind(R.id.query) ChangeColorIconWithTextView query;
  @Bind(R.id.voucher) ChangeColorIconWithTextView voucher;
  @Bind(R.id.account) ChangeColorIconWithTextView account;

  QueryRecordFragement queryRecordFragement;
  VoucherFragment voucherFragment;
  AccountFragment accountFragment;
  private List<ChangeColorIconWithTextView> mTabIndicator =
      new ArrayList<ChangeColorIconWithTextView>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setOverflowShowingAlways();
    init();
  }

  void init() {

    initTabIndicator();
    initViewPager();
  }

  void initViewPager() {
    FragmentManager manager = getSupportFragmentManager();
    viewpager.setAdapter(new HomePagerAdapter(manager));
    viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageSelected(int arg0) {
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset > 0) {
          ChangeColorIconWithTextView left = mTabIndicator.get(position);
          ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

          left.setIconAlpha(1 - positionOffset);
          right.setIconAlpha(positionOffset);
        }
      }

      @Override public void onPageScrollStateChanged(int state) {
      }
    });
  }

  private void initTabIndicator() {

    mTabIndicator.add(query);
    mTabIndicator.add(voucher);
    mTabIndicator.add(account);

    query.setOnClickListener(this);
    voucher.setOnClickListener(this);
    account.setOnClickListener(this);

    query.setIconAlpha(1.0f);
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {
      case R.id.query:
        mTabIndicator.get(0).setIconAlpha(1.0f);
        mTabIndicator.get(1).setIconAlpha(0.0f);
        mTabIndicator.get(2).setIconAlpha(0.0f);

        viewpager.setCurrentItem(0, false);
        break;

      case R.id.voucher:
        mTabIndicator.get(1).setIconAlpha(1.0f);
        mTabIndicator.get(0).setIconAlpha(0.0f);
        mTabIndicator.get(2).setIconAlpha(0.0f);
        viewpager.setCurrentItem(1, false);
        break;

      case R.id.account:
        mTabIndicator.get(2).setIconAlpha(1.0f);
        mTabIndicator.get(0).setIconAlpha(0.0f);
        mTabIndicator.get(1).setIconAlpha(0.0f);
        viewpager.setCurrentItem(2, false);
        break;
    }
  }

  public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager manager) {

      super(manager);
    }

    private final String[] titles = { "选项一", "选项二", "选项三" };

    @Override public CharSequence getPageTitle(int position) {
      return titles[position];
    }

    @Override public int getCount() {
      return titles.length;
    }

    @Override public Fragment getItem(int position) {
      switch (position) {
        case 0:
          if (queryRecordFragement == null) queryRecordFragement = new QueryRecordFragement();

          return queryRecordFragement;

        case 1:

          if (voucherFragment == null) voucherFragment = new VoucherFragment();

          return voucherFragment;

        case 2:

          if (accountFragment == null) accountFragment = new AccountFragment();
          return accountFragment;
      }

      return null;
    }
  }

  private void setOverflowShowingAlways() {
    try {
      // true if a permanent menu key is present, false otherwise.
      ViewConfiguration config = ViewConfiguration.get(this);
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      menuKeyField.setAccessible(true);
      menuKeyField.setBoolean(config, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
