package com.appb.app.appb.ui.fragments;

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
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.ThreadListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 10.03.2017.
 */

public class ThreadListFragment extends BaseFragment {

    private static final String THREADS = "threads";
    private static final int FIRST = 0;
    private static final int THREAD_MAX_COUNT = 22;

    private int currentPage = 1;
    private boolean mIsLoadingData = false;
    private boolean hasNextPage;

    private LinearLayoutManager llm;
    private ThreadListAdapter threadListAdapter;
    private ArrayList<Thread> threads = new ArrayList<>();

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
        initRV();
        if (threads.size() == 0) {
            loadThreads();
        }
    }

    public void initRV() {
        rvThreads.setHasFixedSize(true);
        llm = new LinearLayoutManager(getContext());
        rvThreads.setLayoutManager(llm);
        rvThreads.addOnScrollListener(listScrollListener);
    }

    private void initAdapter() {
        threadListAdapter = new ThreadListAdapter(threads) {
            @Override
            public void onImageClick(View v, int position, int pos) {
                openPicViewerActivity(getFirstPostForThread(position).getFiles(), pos);
            }

            //может быть тебе пора?
            @Override
            public void onCommentClick(View v, int pos) {
                showFragment(PostListFragments.newInstance(getFirstPostForThread(pos).getNum()), true);
            }
        };
        rvThreads.setAdapter(threadListAdapter);
    }

    public void loadThreads() {
        mIsLoadingData = true;
        API.getInstance().getThreads(currentPage, new Callback<BoardPage>() {
            @Override
            public void onResponse(Call<BoardPage> call, Response<BoardPage> response) {
                mIsLoadingData = false;
                progressBarLoading.setVisibility(View.GONE);
                if (response.body().getThreads() != null) {
                    processThreadCallResponse(response.body().getThreads());
                }
            }

            @Override

            public void onFailure(Call<BoardPage> call, Throwable t) {
                showError(t.getMessage());
                mIsLoadingData = false;
            }
        });
    }

    private void processThreadCallResponse(ArrayList<Thread> threads) {
        this.threads.addAll(threads);
        log("On Response thread size: " + threads.size());
        threadListAdapter.notifyDataSetChanged();
        currentPage++;
        if (threads.size() == THREAD_MAX_COUNT) {
            hasNextPage = true;
        }
    }

    private void openPicViewerActivity(ArrayList<File> files, int imageIndex) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, imageIndex);
        startActivity(intent);
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

    private Post getFirstPostForThread(int position) {
        return threads.get(position).getPosts().get(FIRST);
    }
}
