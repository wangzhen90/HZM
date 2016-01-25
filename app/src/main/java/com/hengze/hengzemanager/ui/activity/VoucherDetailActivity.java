package com.hengze.hengzemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengze.hengzemanager.Constant;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.modle.CardVoucherInfo;

public class VoucherDetailActivity extends FragmentActivity {
  CardVoucherInfo info;
  @Bind(R.id.card_id) TextView cardId;
  @Bind(R.id.well_id) TextView wellId;
  @Bind(R.id.den_water) TextView denWater;
  @Bind(R.id.den_fee) TextView denFee;
  @Bind(R.id.cde_water) TextView cdeWater;
  @Bind(R.id.cde_fee) TextView cdeFee;
  @Bind(R.id.total_water) TextView totalWater;
  @Bind(R.id.total_fee) TextView totalFee;
  @Bind(R.id.use_water) TextView useWater;
  @Bind(R.id.den_left_water) TextView denLeftWater;
  @Bind(R.id.power) TextView power;
  @Bind(R.id.state) TextView state;
  @Bind(R.id.operor) TextView operor;
  @Bind(R.id.operate_time) TextView operateTime;
  @Bind(R.id.back) View back;

  @Override protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voucher_detail);
    ButterKnife.bind(this);
    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    info = (CardVoucherInfo) getIntent().getSerializableExtra(Constant.CARD_VOUCHER_INFO);
    setInfo();
  }

  void setInfo() {
    if (info == null) {
      return;
    }

    cardId.setText("卡  号:" + info.cardID);
    wellId.setText("机电井ID:" + info.wellID);
    denWater.setText("定额内水量:" + info.denWater);
    denFee.setText("定额内费用:" + info.denFee);
    cdeWater.setText("超定额水量:" + info.cdeWater);
    cdeFee.setText("超定额费用:" + info.cdeFee);
    totalWater.setText("合计水量:" + info.totalWater);
    totalFee.setText("合计费用:" + info.totalFee);
    useWater.setText("累计用水量:" + info.usedWater);
    denLeftWater.setText("剩余定额水量:" + info.denLeftWater);
    power.setText("电  量:" + info.power);
    state.setText("状  态:" + info.state);
    operor.setText("操  作人:" + info.operator);
    operateTime.setText("操作时间:" + info.operaTime);
  }
}
