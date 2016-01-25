package com.hengze.hengzemanager.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardConsumeInfo;
import com.hengze.hengzemanager.modle.User;
import com.hengze.hengzemanager.ui.fragment.CardDetailFragment;
import com.hengze.hengzemanager.ui.fragment.CardTodayInfoFragment;
import java.util.ArrayList;
import java.util.List;

public class CardConsumeActivity extends FragmentActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.user_portrait) ImageView userPortrait;
  @Bind(R.id.user_name) TextView userName;
  @Bind(R.id.user_card_id) TextView userCardId;
  @Bind(R.id.user_address) TextView userAddress;
  @Bind(R.id.phone) ImageView phone;
  @Bind(R.id.consumer_info) RelativeLayout consumerInfo;
  @Bind(R.id.tabs) TabLayout tabs;
  @Bind(R.id.appbar) AppBarLayout appbar;
  @Bind(R.id.viewpager) ViewPager viewpager;
  @Bind(R.id.main_content) CoordinatorLayout mainContent;
  @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
  @Bind(R.id.back) View back;

  CardDetailFragment cardDetailFragment;
  ArrayList<CardConsumeInfo> infos;

  @Override protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_water_cosume);
    ButterKnife.bind(this);
    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    //collapsingToolbar.setTitle("CollapsingToolbarLayout");
    ////通过CollapsingToolbarLayout修改字体颜色
    //collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
    //collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    //toolbar.setLogo(R.drawable.arrow_white_left);
    //toolbar.setTitle("用户详情");

    getInfo();
    tabs.setupWithViewPager(viewpager);
  }

  void getInfo() {

    infos =
        (ArrayList<CardConsumeInfo>) getIntent().getSerializableExtra(Constant.CARD_CONSUME_INFO);
    if (infos != null && infos.size() > 0) {
      CardConsumeInfo cardConsumeInfo = infos.get(0);

      User user =
          new User(cardConsumeInfo.userName, cardConsumeInfo.cardID, cardConsumeInfo.userAddress,
              cardConsumeInfo.phone, cardConsumeInfo.userID);

      setUserInfo(user);
      setupViewPager(viewpager);
    }
  }

  void setUserInfo(User user) {
    userName.setText("用户名： " + user.name);
    userAddress.setText("地   址： " + user.address);
    userCardId.setText("卡  号： " + user.cardID);
  }

  private void setupViewPager(ViewPager viewPager) {

    Bundle bundle = new Bundle();
    bundle.putSerializable(Constant.CARD_CONSUME_INFO, infos);
    cardDetailFragment = new CardDetailFragment();
    cardDetailFragment.setArguments(bundle);

    MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
    CardTodayInfoFragment cardTodayInfoFragment = new CardTodayInfoFragment();

    adapter.addFragment(cardDetailFragment, "用水记录");
    adapter.addFragment(cardTodayInfoFragment, "今日统计");
    viewPager.setAdapter(adapter);
    viewPager.setCurrentItem(0);
  }

  class MyAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public MyAdapter(FragmentManager fm) {
      super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
      mFragments.add(fragment);
      mFragmentTitles.add(title);
    }

    @Override public Fragment getItem(int position) {
      return mFragments.get(position);
    }

    @Override public int getCount() {
      Log.e("getCount:", "count:" + mFragments.size() + "");
      return mFragments.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return mFragmentTitles.get(position);
    }
  }
}
