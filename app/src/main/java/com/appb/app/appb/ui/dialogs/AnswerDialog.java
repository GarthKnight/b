package com.appb.app.appb.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.activities.PicViewerActivity;
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

    ArrayList<Post> posts;
    int index;
    int pFinal;
    Context context;



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

    public AnswerDialog(Context context, ArrayList<Post> posts, int index) {
        super(context);
        this.context = context;
        this.posts = posts;
        this.index = index;
        pFinal = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
        init();

    }

    public void init(){
        int filesSize = posts.get(index).getFiles().size();
        String number = "№" + posts.get(index).getNum();
        tvCommentDate.setText(Html.fromHtml(posts.get(index).getDate()));
        tvCommentNumer.setText(number);

        Spanned text = Html.fromHtml(posts.get(index).getComment());
        if (TextUtils.isEmpty(text)) {
            tvTextComment.setVisibility(GONE);
        }

        tvTextComment.setSpannableText(text);
        tvTextComment.setLinkClickListener(new TextViewWithClickableSpan.LinkClickListener() {
            @Override
            public void onLinkClick(int number) {
                int tmp = -1;


                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        tmp = i;
                    }
                }

                if (tmp != -1) {
                    AnswerDialog answerDialog = new AnswerDialog(context, posts, tmp){
                        @Override
                        public void onItemClick(View v, int position, int pos) {
                            Intent intent = new Intent(getContext(), PicViewerActivity.class);
                            intent.putExtra(FILES, posts.get(position).getFiles());
                            intent.putExtra(POS, pos);
                            context.startActivity(intent);
                        }
                    };
                    answerDialog.show();
                }
            }
        });

        visibilitySetter();

        for (int i = 0; i < filesSize; i++) {
            final int iFinal = i;
            String url = "http://2ch.hk";
            String path = url + (posts.get(index).getFiles().get(i).getThumbnail());
            Context context = imageViews.get(i).getContext();
            Glide.with(context).load(path).asBitmap().into(imageViews.get(i));
            textViews.get(i).setText(posts.get(index).getFiles().get(i).getName());

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



    public void visibilitySetter(){
        int filesSize = posts.get(index).getFiles().size();

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
    }

}