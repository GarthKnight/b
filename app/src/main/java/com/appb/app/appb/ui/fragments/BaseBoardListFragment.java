package com.appb.app.appb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.ui.activities.ThreadsListActivity;
import com.appb.app.appb.ui.adapters.ListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by seishu on 17.10.2017.
 */

public abstract class BaseBoardListFragment extends BaseFragment {

    private static final String BOARD_ID = "boardId";

    @BindView(R.id.rvList)
    RecyclerView rvBoard;

    ListAdapter rvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        initRV();
    }

    private void initRV() {
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdapter = new ListAdapter<Board>(getBoards()) {
            @Override
            public void onItemClick(int pos) {
                super.onItemClick(pos);
                openThreadsListActivity(getBoards().get(pos).getId());
            }
        };
        rvBoard.setAdapter(rvAdapter);
    }

    private void openThreadsListActivity(String boardId) {
        Intent intent = new Intent(getContext(), ThreadsListActivity.class);
        intent.putExtra(BOARD_ID, boardId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        rvAdapter.notifyDataSetChanged();
    }

    public abstract ArrayList<Board> getBoards();
}
