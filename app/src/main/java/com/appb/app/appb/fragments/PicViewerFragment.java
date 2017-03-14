package com.appb.app.appb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.adapters.PicViewerAdapter;
import com.appb.app.appb.data.File;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by 1 on 13.03.2017.
 */

public class PicViewerFragment extends BaseFragment {

//    private static final String FILES = "files";
//    @BindView(R.id.vpPicPager) ViewPager vpPicPager;
//    PicViewerAdapter picViewerAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v =  inflater.inflate(R.layout.activity_pic_viewer, container, false);
//        bindUI(v);
//        return v;
//    }
//
//
//    @Override
//    public void init() {
//        ArrayList<File> files =  getArguments().getParcelableArrayList(FILES);
//        picViewerAdapter = new PicViewerAdapter(files);
//        vpPicPager.setAdapter(picViewerAdapter);
//        vpPicPager.setPageMargin(16);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        vpPicPager.setCurrentItem(getArguments().getInt("PPOS"));
//    }
//
//    public static PicViewerFragment create(ArrayList<File> files, int pPos) {
//        Bundle args = new Bundle();
//        args.putParcelableArrayList(FILES, files);
//        args.putInt("PPOS", pPos);
//        PicViewerFragment fragment = new PicViewerFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

//    public static PicViewerFragment newInstance(int pPos) {
//
//        Bundle args = new Bundle();
//        PicViewerFragment fragment = new PicViewerFragment();
//        fragment.setArguments(args);
//        args.putInt("PPOS", pPos);
//        return fragment;
//    }
}
