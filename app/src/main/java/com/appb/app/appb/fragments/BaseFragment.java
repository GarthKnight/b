package com.appb.app.appb.fragments;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1 on 06.03.2017.
 */

public class BaseFragment extends Fragment {

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
}
