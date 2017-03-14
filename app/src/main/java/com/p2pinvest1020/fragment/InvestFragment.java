package com.p2pinvest1020.fragment;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.adapter.InvesAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

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
    @Bind(R.id.tv_invest_all)
    TextView tvInvestAll;
    @Bind(R.id.tv_invest_recommend)
    TextView tvInvestRecommend;
    @Bind(R.id.tv_invest_hot)
    TextView tvInvestHot;

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    protected void initData(String json) {
        initListener();
        //初始化标题
        initTitle();
        //初始化fragment
        initFragments();
        //初始化viewpager
        initViewPager();
        //设置默认选中的tab
        initTab();

    }

    private void initTab() {
        selectText(0);
    }

    private void initViewPager() {
        investVp.setAdapter(new InvesAdapter(getChildFragmentManager(), list));
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


    protected void initListener() {
        investVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void selectText(int position) {
        //把所有按按钮设置成默认颜色
        hiddenTextViewState();
        switch (position) {
            case 0:
                tvInvestAll.setBackgroundColor(Color.BLUE);
                break;
            case 1:
                tvInvestRecommend.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                tvInvestHot.setBackgroundColor(Color.BLUE);
                break;
        }
    }

    private void hiddenTextViewState() {
        tvInvestAll.setBackgroundColor(Color.WHITE);
        tvInvestHot.setBackgroundColor(Color.WHITE);
        tvInvestRecommend.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest;
    }


    @OnClick({R.id.tv_invest_all, R.id.tv_invest_recommend, R.id.tv_invest_hot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invest_all:
                investVp.setCurrentItem(0);
                break;
            case R.id.tv_invest_recommend:
                investVp.setCurrentItem(1);
                break;
            case R.id.tv_invest_hot:
                investVp.setCurrentItem(2);
                break;
        }
    }
}
