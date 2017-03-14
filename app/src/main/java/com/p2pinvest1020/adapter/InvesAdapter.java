package com.p2pinvest1020.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.p2pinvest1020.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by 刘闯 on 2017/3/14.
 */

public class InvesAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> list = new ArrayList<>();

    public InvesAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        if (list != null && list.size() > 0) {
            this.list = list;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
