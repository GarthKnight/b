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
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.mvp.presenters.ThreadListPresenter;
import com.appb.app.appb.mvp.views.ThreadListView;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.ThreadListAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 10.03.2017.
 */

public class ThreadListFragment extends BaseFragment implements ThreadListView {

    private static final String THREADS = "threads";
    private static final int FIRST = 0;
    private static final int THREAD_MAX_COUNT = 22;
    private static final String BOARD = "board";

    private int currentPage = 1;
    private String board = "b";
    private boolean mIsLoadingData = false;
    private boolean hasNextPage;

    private LinearLayoutManager llm;
    private ThreadListAdapter threadListAdapter;
    private ArrayList<Thread> threads = new ArrayList<>();

    @BindView(R.id.rvThreads)
    RecyclerView rvThreads;
    @BindView(R.id.progressBarLoading)
    ProgressBar progressBarLoading;

    @InjectPresenter
    ThreadListPresenter presenter;


    public static ThreadListFragment create(String board) {
        Bundle args = new Bundle();
        args.putString(BOARD, board);
        ThreadListFragment fragment = new ThreadListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = getArguments().getString(BOARD);
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
            loadThreadsRX();
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
            public void onThumbnailClick(int position, ArrayList<File> files) {
                super.onThumbnailClick(position, files);
                openPicViewerActivity(files, position);
            }

            //может быть тебе пора?
            @Override
            public void onCommentClick(View v, int pos) {
                showFragment(PostListFragments.create(getFirstPostForThread(pos).getNum(), board), true);
            }
        };
        rvThreads.setAdapter(threadListAdapter);
    }

    private void openPicViewerActivity(ArrayList<File> files, int imageIndex) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, imageIndex);
        startActivity(intent);
    }

    private Post getFirstPostForThread(int position) {
        return threads.get(position).getPosts().get(FIRST);
    }

    public void loadThreadsRX(){
        mIsLoadingData = true;
        presenter.getThreads(currentPage, board);



    }

    private RecyclerView.OnScrollListener listScrollListener = new RecyclerView.OnScrollListener() {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        public void onScrolled(RecyclerView recyclerView, int scrollHorizontal, int scrollVertical) {
            if (scrollVertical > 0) {

                int visibleItemCount = llm.getChildCount();
                int totalItemCount = llm.getItemCount();
                int pastVisibleItems = llm.findFirstVisibleItemPosition();

                if (!mIsLoadingData) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && hasNextPage) {
                        mIsLoadingData = true;
                        hasNextPage = false;
                        loadThreadsRX();
                        progressBarLoading.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    };
    
    @Override
    public void onThreadsLoaded(ArrayList<Thread> _threads) {
        mIsLoadingData = false;
        threads.addAll(_threads);
        threadListAdapter.notifyDataSetChanged();
        currentPage++;
        if (threads.size() == THREAD_MAX_COUNT) {
            hasNextPage = true;
        }
    }

    @Override
    public void onError(String error) {
        mIsLoadingData = false;
    }

    @Override
    public void onLoadingStart() {

    }

    @Override
    public void onLoadingEnd() {

    }

    @Override
    public void setProgressBarLoading() {
        progressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(THREADS, threads);
    }
}
