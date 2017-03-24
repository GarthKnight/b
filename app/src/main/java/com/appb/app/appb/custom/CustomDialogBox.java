package com.appb.app.appb.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 24.03.2017.
 */

public class CustomDialogBox extends Dialog {

    String date;
    String num;
    String comment;
    int filesSize;
    Post post;
    private Unbinder unbinder;
    int pFinal;


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

    @BindView(R.id.tvCommentDate)
    TextView tvCommentDate;
    @BindView(R.id.tvCommentNumer)
    TextView tvCommentNumer;
    @BindView(R.id.tvTextComment)
    TextViewWithClickableSpan tvTextComment;


    public CustomDialogBox(Context context, String date, String num, String comment, int filesSize, Post post,  int positionForSpan) {
        super(context);
        this.date = date;
        this.num = num;
        this.comment = comment;
        this.filesSize = filesSize;
        this.post = post;
        pFinal = positionForSpan;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        setContentView(R.layout.custom_dialog);
        unbinder = ButterKnife.bind(this);

        tvCommentDate.setText(date);
        tvCommentNumer.setText(num);
        tvTextComment.setText(comment);

        if (filesSize < 1) {
            llPicLine1.setVisibility(GONE);
            llPicLine2.setVisibility(GONE);
            llPicLine3.setVisibility(GONE);
        } else if (filesSize > 0 && filesSize < 4) {
            llPicLine1.setVisibility(VISIBLE);
            llPicLine2.setVisibility(GONE);
            llPicLine3.setVisibility(GONE);
        } else if (filesSize > 3 && filesSize < 7) {
            llPicLine1.setVisibility(VISIBLE);
            llPicLine2.setVisibility(VISIBLE);
            llPicLine3.setVisibility(GONE);
        } else {
            llPicLine1.setVisibility(VISIBLE);
            llPicLine2.setVisibility(VISIBLE);
            llPicLine3.setVisibility(VISIBLE);
        }

        for (int i = 0; i < filesSize; i++) {
            final int iFinal = i;
            String url = "http://2ch.hk";
            String path = url + (post.getFiles().get(i).getThumbnail());
            Context context = imageViews.get(i).getContext();
            Glide.with(context).load(path).asBitmap().into(imageViews.get(i));
            textViews.get(i).setText(post.getFiles().get(i).getName());

            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, pFinal, iFinal);
                    Log.d("yoba", "de vidos");
                }
            });

        }

    }

    public void onItemClick(View v, int position, int pos) {
    }


    interface ItemClick{
        public void onItemClickHelper(View v, int position, int pos);
    }
}
