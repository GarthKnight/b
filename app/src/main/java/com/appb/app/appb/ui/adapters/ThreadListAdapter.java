package com.appb.app.appb.ui.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Thread;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 1 on 10.03.2017.
 */

public class ThreadListAdapter extends BaseRVAdapterWithImages<ThreadListAdapter.VHThread> {

    ArrayList<Thread> threads;
    ThumbnailsAdapter thumbnailsAdapter;
    Context context;

    public ThreadListAdapter(ArrayList<Thread> threads, Context context) {
        this.threads = threads;
        this.context = context;

    }

    @Override
    public VHThread onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);
        return new VHThread(v);
    }

    @Override
    public void onBindViewHolder(final VHThread holder, int position) {
        super.onBindViewHolder(holder, position);
        String threadNumber = String.format("%s%s", NUMBER_SYMBOL,
                threads.get(position).getPosts().get(0).getNum());

        Spanned comment = Html.fromHtml(threads.get(position).getPosts().get(0).getComment());
        holder.tvCommentThread.setOnClickListener(v -> onCommentClick(v, holder.getAdapterPosition()));
        holder.tvDateThread.setText(threads.get(position).getPosts().get(0).getDate());
        holder.tvThreadNumber.setText(threadNumber);
        holder.tvCommentThread.setText(comment);
        holder.tvThreadCounter.setText("#" + position);


        ArrayList<File> files = threads.get(position).getPosts().get(0).getFiles();

        thumbnailsAdapter = new ThumbnailsAdapter(files);
        holder.rvThumbnails.setAdapter(thumbnailsAdapter);
        holder.rvThumbnails.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public ArrayList<File> getFiles(int pos) {
        return threads.get(pos).getPosts().get(0).getFiles();
    }

    public void onCommentClick(View v, int pos) {

    }

    @Override
    public int getItemCount() {
        return threads.size();
    }


    public class VHThread extends VHImages {

        @BindView(R.id.tvDateThread)
        TextView tvDateThread;
        @BindView(R.id.tvThreadNumer)
        TextView tvThreadNumber;
        @BindView(R.id.tvCommentThread)
        TextView tvCommentThread;
        @BindView(R.id.tvThreadCounter)
        TextView tvThreadCounter;
        @BindView(R.id.rvThumbnails)
        RecyclerView rvThumbnails;


        public VHThread(View v) {
            super(v);
        }
    }


}
