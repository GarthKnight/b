package com.appb.app.appb.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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
import com.appb.app.appb.fragments.ThreadListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 17.03.2017.
 */

public class BoardListActivity extends BaseActivity {


    @BindView(R.id.rvBoards)
    RecyclerView rvBoard;
    BoardListAdapter boardListAdapter;
    ArrayList<Board> boards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_boards);
        bindUI(this);
    }

    @Override
    public void init() {
        log("BoardListFragment: " + "init");
        rvBoard.setLayoutManager(new LinearLayoutManager(this));
        API.getInstance().getLists(new Callback<Boards>() {
            @Override
            public void onResponse(Call<Boards> call, Response<Boards> response) {
                log("BoardListFragment: " + "onResponse");
                initAdapter(response.body().getDifferent());
            }

            @Override
            public void onFailure(Call<Boards> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: " + t.toString());
//                showError(t.getMessage());
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    private void initAdapter(ArrayList<Board> different) {
        boardListAdapter = new BoardListAdapter(different, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new ThreadListFragment(), true);
            }
        });
        rvBoard.setAdapter(boardListAdapter);
    }

}
