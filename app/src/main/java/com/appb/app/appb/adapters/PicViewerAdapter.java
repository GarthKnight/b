package com.appb.app.appb.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.appb.app.appb.R;
import com.appb.app.appb.activities.PicViewerActivity;
import com.appb.app.appb.custom.ForegroundImageView;
import com.appb.app.appb.data.File;
import com.bumptech.glide.Glide;
import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 1 on 13.03.2017.
 */

public class PicViewerAdapter extends PagerAdapter {

    ArrayList<File> files;

    public PicViewerAdapter(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final int pos = position;
        String tmp = (files.get(position).getName());
        String url = "http://2ch.hk" + (files.get(position).getPath());
//        String thubmUrl = "http://2ch.hk" + (files.get(position).getThumbnail());

        if (tmp.toLowerCase().contains(".webm")) {
            BetterVideoPlayer bvp = new BetterVideoPlayer(container.getContext());
            bvp.setCallback(bvc);
            bvp.setSource(Uri.parse(url));
            bvp.setWindow(((PicViewerActivity) container.getContext()).getWindow());
            container.addView(bvp, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return bvp;
        } else {
            FrameLayout frame = new FrameLayout(container.getContext());
            ProgressBar progressBar = new ProgressBar(container.getContext(), null, android.R.attr.progressBarStyleSmall);
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(container.getContext()).load(url).asBitmap().into(photoView);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(100, 100);
            lp.gravity = Gravity.CENTER;
            container.addView(frame, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frame.addView(progressBar, lp);
            frame.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return frame;
        }

    }

//    public void onItemClick(View v, int position) {
//
//    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    BetterVideoCallback bvc = new BetterVideoCallback() {
        @Override
        public void onStarted(BetterVideoPlayer player) {
            //Log.i(TAG, "Started");
        }

        @Override
        public void onPaused(BetterVideoPlayer player) {
            //Log.i(TAG, "Paused");
        }

        @Override
        public void onPreparing(BetterVideoPlayer player) {
            //Log.i(TAG, "Preparing");
        }

        @Override
        public void onPrepared(BetterVideoPlayer player) {
            //Log.i(TAG, "Prepared");
        }

        @Override
        public void onBuffering(int percent) {
            //Log.i(TAG, "Buffering " + percent);
        }

        @Override
        public void onError(BetterVideoPlayer player, Exception e) {
            //Log.i(TAG, "Error " +e.getMessage());
        }

        @Override
        public void onCompletion(BetterVideoPlayer player) {
            //Log.i(TAG, "Completed");
        }

        @Override
        public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {

        }
    };


//            ForegroundImageView fiv = new ForegroundImageView(container.getContext());
//            Context context = fiv.getContext();
//            Glide.with(context).load(thubmUrl).asBitmap().into(fiv);
//            container.addView(fiv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            fiv.setForegroundResource(R.drawable.ic_play1);
//            fiv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClick(v, pos);
//                }
//            });
//
//            return fiv;
}

