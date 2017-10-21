package com.appb.app.appb.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.appb.app.appb.R;
import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1 on 06.03.2017.
 */

public class BaseActivity extends MvpAppCompatActivity {

    private static final String TAG = "BaseActivity";
    public Unbinder unbinder;

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void bindUI(Activity v) {
        unbinder = ButterKnife.bind(this);
        init();
    }

    public void init() {

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
        hideKeyboard();
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

    public void clearStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public Fragment findFragmentById(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }

    public void checkFragmentStack() {
        log("getBackStackEntryCount: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void log(String s) {
        if (s != null) {
            Log.d("Base Activity", s);
        }
    }

    protected void focus(final EditText editText) {
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
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
