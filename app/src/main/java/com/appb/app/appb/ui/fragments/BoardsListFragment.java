package com.appb.app.appb.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.ui.adapters.ListAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by seishu on 15.07.17.
 */

public class BoardsListFragment extends BaseFragment {

    private static final String BOARDS = "boards";
    @BindView(R.id.rvList)
    RecyclerView rvBoard;

    ListAdapter boardListAdapter;
    ArrayList<Board> boards = new ArrayList<>();

    public static BoardsListFragment create(ArrayList<Board> boards) {

        Bundle args = new Bundle();
        args.putParcelable(BOARDS, Parcels.wrap(boards));
        BoardsListFragment fragment = new BoardsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boards = Parcels.unwrap(getArguments().getParcelable(BOARDS));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        bindUI(v);
        return v;    }

    @Override
    public void init() {
        if (boards.size() == 0){

        }
        initRV();

    }

    private void initRV() {
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        boardListAdapter = new ListAdapter<Board>(boards){
            @Override
            public void onItemClick(int pos) {
                super.onItemClick(pos);
                showFragment(ThreadListFragment.create(boards.get(pos).getId()), true);
            }
        };
        rvBoard.setAdapter(boardListAdapter);
    }


}
