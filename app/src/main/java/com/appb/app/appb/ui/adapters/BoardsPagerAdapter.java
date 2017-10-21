package com.appb.app.appb.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.fragments.CategoriesListFragment;
import com.appb.app.appb.ui.fragments.MyFavoritesBoardsListFragment;

import rx.Observable;

/**
 * Created by Logvinov.sv on 14.07.2017.
 */

public class BoardsPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;
    private FragmentManager fragmentManager;

    public BoardsPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        fragmentManager = fm;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MyFavoritesBoardsListFragment();
        } else {
            return new CategoriesListFragment();
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

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Object fragment =  fragmentManager.findFragmentByTag(makeFragmentName(R.id.vpBoards, getItemId(0)));
        if (fragment != null) {
            ((MyFavoritesBoardsListFragment) fragment).notifyDataSetChanged();
        }

        fragment = fragmentManager.findFragmentByTag(makeFragmentName(R.id.vpBoards, getItemId(1)));
        if (fragment != null) {
            ((CategoriesListFragment) fragment).notifyDataSetChanged();
        }
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public long getItemId(int position) {
        return position;
    }
}
