package com.appb.app.appb.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.presenters.ThreadPresenter;
import com.appb.app.appb.mvp.views.ThreadView;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.PostsAdapter;
import com.appb.app.appb.ui.dialogs.AnswerDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListFragments extends BaseFragment implements ThreadView{

    private static final String POSTS = "posts";
    private static final String THREAD_NUMBER = "num";
    private static final int FIRST = 1;

    @InjectPresenter
    ThreadPresenter presenter;

    @BindView(R.id.rvPosts) RecyclerView rvPosts;

    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            posts = savedInstanceState.getParcelableArrayList(POSTS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();
        if (posts.size() == 0) {
            loadPosts();
        }

    }

    private void loadPostsRX(){

    }

    private void loadPosts() {
        int threadNumber = getArguments().getInt(THREAD_NUMBER);
        presenter.getPosts(threadNumber);
    }


    public void initAdapter() {
        postsAdapter = new PostsAdapter(posts, getContext()) {
            @Override
            public void onImageClick(View v, int position, int pos) {
                startPicViewerActivity(posts.get(position).getFiles(), pos);
            }

            @Override
            public void onAnswerClick(ArrayList<Post> postsAnswer, int index) {
                AnswerDialog answerDialog = new AnswerDialog((getContext()), postsAnswer, index) {
                    @Override
                    public void onItemClick(View v, int position, int pos) {
                        startPicViewerActivity(posts.get(position).getFiles(), pos);
                    }
                };
                answerDialog.show();
            }
        };
        rvPosts.setAdapter(postsAdapter);
    }

    private void startPicViewerActivity(ArrayList<File> files, int pos) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, pos);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(POSTS, posts);
    }

    public static PostListFragments newInstance(int num) {
        Bundle args = new Bundle();
        args.putInt(THREAD_NUMBER, num);
        PostListFragments fragment = new PostListFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void openAnswerDialog(int postNumber) {
        presenter.getAnswers();
    }

    @Override
    public void onPostsLoaded(ArrayList<Post> _posts) {
        posts.clear();
        posts.addAll(_posts);
        postsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        //// TODO: 28.05.17
    }

    @Override
    public void onLoadingEnd() {
        //progressBar.setVisible(false);
    }

    @Override
    public void onLoadingStart() {

    }
}

