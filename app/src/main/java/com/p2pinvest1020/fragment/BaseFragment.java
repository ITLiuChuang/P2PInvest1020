package com.p2pinvest1020.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p2pinvest1020.ui.LoadingPager;

import butterknife.ButterKnife;

/**
 * Created by 刘闯 on 2017/3/13.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPager loadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingPager = new LoadingPager(getActivity()) {


            @Override
            protected void onSuccess(ResultState resultState, View sucessView) {
                ButterKnife.bind(BaseFragment.this, sucessView);
                initData(resultState.getJson());
            }

            @Override
            protected String getUrl() {
                return getChildUrl();
            }

            @Override
            public int getViewId() {
                return getLayoutid();
            }
        };
        return loadingPager;
    }

    //每一个fragment返回的地址
    protected abstract String getChildUrl();

    protected abstract void initData(String json);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
//        initListener();
        loadingPager.loadData();
    }


    protected abstract void initListener();

    public abstract int getLayoutid();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
