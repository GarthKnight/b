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
import com.appb.app.appb.data.Data;
import com.appb.app.appb.mvp.presenters.BoardsListPresenter;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.appb.app.appb.ui.adapters.BoardsPagerAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;

/**
 * Created by seishu on 18.07.17.
 */

public class BoardsTabFragment extends BaseFragment implements BoardlistView {

    private static final String TAG = "BoardsTabFragment";

    @BindView(R.id.vpBoards)
    ViewPager vpBoards;
    @BindView(R.id.tabs)
    TabLayout tabView;

    @InjectPresenter
    BoardsListPresenter presenter;
    private BoardsPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_boards_pager, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        presenter.loadData();
        setUpAdapter();
    }

    @Override
    public void onError(String error) {
        Log.d(TAG, "onError: " + error);
    }

    @Override
    public void onDataLoaded() {
        log("onDataLoaded : " + TAG);
        adapter.notifyDataSetChanged();
    }

    private void setUpAdapter() {
        if (adapter == null) {
            adapter = new BoardsPagerAdapter(getChildFragmentManager(), new String[]{"Мои доски", "Мои категории"});
        }
        vpBoards.setAdapter(adapter);
        vpBoards.setPageMargin(6);
        vpBoards.setPageMarginDrawable(getResources().getDrawable(R.color.subGray));
        tabView.setupWithViewPager(vpBoards);
    }

}
