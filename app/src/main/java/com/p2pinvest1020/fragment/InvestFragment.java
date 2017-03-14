package com.p2pinvest1020.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.adapter.InvesAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/10.
 */
public class InvestFragment extends BaseFragment {

    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_back)
    ImageView baseBack;
    @Bind(R.id.base_setting)
    ImageView baseSetting;
    @Bind(R.id.invest_vp)
    ViewPager investVp;

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    protected void initData(String json) {
        //初始化标题
        initTitle();
        //初始化fragment
        initFragments();
        //初始化viewpager
        initViewPager();
    }

    private void initViewPager() {
        investVp.setAdapter(new InvesAdapter(getChildFragmentManager(),list));
    }

    private ArrayList<BaseFragment> list = new ArrayList<>();

    private void initFragments() {
        list.add(new InvestAllFragment());
        list.add(new InvestRecommendFragment());
        list.add(new InvestHotFragment());
    }

    private void initTitle() {
        baseBack.setVisibility(View.GONE);
        baseSetting.setVisibility(View.GONE);
        baseTitle.setText("投资");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest;
    }

}
