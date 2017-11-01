package com.appb.app.appb.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.appb.app.appb.ui.fragments.PostListFragment;
import com.appb.app.appb.utils.PrefUtils;
import com.appb.app.appb.R;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.mvp.presenters.ThreadListPresenter;
import com.appb.app.appb.mvp.views.ThreadListView;
import com.appb.app.appb.ui.adapters.ThreadListAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.OnClick;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by seishu on 11.10.2017.
 */

public class ThreadsListActivity extends BaseActivity implements ThreadListView {

    private static final String THREADS = "threads";
    private static final int FIRST = 0;
    private static final int THREAD_MAX_COUNT = 22;
    private static final String BOARD_ID = "boardId";
    private static final String TAG = "ThreadsListActivity";

    private int currentPage = 1;
    private String boardId = "b";
    private boolean mIsLoadingData = false;
    private boolean hasNextPage;

    private LinearLayoutManager llm;
    private ThreadListAdapter threadListAdapter;
    private ArrayList<Thread> threads = new ArrayList<>();

    @BindView(R.id.rvThreads)
    RecyclerView rvThreads;
    @BindView(R.id.progressBarLoading)
    ProgressBar progressBarLoading;
    @BindView(R.id.btnStar)
    ToggleButton btnStar;

    @InjectPresenter
    ThreadListPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_list);
        bindUI(this);
    }

    @Override
    public void init() {
        super.init();
        HashSet<String> myBoardsIds = PrefUtils.getMyBoards();
        boardId = getIntent().getExtras().getString(BOARD_ID);
        btnStar.setChecked(myBoardsIds.contains(boardId));
        initAdapter();
        initRV();

        if (threads.size() == 0) {
            loadThreadsRX();
        }
    }

    public void initRV() {
        rvThreads.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
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
                showFragment(PostListFragment.create(getFirstPostForThread(pos).getNum(), boardId), true);
            }
        };
        rvThreads.setAdapter(threadListAdapter);
    }

    private void openPicViewerActivity(ArrayList<File> files, int imageIndex) {
        Intent intent = new Intent(this, PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, imageIndex);
        startActivity(intent);
    }

    private Post getFirstPostForThread(int position) {
        return threads.get(position).getPosts().get(FIRST);
    }

    public void loadThreadsRX() {
        mIsLoadingData = true;
        presenter.getThreads(currentPage, boardId);
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


    @OnClick(R.id.btnStar)
    public void onStarClick(View v) {
        HashSet<String> set = PrefUtils.getMyBoards();
        if (((ToggleButton) v).isChecked()) {
            set.add(boardId);
            PrefUtils.setMyBoards(set);
        } else {
            set.remove(boardId);
            PrefUtils.setMyBoards(set);
        }
        Data.getInstance().syncFavouritesWithPreference();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(THREADS, threads);
    }


    @Override
    public void onThreadsLoaded(ArrayList<Thread> justLoadedThreads) {
        mIsLoadingData = false;

        if (threads.size() > 0) {
            ArrayList<Thread> newThreads = new ArrayList<>();

            for (Thread justLoadedThread : justLoadedThreads) {
                boolean isContains = false;
                for (Thread thread : threads) {
                    if (justLoadedThread.getThreadNum().equals(thread.getThreadNum())) {
                        isContains = true;
                        break;
                    }
                }

                if (!isContains) {
                    newThreads.add(justLoadedThread);
                }
            }
            threads.addAll(newThreads);
        } else {
            threads.addAll(justLoadedThreads);
        }

        threadListAdapter.notifyDataSetChanged();
        currentPage++;
        if (justLoadedThreads.size() == THREAD_MAX_COUNT) {
            hasNextPage = true;
        }
    }

    @Override
    public void onError(String error) {
        mIsLoadingData = false;
        Log.d(TAG, "onError: " + error);
    }

    @Override
    public void onLoadingStart() {

    }

    @Override
    public void onLoadingEnd() {
        progressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void setProgressBarLoading() {

    }


}
