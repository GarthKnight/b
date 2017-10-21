package com.appb.app.appb.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.api.API;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.activities.StartActivity;
import com.appb.app.appb.ui.adapters.ThumbnailsAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 31.03.2017.
 */

public class AnswerDialog extends Dialog {

    static final String NUMBER_SYMBOL = "№";

    private ArrayList<Post> posts;
    private ThumbnailsAdapter thumbnailsAdapter;
    private int index;
    private int pFinal;

    @BindView(R.id.rvThumbnails)
    RecyclerView rvThumbnails;
    @BindView(R.id.tvCommentDate)
    TextView tvCommentDate;
    @BindView(R.id.tvCommentNumer)
    TextView tvCommentNumber;
    @BindView(R.id.tvTextComment)
    TextViewWithClickableSpan tvTextComment;

    public AnswerDialog(Context context, ArrayList<Post> posts, int index) {
        super(context);
        this.posts = posts;
        this.index = index;
        pFinal = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        init();
    }

    public void init() {

        int filesSize = posts.get(index).getFiles().size();

        String postNum = String.format("%s%s", NUMBER_SYMBOL, posts.get(index).getNum());
        tvCommentNumber.setText(postNum);

        tvCommentDate.setText(posts.get(index).getDate());
        Spanned text = Html.fromHtml(posts.get(index).getComment());

        thumbnailsAdapter = new ThumbnailsAdapter(posts.get(index).getFiles()){
            @Override
            public void onPictureClick(int positionOfImage) {
                super.onPictureClick(positionOfImage);
                onThumbnailClick(positionOfImage, posts.get(index).getFiles());
            }
        };
        rvThumbnails.setAdapter(thumbnailsAdapter);
        rvThumbnails.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));


        if (TextUtils.isEmpty(text)) {
            tvTextComment.setVisibility(GONE);
        } else {

            tvTextComment.setSpannableText(text);
            tvTextComment.setLinkClickListener(number -> {

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        new AnswerDialog(getContext(), posts, i).show();
                        break;
                    }
                }
            });
        }


    }

    public void onThumbnailClick(int position, ArrayList<File> files) {
    }

}
