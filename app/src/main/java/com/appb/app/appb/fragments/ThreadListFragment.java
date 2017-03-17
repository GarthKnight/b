package com.appb.app.appb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appb.app.appb.R;
import com.appb.app.appb.activities.MyPlayerActivity;
import com.appb.app.appb.activities.PicViewerActivity;
import com.appb.app.appb.adapters.PicViewerAdapter;
import com.appb.app.appb.adapters.ThreadListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Thread;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appb.app.appb.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.activities.PicViewerActivity.POS;

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
        API.getInstance().getThreads(new Callback<BoardPage>() {
            @Override
            public void onResponse(Call<BoardPage> call, Response<BoardPage> response) {
                ThreadListFragment.this.threads = response.body().getThreads();
                threadListAdapter = new ThreadListAdapter(threads) {
                    @Override
                    public void onItemClick(View v, int position, int pos) {
                        Intent intent = new Intent(getContext(), PicViewerActivity.class);
                        intent.putExtra(FILES, threads.get(position).getPosts().get(0).getFiles());
                        intent.putExtra(POS, pos);
                        startActivity(intent);
                    }
                };
                if (rvThreads != null) rvThreads.setAdapter(threadListAdapter);
            }

            @Override
            public void onFailure(Call<BoardPage> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    @OnClick(R.id.btnWebm)
    public void Onclick(){
        Intent intent = new Intent(getContext(), MyPlayerActivity.class);
        startActivity(intent);
    }

}
