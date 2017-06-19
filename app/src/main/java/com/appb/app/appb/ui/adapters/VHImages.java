package com.appb.app.appb.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Garth Knight on 07.04.17.
 */

public class VHImages extends RecyclerView.ViewHolder {

    @BindView(R.id.rvThumbnails)
    RecyclerView rvThumbnails;

    public VHImages(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
