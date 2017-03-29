package com.appb.app.appb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appb.app.appb.R;
import com.appb.app.appb.activities.PicViewerActivity;
import com.appb.app.appb.adapters.ThreadListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Thread;

import java.util.ArrayList;

import butterknife.BindView;
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
    private static final String COUNT = "currentPage";
    private int currentPage = 1;
    private boolean mIsLoadingData = false;
    private boolean hasNextPage;

    LinearLayoutManager llm;

    ThreadListAdapter threadListAdapter;
    ArrayList<Thread> threads = new ArrayList<>();

    @BindView(R.id.rvThreads)
    RecyclerView rvThreads;
    @BindView(R.id.progressBarLoading)
    ProgressBar progressBarLoading;

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
        initAdapter();
        if (threads.size() == 0) {
            loadThreads();
        }
        initRV();
    }

    public void initRV() {
        rvThreads.setHasFixedSize(true);
        llm = new LinearLayoutManager(getContext());
        rvThreads.setLayoutManager(llm);
        rvThreads.addOnScrollListener(listScrollListener);

    }

    public void loadThreads() {

        mIsLoadingData = true;

        API.getInstance().getThreads(currentPage, new Callback<BoardPage>() {
            @Override
            public void onResponse(Call<BoardPage> call, Response<BoardPage> response) {
                if (response.body().getThreads() != null)
                    threads.addAll(response.body().getThreads());
                log("On Response thread size: " + response.body().getThreads().size());
                threadListAdapter.notifyDataSetChanged();
                currentPage++;
                mIsLoadingData = false;
                progressBarLoading.setVisibility(View.GONE);

                if (response.body().getThreads().size() == 22) {
                    hasNextPage = true;
                }
            }

            @Override

            public void onFailure(Call<BoardPage> call, Throwable t) {
                showError(t.getMessage());
                mIsLoadingData = false;
            }
        });

    }

    private void initAdapter() {

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


    private RecyclerView.OnScrollListener listScrollListener = new RecyclerView.OnScrollListener() {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int scrollHorizontal, int scrollVertical) {

            if (scrollVertical > 0) {
                int visibleItemCount = llm.getChildCount();
                int totalItemCount = llm.getItemCount();
                int pastVisibleItems = llm.findFirstVisibleItemPosition();
                log("pastVisibleItems : " + pastVisibleItems + ", visibleItemCount : " + visibleItemCount +
                        ", totalItemCount : " + totalItemCount);
                if (!mIsLoadingData) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && hasNextPage) {
                        mIsLoadingData = true;
                        hasNextPage = false;
                        loadThreads();
                        progressBarLoading.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    };
}
