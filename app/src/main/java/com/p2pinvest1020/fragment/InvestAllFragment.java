package com.p2pinvest1020.fragment;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.p2pinvest1020.R;
import com.p2pinvest1020.adapter.InvestAllAdapter;
import com.p2pinvest1020.bean.InvestAllBean;
import com.p2pinvest1020.command.AppNetConfig;

import butterknife.Bind;

/**
 * Created by 刘闯 on 2017/3/14.
 */
public class InvestAllFragment extends BaseFragment {
    @Bind(R.id.invest_all_lv)
    ListView investAllLv;

    @Override
    protected String getChildUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String json) {
        InvestAllBean investAllBean = JSON.parseObject(json, InvestAllBean.class);
        InvestAllAdapter investAllAdapter = new InvestAllAdapter(investAllBean.getData());
        investAllLv.setAdapter(investAllAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest_all;
    }


}
