package com.appb.app.appb.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.mvp.presenters.BoardsListPresenter;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.appb.app.appb.ui.adapters.BoardsPagerAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;

/**
 * Created by seishu on 18.07.17.
 */

public class BoardsPagerFragment extends BaseFragment implements BoardlistView {

    private static final String TAG = "BoardsPagerFragment";

    @BindView(R.id.vpBoards)
    ViewPager vpBoards;
    @BindView(R.id.tabs)
    TabLayout tabView;

    @InjectPresenter
    BoardsListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_boards_pager, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        presenter.getData();
        vpBoards.setAdapter(new BoardsPagerAdapter(getChildFragmentManager(), new String[]{"Мои доски", "Мои категории"}));
        vpBoards.setPageMargin(6);
        vpBoards.setPageMarginDrawable(getResources().getDrawable(R.color.subGray));
        tabView.setupWithViewPager(vpBoards);
    }

    @Override
    public void onError(String error) {
        Log.d(TAG, "onError: " + error);
    }
}
