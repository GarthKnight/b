package com.appb.app.appb.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.fragments.PicViewerFragment;
import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 10.03.2017.
 */

public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.ViewHolder> {

    ArrayList<Thread> threads;
    private FragmentManager fragmentManager;

    public ThreadListAdapter(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public ThreadListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);
        return new ThreadListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int size = (threads.get(position).getPosts().get(0).getFiles().size());
        String url = "http://2ch.hk";
        String num = ("№" + String.valueOf(threads.get(position).getPosts().get(0).getNum()));

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

        for (int i = 0; i < size; i++) {
            String path = url + (threads.get(position).getPosts().get(0).getFiles().get(i).getThumbnail());
            Context context = holder.imageViews.get(i).getContext();
            Glide.with(context).load(path).asBitmap().into(holder.imageViews.get(i));
            holder.textViews.get(i).setText(threads.get(position).getPosts().get(0).getFiles().get(i).getName());
            final int iFinal = i;
            holder.imageViews.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onItemClick(v, iFinal);
                }
            });
        }

        holder.tvDateThread.setText(threads.get(position).getPosts().get(0).getDate());
        holder.tvThreadNumer.setText(num);
        holder.tvThreadName.setText(Html.fromHtml(threads.get(position).getPosts().get(0).getName()));
        holder.tvCommentThread.setText(Html.fromHtml(threads.get(position).getPosts().get(0).getComment()));
    }

    public void onItemClick(View v, int position) {

    }

    @Override
    public int getItemCount() {
        return threads.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.tvDateThread)
        TextView tvDateThread;
        @BindView(R.id.tvThreadNumer)
        TextView tvThreadNumer;
        @BindView(R.id.tvThreadName)
        TextView tvThreadName;
        @BindView(R.id.tvCommentThread)
        TextView tvCommentThread;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


}
