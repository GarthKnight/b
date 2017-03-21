package com.appb.app.appb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.VH> {

    ArrayList<Post> posts;

    public PostListAdapter (ArrayList<Post> posts) {
        this.posts = posts;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new PostListAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int size = (posts.get(position).getFiles().size());
        String url = "http://2ch.hk";
        String num = ("№" + String.valueOf(posts.get(position).getNum()));

        if (size < 1) {
            holder.llPicLine1.setVisibility(GONE);
            holder.llPicLine2.setVisibility(GONE);
            holder.llPicLine3.setVisibility(GONE);
        } else if (size > 0 && size < 4) {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(GONE);
            holder.llPicLine3.setVisibility(GONE);
        } else if (size > 3 && size < 7) {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(VISIBLE);
            holder.llPicLine3.setVisibility(GONE);
        } else {
            holder.llPicLine1.setVisibility(VISIBLE);
            holder.llPicLine2.setVisibility(VISIBLE);
            holder.llPicLine3.setVisibility(VISIBLE);
        }

        final int pFinal = position;
        for (int i = 0; i < size; i++) {
            final int iFinal = i;
            String path = url + (posts.get(position).getFiles().get(i).getThumbnail());
            Context context = holder.imageViews.get(i).getContext();
            Glide.with(context).load(path).asBitmap().into(holder.imageViews.get(i));
            holder.textViews.get(i).setText(posts.get(position).getFiles().get(i).getName());

            holder.imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(v, pFinal, iFinal);
                }
            });

        }
        String postNum = ("№" + String.valueOf(posts.get(position).getNum()));
        holder.tvTextComment.setText(Html.fromHtml(posts.get(position).getComment()));
        holder.tvCommentDate.setText(posts.get(position).getDate());
        holder.tvCommentNumber.setText(postNum);
    }

    public void onItemClick(View v, int position, int pos) {
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class VH extends RecyclerView.ViewHolder {

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
        @BindView(R.id.tvCommentDate) TextView tvCommentDate;
        @BindView(R.id.tvCommentNumer) TextView tvCommentNumber;
        @BindView(R.id.tvTextComment) TextView tvTextComment;


        public VH(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
