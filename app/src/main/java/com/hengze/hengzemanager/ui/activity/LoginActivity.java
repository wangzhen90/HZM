package com.hengze.hengzemanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.hengze.hengzemanager.R;
import com.hengze.hengzemanager.Utils.ToastUtils;
import com.hengze.hengzemanager.modle.UserWrapper;
import com.hengze.hengzemanager.net.ApiClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {

  @Bind(R.id.user_name) EditText userName;
  @Bind(R.id.password) EditText password;
  @Bind(R.id.bt_login) Button login;
  static final String TAG = LoginActivity.class.getSimpleName();
  Spring spring;

  @Override protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    SpringSystem springSystem = SpringSystem.create();
    spring = springSystem.createSpring();
    spring.addListener(new SimpleSpringListener() {
      @Override public void onSpringUpdate(Spring spring) {
        // You can observe the updates in the spring
        // state by asking its current value in onSpringUpdate.
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.1f);
        login.setScaleX(scale);
        login.setScaleY(scale);
      }
    });

    login.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            spring.setEndValue(1);
            return true;

          case MotionEvent.ACTION_UP:
            spring.setEndValue(0);
            String userName = LoginActivity.this.userName.getText().toString();
            String password = LoginActivity.this.password.getText().toString();

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
              ToastUtils.showToast("请输入账号和密码");
              break;
            }

            UserWrapper userWrapper = new UserWrapper(userName, password);

            ApiClient.get().api.login(userWrapper, new Callback<String>() {
              @Override public void success(String status, Response response) {
                Log.e(TAG, "登陆成功,status:" + status);
                toMainPage();
              }

              @Override public void failure(RetrofitError error) {
                Log.e(TAG, "登陆失败，error:" + error.getMessage() + ",kind:" + error.getKind());
              }
            });

            return true;
        }

        return true;
      }
    });

    //login.setOnClickListener(this);
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {

      case R.id.bt_login:

        break;
    }
  }

  void toMainPage() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }
}
