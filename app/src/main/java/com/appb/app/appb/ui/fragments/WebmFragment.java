package com.appb.app.appb.ui.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.ForegroundImageView;
import com.bumptech.glide.Glide;
import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by 1 on 26.03.2017.
 */

public class WebmFragment extends BaseFragment {

    private static final String PATH = "path";
    private static final String THUMBNAIL = "thumbnail";
    private static final String CURRENT_POSITION = "currentpositon";
    int currentPosition;


    @BindView(R.id.bvpWebm)
    BetterVideoPlayer bvpWebm;
    @BindView(R.id.fivPlay)
    ForegroundImageView fivPlay;
    @BindView(R.id.btnPlay)
    Button btnPlay;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webm, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        Glide.with(getContext()).load(getArguments().getString(THUMBNAIL)).asBitmap().into(fivPlay);
        bvpWebm.setVisibility(GONE);
        Log.d("yoba", "init: " + R.drawable.ic_play1);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bvpWebm.setVisibility(View.VISIBLE);
                fivPlay.setVisibility(GONE);
                btnPlay.setVisibility(GONE);
                bvpWebm.setCallback(betterVideoCallback);
                bvpWebm.setSource(Uri.parse(getArguments().getString(PATH)));
                bvpWebm.setLoop(true);
            }
        });

        fivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bvpWebm.setVisibility(View.VISIBLE);
                fivPlay.setVisibility(GONE);
                btnPlay.setVisibility(GONE);
                bvpWebm.setCallback(betterVideoCallback);
                bvpWebm.setSource(Uri.parse(getArguments().getString(PATH)));
                bvpWebm.setLoop(true);
            }
        });
    }


    BetterVideoCallback betterVideoCallback = new BetterVideoCallback() {
        @Override
        public void onStarted(BetterVideoPlayer player) {

        }

        @Override
        public void onPaused(BetterVideoPlayer player) {

        }

        @Override
        public void onPreparing(BetterVideoPlayer player) {

        }

        @Override
        public void onPrepared(BetterVideoPlayer player) {

        }

        @Override
        public void onBuffering(int percent) {

        }

        @Override
        public void onError(BetterVideoPlayer player, Exception e) {

        }

        @Override
        public void onCompletion(BetterVideoPlayer player) {

        }

        @Override
        public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {

        }
    };

    public static WebmFragment newInstance(String path, String thumbnail) {
        Bundle args = new Bundle();
        args.putString(PATH, path);
        args.putString(THUMBNAIL, thumbnail);
        WebmFragment fragment = new WebmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onScrolledPause() {
        bvpWebm.pause();
    }

    public void onBackPress() {
        bvpWebm.stop();
    }

    public BetterVideoPlayer getBvpWebm() {
        return bvpWebm;
    }

    public void onOrientationChanged() {
        bvpWebm.setInitialPosition(currentPosition);
        Log.d("currentpos", "onOrientationChanged: " + currentPosition);
        bvpWebm.start();
        bvpWebm.setAutoPlay(true);
        bvpWebm.setVisibility(View.VISIBLE);
        fivPlay.setVisibility(GONE);
        btnPlay.setVisibility(GONE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getInt(CURRENT_POSITION, bvpWebm.getCurrentPosition());
    }
}
