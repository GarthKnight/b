package com.appb.app.appb.mvp.views;

import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by Logvinov.sv on 09.06.2017.
 */

public interface ThreadListView extends MvpView {

    void onThreadsLoaded(ArrayList<Thread> threads);

    void  onError(String error);

    void onLoadingStart();

    void onLoadingEnd();

    void setProgressBarLoading();
}
