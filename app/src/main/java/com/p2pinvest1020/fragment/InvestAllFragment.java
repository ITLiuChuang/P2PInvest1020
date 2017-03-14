package com.p2pinvest1020.fragment;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.p2pinvest1020.R;
import com.p2pinvest1020.adapter.InvestAllAdapter3;
import com.p2pinvest1020.base.InvestAllBean;
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
//        InvestAllAdapter adapter = new InvestAllAdapter(investAllBean.getData());
        InvestAllAdapter3 adapter = new InvestAllAdapter3(investAllBean.getData());
        investAllLv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest_all;
    }


}
