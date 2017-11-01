package com.appb.app.appb.ui.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.activities.BaseActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1 on 06.03.2017.
 */

public class BaseFragment extends MvpAppCompatFragment {

    private Unbinder unbinder;

    public void bindUI(View v) {
        unbinder = ButterKnife.bind(this, v);
        init();
    }

    public void init() {

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void showError(String error) {
        if (getView() != null) {
            Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void showFragment(Fragment fragment, boolean addToBack) {
        ((BaseActivity) getActivity()).showFragment(fragment, addToBack);
    }

    public void log(String log) {
        Log.d(this.getClass().getName(), log);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }
}
