package com.appb.app.appb.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.dialogs.AnswersDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 31.03.2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.VHolder> {

    private ArrayList<Post> posts;

    public PostsAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new PostsAdapter.VHolder(v);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        final int filesCount = (posts.get(position).getFiles().size());
        String url = "http://2ch.hk";
        String postNum = ("№" + String.valueOf(posts.get(position).getNum()));
        final ArrayList<Integer> answersNumbers = new ArrayList<>();
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
                String path = url + (posts.get(position).getFiles().get(i).getThumbnail());
                Context context = holder.imageViews.get(i).getContext();
                Glide.with(context).load(path).asBitmap().into(holder.imageViews.get(i));
                holder.imageViews.get(i).setVisibility(VISIBLE);
                holder.textViews.get(i).setVisibility(VISIBLE);
                holder.textViews.get(i).setText(posts.get(position).getFiles().get(i).getName());

                holder.imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(v, pFinal, iFinal);
                    }
                });
            } else {
                holder.imageViews.get(i).setImageDrawable(null);
                holder.imageViews.get(i).setVisibility(GONE);
                holder.textViews.get(i).setVisibility(GONE);
            }
        }


        Spanned text = Html.fromHtml(posts.get(position).getComment());
        if (TextUtils.isEmpty(text)) {
            holder.tvTextComment.setVisibility(GONE);
        } else {
            holder.tvTextComment.setVisibility(VISIBLE);
        }

        holder.tvTextComment.setSpannableText(text);
        holder.tvTextComment.setLinkClickListener(new TextViewWithClickableSpan.LinkClickListener() {
            @Override
            public void onLinkClick(int number) {
                int tmp = -1;

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        tmp = i;
                    }
                }

                if (tmp != -1) {
                    onPrefClick(posts, tmp);
                }

            }
        });
        holder.tvCommentDate.setText(posts.get(position).getDate());
        holder.tvCommentNumber.setText(postNum);

        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getComment().contains(String.valueOf(posts.get(position).getNum()))) {
                answersNumbers.add(i);
            }
        }
        if (answersNumbers.size() != 0) {
            holder.tvAnswers.setText("Ответы");
            holder.tvAnswers.setVisibility(VISIBLE);
            holder.tvAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnswersDialog answersDialog = new AnswersDialog(v.getContext(), posts, answersNumbers);
                    answersDialog.show();
                }
            });
        } else {
            holder.tvAnswers.setVisibility(GONE);
        }

    }

    public void onPrefClick(ArrayList<Post> posts, int index) {

    }

    public void onItemClick(View v, int position, int pos) {
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }



public class VHolder extends RecyclerView.ViewHolder {

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
    TextView tvCommentNumber;
    @BindView(R.id.tvTextComment)
    TextViewWithClickableSpan tvTextComment;
    @BindView(R.id.tvAnswers)
    TextView tvAnswers;

    public VHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
}
