package com.appb.app.appb.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appb.app.appb.ui.fragments.BaseFragment;

/**
 * Created by Logvinov.sv on 14.07.2017.
 */

public class BoardsPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;

    public BoardsPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BaseFragment();
        } else {
            return new BaseFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
