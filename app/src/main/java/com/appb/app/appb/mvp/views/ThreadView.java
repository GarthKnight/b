package com.appb.app.appb.mvp.views;

import com.appb.app.appb.data.Post;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by 1 on 22.04.2017.
 */

public interface ThreadView extends MvpView {

    void openAnswerDialog(int postNumber);

    void onPostsLoaded(ArrayList<Post> posts);

    void onError(String error);

    void onLoadingStart();

    void onLoadingEnd();

}