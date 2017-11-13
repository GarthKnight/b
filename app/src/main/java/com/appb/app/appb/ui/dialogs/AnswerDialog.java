package com.appb.app.appb.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.ThumbnailsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 31.03.2017.
 */

public class AnswerDialog extends Dialog {

    static final String NUMBER_SYMBOL = "№";

    private ArrayList<Post> posts;
    private ThumbnailsAdapter thumbnailsAdapter;
    private Post answer;


    @BindView(R.id.rvThumbnails)
    RecyclerView rvThumbnails;
    @BindView(R.id.tvCommentDate)
    TextView tvCommentDate;
    @BindView(R.id.tvCommentNumer)
    TextView tvCommentNumber;
    @BindView(R.id.tvTextComment)
    TextViewWithClickableSpan tvTextComment;

    public AnswerDialog(Context context, ArrayList<Post> posts, Post answer) {
        super(context);
        this.posts = posts;
        this.answer = answer;
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

        String postNum = String.format("%s%s", NUMBER_SYMBOL, answer.getNum());
        tvCommentNumber.setText(postNum);
        Spanned text = Html.fromHtml(answer.getComment());
        tvCommentDate.setText(answer.getDate());

        initRV();

        if (TextUtils.isEmpty(text)) {
            tvTextComment.setVisibility(GONE);
        } else {

            tvTextComment.setSpannableText(text);
            tvTextComment.setLinkClickListener(number -> {

                for (Post post : posts) {
                    if (post.getNum() == number) {
                        AnswerDialog dialog = new AnswerDialog(getContext(), posts, post){
                            @Override
                            public void onThumbnailClick(int position, ArrayList<File> files) {
                                super.onThumbnailClick(position, files);
                                AnswerDialog.this.startPicViewerActivity(files, position);
                            }
                        };
                        dialog.show();
                        break;
                    }
                }
            });
        }


    }

    private void startPicViewerActivity(ArrayList<File> files, int pos) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, pos);
        getContext().startActivity(intent);
    }


    private void initRV() {

        if (answer.getFiles().isEmpty()) {
            rvThumbnails.setVisibility(GONE);
        } else {
            rvThumbnails.setVisibility(View.VISIBLE);
        }

        thumbnailsAdapter = new ThumbnailsAdapter(answer.getFiles()) {
            @Override
            public void onPictureClick(int positionOfImage) {
                super.onPictureClick(positionOfImage);
                onThumbnailClick(positionOfImage, answer.getFiles());
            }
        };
        rvThumbnails.setAdapter(thumbnailsAdapter);
        rvThumbnails.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    public void onThumbnailClick(int position, ArrayList<File> files) {
    }

}
