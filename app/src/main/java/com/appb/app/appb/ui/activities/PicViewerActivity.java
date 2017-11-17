package com.appb.app.appb.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.appb.app.appb.R;
import com.appb.app.appb.api.API;
import com.appb.app.appb.custom.TouchableViewPager;
import com.appb.app.appb.data.DvachMediaFile;
import com.appb.app.appb.ui.adapters.ViewerAdapter;
import com.appb.app.appb.ui.fragments.WebmFragment;
import com.appb.app.appb.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 14.03.2017.
 */

public class PicViewerActivity extends BaseActivity {

    public static final String FILES = "files";
    public static final String POS = "pos";
    public static final String TAG = "picViewer";
    private static final int REQUEST_CODE = 228;

    @BindView(R.id.vpPicPager)
    TouchableViewPager vpPicPager;

    private ArrayList<DvachMediaFile> dvachMediaFiles;
    public DvachMediaFile dvachMediaFile;

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
                        if(Build.VERSION.SDK_INT >=23){
                            checkIfWritable();
                        }
                        dvachMediaFile = dvachMediaFiles.get(vpPicPager.getCurrentItem());
                        downloadFile(API.URL + dvachMediaFile.getPath());
                        return true;

                    default:
                        return this.onMenuItemClick(item);

                }
            }
        });
        myPopup.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkIfWritable() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void downloadFile(String path){
        API.getInstance().dowloadFileWithDynamicUrl(path)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        writeResponseBodyToDisk(responseBody);
                    }
                });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File file = new File(Utils.getPicturesDirectory("2ch") + File.separator + dvachMediaFile.getName());
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, (path, uri) -> {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                });

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }



}

