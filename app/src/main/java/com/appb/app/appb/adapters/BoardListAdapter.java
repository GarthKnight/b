package com.appb.app.appb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Board;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1 on 06.03.2017.
 */

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.VH> {

    ArrayList<Board> boards;

    private final View.OnClickListener threadOnClickListener;


    public BoardListAdapter(ArrayList<Board> boards, View.OnClickListener threadOnClickListener){
        this.boards = boards;
        this.threadOnClickListener = threadOnClickListener;
    }

    @Override
    public BoardListAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        v.setOnClickListener(threadOnClickListener);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tvBoardName.setText(boards.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tvBoardName) TextView tvBoardName;

        public VH(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
