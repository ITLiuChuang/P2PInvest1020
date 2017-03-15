package com.p2pinvest1020.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.p2pinvest1020.R;
import com.p2pinvest1020.base.UserInfo;
import com.p2pinvest1020.command.AppNetConfig;
import com.p2pinvest1020.utils.LoadNet;
import com.p2pinvest1020.utils.LoadNetHttp;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_back)
    ImageView baseBack;
    @Bind(R.id.base_setting)
    ImageView baseSetting;
    @Bind(R.id.tv_login_number)
    TextView tvLoginNumber;
    @Bind(R.id.login_et_number)
    EditText loginEtNumber;
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;
    @Bind(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @Bind(R.id.login_et_pwd)
    EditText loginEtPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.login_regitster_tv)
    TextView loginRegitsterTv;

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = loginEtNumber.getText().toString().trim();
                String pwd = loginEtPwd.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    showToast("账号不能为空");
                    return;
                } else if (TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", phone);
                map.put("pwd", pwd);

                //去服务器登陆
                LoadNet.getDataPost(AppNetConfig.LOGIN, map, new LoadNetHttp() {
                    @Override
                    public void success(String context) {
//                        Log.e("login", "success: " + context);
                        JSONObject jsonObject = JSON.parseObject(context);
                        Boolean success = jsonObject.getBoolean("success");
                        if(success) {
                            //解析数据
                            UserInfo userInfo = JSON.parseObject(context, UserInfo.class);
                            //把数据保存到数据库
                            saveUser(userInfo);
                            //跳转
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            showToast("账号或密码错误");
                        }
                    }

                    @Override
                    public void failure(String error) {
                        Log.e("error", "success: " + error);
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        baseBack.setVisibility(View.INVISIBLE);
        baseSetting.setVisibility(View.INVISIBLE);
        baseTitle.setText("登录");
    }

    @Override
    public int getLayoutid() {
        return R.layout.activity_login;
    }


}
