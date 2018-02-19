package com.appb.app.appb.ui.activities;

import android.os.Bundle;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.fragments.borads.BoardsTabFragment;

public class MainTabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        bindUI(this);
        showFragment(new BoardsTabFragment(), false);
    }

}


