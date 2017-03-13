package com.appb.app.appb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.adapters.ThreadListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.data.Threads;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 10.03.2017.
 */

public class ThreadListFragment extends BaseFragment {

    @BindView(R.id.rvThreads)
    RecyclerView rvThreads;

    ThreadListAdapter threadListAdapter;
    ArrayList<Thread> threads;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thread_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        rvThreads.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        API.getInstance().getThreads(new Callback<BoardPage>() {
            @Override
            public void onResponse(Call<BoardPage> call, Response<BoardPage> response) {
                threadListAdapter = new ThreadListAdapter(response.body().getThreads());
                rvThreads.setAdapter(threadListAdapter);
            }

            @Override
            public void onFailure(Call<BoardPage> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }
}
