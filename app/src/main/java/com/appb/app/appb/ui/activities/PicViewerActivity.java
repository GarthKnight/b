package com.appb.app.appb.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TouchableViewPager;
import com.appb.app.appb.data.File;
import com.appb.app.appb.ui.adapters.ViewerAdapter;
import com.appb.app.appb.ui.fragments.WebmFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 1 on 14.03.2017.
 */

public class PicViewerActivity extends BaseActivity {

    public static final String FILES = "files";
    public static final String POS = "pos";
    public static final String TAG = "picViewer";

    @BindView(R.id.vpPicPager)
    TouchableViewPager vpPicPager;

    private ViewerAdapter viewerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pic_viewer);
        bindUI(this);
    }


    @Override
    public void init() {
        initPager();
    }

    private void initPager() {
        ArrayList<File> files = getIntent().getExtras().getParcelableArrayList(FILES);
        viewerAdapter = new ViewerAdapter(files, getSupportFragmentManager());
        vpPicPager.setAdapter(viewerAdapter);
        vpPicPager.setPageMargin(16);
        vpPicPager.addOnPageChangeListener(onPageChangeListener);
        vpPicPager.setCurrentItem(getIntent().getExtras().getInt(POS));
        vpPicPager.setOnTouchListener(backSwipeTouchListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int pos = vpPicPager.getCurrentItem();
        Fragment fragment = getFragmentForPosition(pos);
        if (fragment instanceof WebmFragment) {
            ((WebmFragment) fragment).onBackPress();
        }
    }

    private View.OnTouchListener backSwipeTouchListener = new View.OnTouchListener() {

        float startX = 0;
        float startY = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startX = event.getRawX();
                startY = event.getRawY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                float x = Math.abs(startX - event.getX());
                float y = Math.abs(startY - event.getY());

                Log.d(TAG, "onTouch: " + x + " | " + y);

                if (Math.abs(startX - event.getX()) * 2 + 25 < Math.abs(startY - event.getY())) {
                    int pos = vpPicPager.getCurrentItem();
                    Fragment fragment = getFragmentForPosition(pos);
                    if (fragment instanceof WebmFragment) {
                        ((WebmFragment) fragment).onBackPress();
                    }
                    finish();
                }
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int state) {
            int pos = vpPicPager.getCurrentItem();
            Fragment fragment = getFragmentForPosition(pos);
            if (fragment instanceof WebmFragment) {
                ((WebmFragment) fragment).stopVideo();
            }
        }
    };

    public static String makeVPFragmentName(int containerViewId, long id) {
        return "android:switcher:" + containerViewId + ":" + id;
    }

    public
    @Nullable
    Fragment getFragmentForPosition(int position) {
        String tag = makeVPFragmentName(R.id.vpPicPager, position);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment;
    }

}

