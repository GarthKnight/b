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
    private ThumbnailsAdapter thumbnailsAdapter;


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        ArrayList<File> files = getFiles(position);

        if (files.size() > 0){
            vh.rvThumbnails.setVisibility(VISIBLE);
        } else {
            vh.rvThumbnails.setVisibility(GONE);
        }

        Context context = vh.rvThumbnails.getContext();
        thumbnailsAdapter = new ThumbnailsAdapter(getFiles(vh.getAdapterPosition())){
            @Override
            public void onPictureClick(int position) {
                super.onPictureClick(position);
                onThumbnailClick(position, files);
            }
        };
        vh.rvThumbnails.setAdapter(thumbnailsAdapter);
        vh.rvThumbnails.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));

    }

    public void onThumbnailClick(int position, ArrayList<File> files){}

    public abstract ArrayList<File> getFiles(int pos);

}
