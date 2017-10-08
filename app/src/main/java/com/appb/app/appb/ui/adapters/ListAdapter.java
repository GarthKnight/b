package com.appb.app.appb.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appb.app.appb.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1 on 06.03.2017.
 */

public class ListAdapter<T extends ListAdapterItem> extends RecyclerView.Adapter<ListAdapter.VH> {

    private ArrayList<T> boards;


    protected ListAdapter(ArrayList<T> boards){
        this.boards = boards;
    }

    @Override
    public ListAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tvName.setText(boards.get(position).getItemName());
        holder.tvName.setOnClickListener(v -> onItemClick(position));
    }

    public void onItemClick(int position){}


    @Override
    public int getItemCount() {
        return boards.size();
    }

    class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName) TextView tvName;

        VH(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
