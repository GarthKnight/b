package com.appb.app.appb.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.activities.BaseActivity;
import com.appb.app.appb.ui.adapters.BoardsPagerAdapter;

import butterknife.BindView;

/**
 * Created by Logvinov.sv on 14.07.2017.
 */

public class BoardsPagerActivity extends BaseActivity {

    @BindView(R.id.vpBoards)
    ViewPager vpBoards;
    @BindView(R.id.tabs)
    TabLayout tabView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bindUI(this);
        vpBoards.setAdapter(new BoardsPagerAdapter(getSupportFragmentManager(), new String[]{"Мои доски", "Мои категории"}));
        tabView.setupWithViewPager(vpBoards);
    }
}
