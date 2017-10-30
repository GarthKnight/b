package com.appb.app.appb.mvp.views;

import com.appb.app.appb.data.Post;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 1 on 22.04.2017.
 */

public interface PostListView extends MvpView {

    void onPostsLoaded(ArrayList<Post> posts);

    void onError(String error);

    void onLoadingStart();

    void onLoadingEnd();

}