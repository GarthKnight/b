package com.appb.app.appb.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appb.app.appb.PrefUtils;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.ui.fragments.BaseFragment;
import com.appb.app.appb.ui.fragments.BoardsCategoriesListFragment;
import com.appb.app.appb.ui.fragments.BoardsListFragment;

import java.util.ArrayList;
import java.util.HashSet;

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
            return BoardsListFragment.create(Data.getInstance().getMyBoards());
        } else {
            return new BoardsCategoriesListFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
