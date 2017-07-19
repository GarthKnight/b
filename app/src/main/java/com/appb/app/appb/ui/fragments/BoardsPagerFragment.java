package com.appb.app.appb.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.adapters.BoardsPagerAdapter;

import butterknife.BindView;

/**
 * Created by seishu on 18.07.17.
 */

public class BoardsPagerFragment extends BaseFragment {
    @BindView(R.id.vpBoards)
    ViewPager vpBoards;
    @BindView(R.id.tabs)
    TabLayout tabView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_boards_pager, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        vpBoards.setAdapter(new BoardsPagerAdapter(getChildFragmentManager(), new String[]{"Мои доски", "Мои категории"}));
        tabView.setupWithViewPager(vpBoards);
    }
}
