package com.appb.app.appb.ui.activities;

import android.os.Bundle;
import android.util.Log;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.fragments.borads.BoardsTabFragment;
import com.crashlytics.android.Crashlytics;
import com.vk.sdk.util.VKUtil;

import io.fabric.sdk.android.Fabric;

public class MainTabActivity extends MerlinActivity {


    private static final String TAG = "MainTabActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        bindUI(this);
        showFragment(new BoardsTabFragment(), false);
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        for (String string : fingerprints) {
            Log.d(TAG, string);
        }
        Log.d(TAG, getApplicationContext().getPackageName());
    }

}


