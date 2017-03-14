package com.p2pinvest1020.viewholder;

import android.view.View;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.base.InvestAllBean;
import com.p2pinvest1020.ui.MyProgress;
import com.p2pinvest1020.utils.UiUtils;

import butterknife.Bind;

/**
 * Created by 刘闯 on 2017/3/14.
 */
public class InvestHolder extends BaseHolder<InvestAllBean.DataBean> {
    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    MyProgress pProgresss;

    @Override
    public void setChildData() {
        InvestAllBean.DataBean dataBean = getT();
        pName.setText(dataBean.getName());
        pProgresss.setProgress(UiUtils.dp2px(50));
        pProgresss.setroundWidth(UiUtils.dp2px(3));
        pSuodingdays.setText(dataBean.getSuodingDays());
        pMoney.setText(dataBean.getMinTouMoney());
        pYearlv.setText(dataBean.getYearRate());
        pMinzouzi.setText(dataBean.getMemberNum() + "起");
        pMinnum.setText(dataBean.getId());
    }

    @Override
    public View initView() {
        return UiUtils.getView(R.layout.adapter_invest_all);
    }
}
