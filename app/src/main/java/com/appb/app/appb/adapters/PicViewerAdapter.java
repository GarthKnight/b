package com.appb.app.appb.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        String thubmUrl =  "http://2ch.hk" + (files.get(position).getThumbnail());
        if (tmp.toLowerCase().contains(".webm")) {
            ForegroundImageView fiv = new ForegroundImageView(container.getContext());
            Context context = fiv.getContext();
            Glide.with(context).load(thubmUrl).asBitmap().into(fiv);
            container.addView(fiv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            fiv.setForegroundResource(R.drawable.ic_play1);
            fiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, pos);
                }
            });

            return fiv;
//            BetterVideoPlayer bvp = new BetterVideoPlayer(container.getContext());
//            bvp.setSource(Uri.parse(url));
//            bvp.setWindow(((PicViewerActivity) container.getContext()).getWindow());
//            container.addView(bvp, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            return bvp;
        } else {
            
            PhotoView photoView = new PhotoView(container.getContext());
            Context context = photoView.getContext();
            Glide.with(context).load(url).asBitmap().into(photoView);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }

    public void onItemClick(View v, int position) {

    }

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




}

