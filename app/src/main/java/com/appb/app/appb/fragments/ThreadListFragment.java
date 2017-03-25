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
import com.appb.app.appb.activities.PicViewerActivity;
import com.appb.app.appb.adapters.ThreadListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Posts;
import com.appb.app.appb.data.Thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    private static final String THREADS = "threads";
    private static final String NUM = "num";
    private int count = 1;
    private String index;
    ThreadListAdapter threadListAdapter;
    ArrayList<Thread> threads = new ArrayList<>();

    @BindView(R.id.rvThreads)
    RecyclerView rvThreads;
    @BindView(R.id.btnForward)
    Button btnForward;
    @BindView(R.id.btnBack)
    Button btnBack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            threads = savedInstanceState.getParcelableArrayList(THREADS);
        }
    }

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
        initAdapter();
        if (threads.size() == 0) {
            API.getInstance().getThreads(new Callback<BoardPage>() {
                @Override
                public void onResponse(Call<BoardPage> call, Response<BoardPage> response) {
                    if (response.body().getThreads() != null)
                        threads.addAll(response.body().getThreads());
                    threadListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<BoardPage> call, Throwable t) {
                    showError(t.getMessage());
                }
            });
        }


    }

    


    @OnClick(R.id.btnForward)
    public void onForwardClick(View v) {
        if (count != 20) {
            count = count++;
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClick(View v) {
        if (count != 1) {
            count = count--;
        }
    }

    private void initAdapter() {

        if (count == 1) {
            index = "index";
        } else {
            index = String.valueOf(count);
        }

        threadListAdapter = new ThreadListAdapter(threads) {
            @Override
            public void onImageClick(View v, int position, int pos) {
                Intent intent = new Intent(getContext(), PicViewerActivity.class);
                intent.putExtra(FILES, threads.get(position).getPosts().get(0).getFiles());
                intent.putExtra(POS, pos);
                startActivity(intent);
            }

            //может быть тебе пора?
            @Override
            public void onCommentClick(View v, int pos) {
                showFragment(PostListFragments.newInstance(threads.get(pos).getPosts().get(0).getNum()), true);
            }
        };
        rvThreads.setAdapter(threadListAdapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(THREADS, threads);
    }


}
