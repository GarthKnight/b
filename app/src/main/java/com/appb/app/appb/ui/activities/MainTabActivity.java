package com.appb.app.appb.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.fragments.borads.BoardsTabFragment;

import butterknife.BindView;

public class MainTabActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        bindUI(this);
        showFragment(new BoardsTabFragment(), false);
    }

}


