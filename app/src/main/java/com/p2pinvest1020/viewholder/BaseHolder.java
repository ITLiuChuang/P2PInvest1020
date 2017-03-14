package com.p2pinvest1020.viewholder;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by 刘闯 on 2017/3/14.
 */

public abstract class BaseHolder<T> {
    private final View rootView;
    private T t;

    public BaseHolder() {
        rootView = initView();
        ButterKnife.bind(this, rootView);
        rootView.setTag(this);

    }

    public View getView() {
        return rootView;
    }

    public void setData(T t) {
        this.t = t;
        setChildData();
    }
    public T getT(){
        return t;
    }


    public abstract void setChildData();

    public abstract View initView();
}
