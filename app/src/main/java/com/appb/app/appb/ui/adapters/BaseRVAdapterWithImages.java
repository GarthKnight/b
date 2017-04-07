package com.appb.app.appb.ui.adapters;

import android.content.Context;
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
 * Created by janaperhun on 07.04.17.
 */

public abstract class BaseRVAdapterWithImages<VH extends VHImages> extends RecyclerView.Adapter<VH> {

    static final String NUMBER_SYMBOL = "№";

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        VHImages holder = ((VHImages) vh);
        final int filesCount = getFiles(position).size();
        final int pFinal = position;
        if (filesCount < 1) {
            holder.llPicLine1.setVisibility(GONE);
            holder.llPicLine2.setVisibility(GONE);
            holder.llPicLine3.setVisibility(GONE);
        } else if (filesCount > 0 && filesCount < 4) {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(GONE);
            holder.llPicLine3.setVisibility(GONE);
        } else if (filesCount > 3 && filesCount < 7) {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(VISIBLE);
            holder.llPicLine3.setVisibility(GONE);
        } else {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(VISIBLE);
            holder.llPicLine3.setVisibility(VISIBLE);
        }

        Log.d("yoba", "onBindViewHolder: pos: " + position + " filescount: " + filesCount);
        for (int i = 0; i < 9; i++) {
            final int iFinal = i;
            if (i < filesCount) {
                String path = API.URL + getFiles(position).get(i).getThumbnail();
                Context context = holder.imageViews.get(i).getContext();
                Glide.with(context).load(path).asBitmap().into(holder.imageViews.get(i));
                holder.imageViews.get(i).setVisibility(VISIBLE);
                holder.textViews.get(i).setVisibility(VISIBLE);
                holder.textViews.get(i).setText(getFiles(position).get(i).getName());

                holder.imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onImageClick(v, pFinal, iFinal);
                    }
                });
            } else {
                holder.imageViews.get(i).setImageDrawable(null);
                holder.imageViews.get(i).setVisibility(GONE);
                holder.textViews.get(i).setVisibility(GONE);
            }
        }

    }

    public void onImageClick(View v, int pFinal, int iFinal) {

    }


    public abstract ArrayList<File> getFiles(int pos);

}
