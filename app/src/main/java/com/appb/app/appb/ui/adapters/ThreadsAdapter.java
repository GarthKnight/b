package com.appb.app.appb.ui.adapters;

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

public class ThreadsAdapter extends BaseRVAdapterWithImages<ThreadsAdapter.VHThread> {

    private static final int THREAD_TEXT_SIZE = 240;
    private ArrayList<Thread> threads;

    protected ThreadsAdapter(ArrayList<Thread> threads) {
        this.threads = threads;
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
        holder.tvCommentThread.setText(formatStringForThread(comment.toString()));
        holder.tvThreadCounter.setText("#" + position);
    }

    @Override
    public ArrayList<File> getFiles(int pos) {
        return threads.get(pos).getPosts().get(0).getFiles();
    }

    private String formatStringForThread(String text){
        if (text.length() < THREAD_TEXT_SIZE){
            return text;
        } else {
            return text.substring(0, THREAD_TEXT_SIZE) + "...";
        }
    }

    public void onCommentClick(View v, int pos) {}

    @Override
    public int getItemCount() {
        return threads.size();
    }


    class VHThread extends VHImages {

        @BindView(R.id.tvDateThread)
        TextView tvDateThread;
        @BindView(R.id.tvThreadNumer)
        TextView tvThreadNumber;
        @BindView(R.id.tvCommentThread)
        TextView tvCommentThread;
        @BindView(R.id.tvThreadCounter)
        TextView tvThreadCounter;

        VHThread(View v) {
            super(v);
        }
    }


}
