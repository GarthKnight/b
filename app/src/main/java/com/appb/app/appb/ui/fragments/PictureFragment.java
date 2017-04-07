package com.appb.app.appb.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by 1 on 26.03.2017.
 */

public class PictureFragment extends BaseFragment {

    private static final String PATH = "path";
    @BindView(R.id.pvPicture)
    PhotoView pvPicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_picture, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        Glide.with(getContext()).load(getArguments().getString(PATH)).asBitmap().into(pvPicture);
    }

    public static PictureFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString(PATH, path);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
