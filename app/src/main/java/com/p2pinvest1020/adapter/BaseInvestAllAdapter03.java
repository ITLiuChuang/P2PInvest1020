package com.p2pinvest1020.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p2pinvest1020.viewholder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘闯 on 2017/3/14.
 */

public abstract class BaseInvestAllAdapter03<T> extends BaseAdapter {
     private List<T> list = new ArrayList<>();

    public BaseInvestAllAdapter03(List<T> list) {
        if (list != null && list.size() > 0) {
            this.list.clear();
            this.list.addAll(list);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder baseHolder = null;
        if(convertView==null) {
            baseHolder = getHolder();
        }else{
            baseHolder = (BaseHolder) convertView.getTag();
        }
        T t = list.get(position);
        baseHolder.setData(t);

        return baseHolder.getView();
    }

    public abstract BaseHolder getHolder() ;

}
