package com.appb.app.appb.ui.adapters;

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

    private ArrayList<Board> boards;


    public BoardListAdapter(ArrayList<Board> boards){
        this.boards = boards;
    }

    @Override
    public BoardListAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tvBoardName.setText(boards.get(position).getName());
        holder.tvBoardName.setOnClickListener(v -> onBoardClick(boards.get(position).getId()));
    }

    public void onBoardClick(String board){}

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
