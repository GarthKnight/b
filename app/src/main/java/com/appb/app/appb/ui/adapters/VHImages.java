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
 * Created by janaperhun on 07.04.17.
 */

public class VHImages extends RecyclerView.ViewHolder {

    @BindViews({R.id.ivPic1, R.id.ivPic2, R.id.ivPic3, R.id.ivPic4, R.id.ivPic5, R.id.ivPic6, R.id.ivPic7, R.id.ivPic8, R.id.ivPic9,})
    List<ImageView> imageViews;
    @BindViews({R.id.tvPic1, R.id.tvPic2, R.id.tvPic3, R.id.tvPic4, R.id.tvPic5, R.id.tvPic6, R.id.tvPic7, R.id.tvPic8, R.id.tvPic9,})
    List<TextView> textViews;

    @BindView(R.id.llPicLine1)
    LinearLayout llPicLine1;
    @BindView(R.id.llPicLine2)
    LinearLayout llPicLine2;
    @BindView(R.id.llPicLine3)
    LinearLayout llPicLine3;

    public VHImages(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
