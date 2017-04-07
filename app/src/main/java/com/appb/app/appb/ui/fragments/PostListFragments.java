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
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.PostsAdapter;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.dialogs.AnswerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListFragments extends BaseFragment {

    private static final String POSTS = "posts";
    private static final String NUM = "num";
    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;

    PostsAdapter postsAdapter;
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
        if (posts.size() == 0) {
            String task = "get_thread";
            String board = "b";
            int tmp = getArguments().getInt(NUM);
            String num = String.valueOf(tmp);
            int postTmp = 1;
            String postNum = String.valueOf(postTmp);
            API.getInstance().getPosts(new Callback<ArrayList<Post>>() {
                @Override
                public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                    posts.addAll(response.body());
                    postsAdapter.notifyDataSetChanged();

                    if (!response.isSuccessful()) {
                        String task = "get_thread";
                        String board = "b";
                        int tmp = getArguments().getInt(NUM);
                        String num = String.valueOf(tmp);
                        int postTmp = 102;
                        String postNum = String.valueOf(postTmp);
                        API.getInstance().getPosts(new Callback<ArrayList<Post>>() {

                            @Override
                            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                                posts.addAll(response.body());
                                postsAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                                showError(t.getMessage());
                            }
                        }, task, board, num, postNum);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                    showError(t.getMessage());
                }
            }, task, board, num, postNum);

        }

    }


    public void initAdapter() {
        postsAdapter = new PostsAdapter(posts) {
            @Override
            public void onItemClick(View v, int position, int pos) {
                Intent intent = new Intent(getContext(), PicViewerActivity.class);
                intent.putExtra(FILES, posts.get(position).getFiles());
                intent.putExtra(POS, pos);
                startActivity(intent);
            }

            @Override
            public void onPrefClick(ArrayList<Post> postsAnswer, int index) {
                AnswerDialog answerDialog = new AnswerDialog((getContext()), postsAnswer, index) {
                    @Override
                    public void onItemClick(View v, int position, int pos) {
                        Intent intent = new Intent(getContext(), PicViewerActivity.class);
                        intent.putExtra(FILES, posts.get(position).getFiles());
                        intent.putExtra(POS, pos);
                        startActivity(intent);
                    }
                };
                answerDialog.show();
            }
        };

        rvPosts.setAdapter(postsAdapter);
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

