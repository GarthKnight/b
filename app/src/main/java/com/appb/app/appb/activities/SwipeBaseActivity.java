package com.appb.app.appb.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.appb.app.appb.R;
import com.liuguangqiang.swipeback.SwipeBackActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1 on 14.03.2017.
 */

public class SwipeBaseActivity extends SwipeBackActivity {

    private static final String TAG = "Swipe";
    private Unbinder unbinder;
    private ProgressDialog pbDialog;
    private Typeface ptSansRegular;
    private Typeface ptSansBold;


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void bindUI(Activity v) {
        unbinder = ButterKnife.bind(this);
        init();
    }

    public void init(){

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void showFragment(Fragment fragment, boolean addToBack, int containerId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }



    public void showFragment(Fragment fragment, boolean addToBack) {
        showFragment(fragment, addToBack, R.id.container);
    }


    public void addFragment(Fragment fragment, boolean addToBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.add(R.id.container, fragment);
        if (addToBack) transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkFragmentStack();
    }

    public void checkFragmentStack() {
        log("getBackStackEntryCount: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    public void log(String s) {
        if (s != null) {
            Log.d(getClass().getSimpleName(), s);
        }
    }


    public void hideStatusBar() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
