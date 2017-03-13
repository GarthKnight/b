package com.appb.app.appb.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.data.File;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 1 on 13.03.2017.
 */

public class PicViewerAdapter extends PagerAdapter {

    ArrayList<File> files;

    public PicViewerAdapter(ArrayList<File> files){
        this.files = files;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        String url = "http://2ch.hk" + (files.get(position).getPath());
        PhotoView photoView = new PhotoView(container.getContext());
        Context context = photoView.getContext();
        Glide.with(context).load(url).asBitmap().into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
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

