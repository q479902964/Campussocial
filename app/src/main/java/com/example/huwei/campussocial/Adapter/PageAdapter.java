package com.example.huwei.campussocial.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by HUWEI on 2017/5/1.
 */

public class PageAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    private LayoutInflater mInflater;
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
