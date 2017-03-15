package com.p2pinvest1020.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.p2pinvest1020.R;
import com.p2pinvest1020.fragment.HomeFragment;
import com.p2pinvest1020.fragment.InvestFragment;
import com.p2pinvest1020.fragment.MoreFragment;
import com.p2pinvest1020.fragment.PropertyFragment;
import com.p2pinvest1020.utils.AppManager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_rg)
    RadioGroup mainRg;
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MoreFragment moreFragment;
    private PropertyFragment propertyFragment;


    public void initListener() {

        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switchFragment(checkedId);
            }
        });
    }

    private void switchFragment(int checkedId) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        hiddenFragment(transaction);
        switch (checkedId) {
            case R.id.rb_invest:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.main_fl, investFragment);
                }
                transaction.show(investFragment);
                break;
            case R.id.rb_main:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_fl, homeFragment);
                }
                transaction.show(homeFragment);
                break;
            case R.id.rb_more:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.main_fl, moreFragment);
                }
                transaction.show(moreFragment);
                break;
            case R.id.rb_propert:
                if (propertyFragment == null) {
                    propertyFragment = new PropertyFragment();
                    transaction.add(R.id.main_fl, propertyFragment);
                }
                transaction.show(propertyFragment);
                break;
        }
        transaction.commit();
    }

    //隐藏所有的fragment
    private void hiddenFragment(FragmentTransaction transaction) {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
        if (propertyFragment != null) {
            transaction.hide(propertyFragment);
        }
    }

    public void initData() {
        AppManager.getInstance().addActivity(this);
        //选择默认的fragment
        switchFragment(R.id.rb_main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutid() {
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_main;
    }


    //点击两次退出


    private boolean isDoulbe = false;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isDoulbe) {
                //退出
                finish();
            }
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            isDoulbe = true;
            //超过2s改isDouble
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isDoulbe = false;
                }
            }, 2000);

            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
