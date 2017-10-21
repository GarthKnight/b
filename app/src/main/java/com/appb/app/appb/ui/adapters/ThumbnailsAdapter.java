package com.appb.app.appb.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appb.app.appb.R;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.File;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Logvinov.sv on 15.06.2017.
 */

public class ThumbnailsAdapter extends RecyclerView.Adapter<ThumbnailsAdapter.VHThumbnails> {

    private ArrayList<File> files;

    public ThumbnailsAdapter(ArrayList<File> _files){
        files = _files;
    }

    @Override
    public VHThumbnails onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);
        return new VHThumbnails(v);

    }

    @Override
    public void onBindViewHolder(VHThumbnails holder, int position) {
        String path = API.URL + files.get(position).getThumbnail();
        Context context = holder.ivThumbnail.getContext();
        Glide.with(context).load(path).asBitmap().into(holder.ivThumbnail);
        holder.ivThumbnail.setOnClickListener(v -> onPictureClick(position));
    }

    public void onPictureClick(int position){

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class VHThumbnails extends RecyclerView.ViewHolder{

        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;

        public VHThumbnails(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
