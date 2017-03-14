package com.p2pinvest1020.adapter;

import com.p2pinvest1020.base.InvestAllBean;
import com.p2pinvest1020.viewholder.BaseHolder;
import com.p2pinvest1020.viewholder.InvestHolder;

import java.util.List;

/**
 * Created by 刘闯 on 2017/3/14.
 */

public class InvestAllAdapter3 extends BaseInvestAllAdapter03<InvestAllBean.DataBean>{
    public InvestAllAdapter3(List<InvestAllBean.DataBean> list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder() {
        return new InvestHolder();
    }
}
