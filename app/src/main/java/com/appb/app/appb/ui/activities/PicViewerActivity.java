package com.appb.app.appb.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TouchableViewPager;
import com.appb.app.appb.data.DvachMediaFile;
import com.appb.app.appb.ui.adapters.ViewerAdapter;
import com.appb.app.appb.ui.fragments.WebmFragment;
import com.appb.app.appb.utils.Utils;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1 on 14.03.2017.
 */

public class PicViewerActivity extends BaseActivity {

    public static final String FILES = "files";
    public static final String POS = "pos";
    public static final String TAG = "picViewer";

    @BindView(R.id.vpPicPager)
    TouchableViewPager vpPicPager;

    private ArrayList<DvachMediaFile> dvachMediaFiles;

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
        dvachMediaFiles = getIntent().getExtras().getParcelableArrayList(FILES);
        ViewerAdapter viewerAdapter = new ViewerAdapter(dvachMediaFiles, getSupportFragmentManager());
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

    @OnClick(R.id.ibMenu)
    public void onMenuClick(View v) {
        PopupMenu myPopup = new PopupMenu(this, v);
        myPopup.inflate(R.menu.menu_picture);
        myPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.download:
                        Log.d(TAG, "onMenuItemClick: ");
                        savePicture();
                        return true;

                    default:
                        return this.onMenuItemClick(item);

                }
            }
        });
        myPopup.show();
    }

    private void savePicture() {
        DvachMediaFile dvachMediaFile = dvachMediaFiles.get(vpPicPager.getCurrentItem());
        File file = new File(Utils.getDocumentFolderPath("2ch") + "/" + dvachMediaFile.getName());
        Bitmap bitmap = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray;

        try {

            bitmap = Glide.with(this).load(dvachMediaFile.getPath()).asBitmap().into(100, 100).get();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap != null){
        }
    }

}

