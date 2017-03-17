package com.appb.app.appb.activities;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.appb.app.appb.R;
import com.appb.app.appb.adapters.PicViewerAdapter;
import com.appb.app.appb.custom.SwipeBackLayout;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.fragments.PicViewerFragment;


import java.util.ArrayList;

import butterknife.BindView;

import static java.security.AccessController.getContext;

/**
 * Created by 1 on 14.03.2017.
 */

public class PicViewerActivity extends SwipeBaseActivity {

    public static final String FILES = "files";
    public static final String POS = "pos";
    private static final String PATH = "path";
    private static final String NAME = "name";


    @BindView(R.id.vpPicPager)
    ViewPager vpPicPager;
    PicViewerAdapter picViewerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_viewer);
        bindUI(this);
        getIntent().getExtras().getInt(POS);

        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
    }


    @Override
    public void init() {
        final ArrayList<File> files = getIntent().getExtras().getParcelableArrayList(FILES);
        picViewerAdapter = new PicViewerAdapter(files){
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(PicViewerActivity.this, FullscreenActivity.class);
                intent.putExtra(PATH, files.get(position).getPath());
                intent.putExtra(NAME, files.get(position).getName());

                startActivity(intent);
            }
        };
        vpPicPager.setAdapter(picViewerAdapter);
        vpPicPager.setPageMargin(16);
    }

    @Override
    public void onResume() {
        super.onResume();
        vpPicPager.setCurrentItem(getIntent().getExtras().getInt(POS));
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

