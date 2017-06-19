package com.appb.app.appb.ui.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.File;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Garth Knight on 07.04.17.
 */

public abstract class BaseRVAdapterWithImages<VH extends VHImages> extends RecyclerView.Adapter<VH> {

    static final String NUMBER_SYMBOL = "№";
    ThumbnailsAdapter thumbnailsAdapter;


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        VHImages holder = ((VHImages) vh);
        ArrayList<File> files = getFiles(position);

        if (files.size() > 0){
            holder.rvThumbnails.setVisibility(VISIBLE);
        } else {
            holder.rvThumbnails.setVisibility(GONE);
        }

        Context context = holder.rvThumbnails.getContext();
        thumbnailsAdapter = new ThumbnailsAdapter(getFiles(position)){
            @Override
            public void onPictureClick(int position) {
                super.onPictureClick(position);
                onThumbnailClick(position, files);
            }
        };
        holder.rvThumbnails.setAdapter(thumbnailsAdapter);
        holder.rvThumbnails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    }

    public void onThumbnailClick(int position, ArrayList<File> files){}

    public abstract ArrayList<File> getFiles(int pos);

}
