package com.appb.app.appb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.activities.PicViewerActivity;
import com.appb.app.appb.adapters.PostListAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Posts;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appb.app.appb.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListFragments extends BaseFragment {

    private static final String POSTS = "posts";
    private static final String NUM = "num";
    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;

    PostListAdapter postListAdapter;
    ArrayList<Post> posts = new ArrayList<>();

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
        if(posts.size() == 0){
            int threadNum = getArguments().getInt(NUM);
            API.getInstance().getPosts(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    posts.addAll(response.body().getPosts());
                    postListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    showError(t.getMessage());
                }
            }, threadNum);
        }

    }

    public void initAdapter() {
        postListAdapter = new PostListAdapter(posts);
        rvPosts.setAdapter(postListAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(POSTS, posts);
    }

    public static PostListFragments newInstance(int num) {
        Bundle args = new Bundle();
        args.putInt(NUM, num);
        PostListFragments fragment = new PostListFragments();
        fragment.setArguments(args);
        return fragment;
    }
}

