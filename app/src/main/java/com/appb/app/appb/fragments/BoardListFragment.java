package com.appb.app.appb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.adapters.BoardListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Boards;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 06.03.2017.
 */

public class BoardListFragment extends BaseFragment {

    @BindView(R.id.rvBoards)
    RecyclerView rvBoard;
    BoardListAdapter boardListAdapter;
    ArrayList<Board> boards;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_boards, container, false);
        bindUI(v);
        return v;
    }


    @Override
    public void init() {
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        API.getInstance().getLists(new Callback<Boards>() {
            @Override
            public void onResponse(Call<Boards> call, Response<Boards> response) {
                initAdapter(response.body().getDifferent());
            }

            @Override
            public void onFailure(Call<Boards> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: " + t.toString());
                showError(t.getMessage());
            }
        });
    }

    private void initAdapter(ArrayList<Board> different) {
        boardListAdapter = new BoardListAdapter(different, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new ThreadListFragment(), true);
            }
        });
        rvBoard.setAdapter(boardListAdapter);
    }

    public void addFragment(Fragment fragment, boolean addToBack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.container, fragment);
        if (addToBack) transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
