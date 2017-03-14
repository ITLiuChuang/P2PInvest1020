package com.p2pinvest1020.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.bean.InvestAllBean;
import com.p2pinvest1020.ui.MyProgress;
import com.p2pinvest1020.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 刘闯 on 2017/3/14.
 */

public class InvestAllAdapter extends BaseAdapter {
    private List<InvestAllBean.DataBean> data = new ArrayList<>();

    public InvestAllAdapter(List<InvestAllBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            this.data.clear();
            this.data.addAll(data);

        }

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.adapter_invest_all, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        InvestAllBean.DataBean dataBean = data.get(position);
        viewHolder.pName.setText(dataBean.getName());
        viewHolder.pProgresss.setProgress(UiUtils.dp2px(50));
        viewHolder.pProgresss.setroundWidth(UiUtils.dp2px(3));
        viewHolder.pSuodingdays.setText(dataBean.getSuodingDays());
        viewHolder.pMoney.setText(dataBean.getMinTouMoney());
        viewHolder.pYearlv.setText(dataBean.getYearRate());
        viewHolder.pMinzouzi.setText(dataBean.getMemberNum() + "起");
        viewHolder.pMinnum.setText(dataBean.getId());
        return convertView;
    }

     class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
