package com.appb.app.appb.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.appb.app.appb.R;
import com.appb.app.appb.adapters.ViewerAdapter;
import com.appb.app.appb.custom.CustomViewPager;
import com.appb.app.appb.custom.SwipeBackLayout;
import com.appb.app.appb.data.File;
import com.appb.app.appb.fragments.BaseFragment;
import com.appb.app.appb.fragments.WebmFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 1 on 14.03.2017.
 */

public class PicViewerActivity extends BaseActivity {

    public static final String FILES = "files";
    public static final String POS = "pos";
    String TAG = "picviewer";


    @BindView(R.id.vpPicPager)
    CustomViewPager vpPicPager;

    ViewerAdapter viewerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pic_viewer);
        bindUI(this);
        getIntent().getExtras().getInt(POS);
//        setDragEdge(SwipeBackLayout.DragEdge.TOP);
//        getSwipeBackLayout().setScrollChild(vpPicPager);

        vpPicPager.setOnTouchListener(new View.OnTouchListener() {

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

                    Log.d(TAG, "onTouch: " + x + " | " + y );

                    if (Math.abs(startX - event.getX())*2 + 50 < Math.abs(startY - event.getY())) {
                        int pos = vpPicPager.getCurrentItem();
                        Fragment fragment = getFragmentForPosition(pos);
                        if(fragment instanceof WebmFragment){
                            ((WebmFragment) fragment).onBackPress();
                        }
                        finish();
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void init() {
        final ArrayList<File> files = getIntent().getExtras().getParcelableArrayList(FILES);
        viewerAdapter = new ViewerAdapter(files, getSupportFragmentManager());
        vpPicPager.setAdapter(viewerAdapter);
        vpPicPager.setPageMargin(16);
        vpPicPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                getSwipeBackLayout().interceptStartY = 0;
//                getSwipeBackLayout().interceptStartX = 0;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//                    getSwipeBackLayout().setEnabled(false);
//                } else {
//                    getSwipeBackLayout().setEnabled(true);
//                }
//                getSwipeBackLayout().interceptStartY = 0;
//                getSwipeBackLayout().interceptStartX = 0;
                int pos = vpPicPager.getCurrentItem();
                Fragment fragment = getFragmentForPosition(pos);
                if(fragment instanceof WebmFragment){
                    ((WebmFragment) fragment).onScrolledPause();
                }
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        vpPicPager.setCurrentItem(getIntent().getExtras().getInt(POS));
    }

    public static String makeFragmentName(int containerViewId, long id) {
        return "android:switcher:" + containerViewId + ":" + id;
    }

    public @Nullable
    Fragment getFragmentForPosition(int position) {
        String tag = makeFragmentName(R.id.vpPicPager, position);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int pos = vpPicPager.getCurrentItem();
        Fragment fragment = getFragmentForPosition(pos);
        if(fragment instanceof WebmFragment){
            ((WebmFragment) fragment).onBackPress();
        }
    }

    //    public static PicViewerFragment create(ArrayList<File> files, int pPos) {
//        Bundle args = new Bundle();
//        args.putParcelableArrayList(FILES, files);
//        args.putInt("PPOS", pPos);
//        PicViewerFragment fragment = new PicViewerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
}

