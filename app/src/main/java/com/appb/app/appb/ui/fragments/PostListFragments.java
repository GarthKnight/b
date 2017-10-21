package com.appb.app.appb.ui.fragments;

import android.app.ProgressDialog;
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
import com.appb.app.appb.mvp.presenters.PostListPresenter;
import com.appb.app.appb.mvp.views.PostListView;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.PostsAdapter;
import com.appb.app.appb.ui.dialogs.AnswerDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListFragments extends BaseFragment implements PostListView {

    private static final String POSTS = "posts";
    private static final String THREAD_NUMBER = "num";
    private static final String BOARD = "board";

    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    public HashMap<Integer, ArrayList<Integer>> answers;

    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;
    @BindView(R.id.pbPosts)
    ProgressBar pbPosts;

    @InjectPresenter
    PostListPresenter presenter;


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

        getAnswers();

    }


    private void loadPosts() {
        int threadNumber = getArguments().getInt(THREAD_NUMBER);
        String board = getArguments().getString(BOARD);
        presenter.getPosts(threadNumber, board);
    }

    private void getAnswers() {
        presenter.getAnswers();
    }

    public void initAdapter() {
        postsAdapter = new PostsAdapter(posts, answers) {

            @Override
            public void onThumbnailClick(int position, ArrayList<File> files) {
                super.onThumbnailClick(position, files);
                startPicViewerActivity(files, position);
            }

            @Override
            public void onAnswerClick(ArrayList<Post> postsAnswer, int index) {
                AnswerDialog answerDialog = new AnswerDialog((getContext()), postsAnswer, index);
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

    public static PostListFragments create(int num, String board) {
        Bundle args = new Bundle();
        args.putInt(THREAD_NUMBER, num);
        args.putString(BOARD, board);
        PostListFragments fragment = new PostListFragments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getAnswers(HashMap<Integer, ArrayList<Integer>> _answers) {
        answers = _answers;
    }

    @Override
    public void onPostsLoaded(ArrayList<Post> _posts) {
        posts.clear();
        posts.addAll(_posts);
        postsAdapter.notifyDataSetChanged();
        pbPosts.setVisibility(View.GONE);
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

