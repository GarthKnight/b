package com.appb.app.appb.mvp.presenters;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Thread;
import com.appb.app.appb.mvp.views.ThreadListView;
import com.appb.app.appb.ui.adapters.ThumbnailsAdapter;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Logvinov.sv on 09.06.2017.
 */

@InjectViewState
public class ThreadListPresenter extends MvpPresenter<ThreadListView> {



    public void getThreads(int currentPage, String board) {


        API.getInstance().getThreadsRX(currentPage, board)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BoardPage>() {
                    @Override
                    public void onCompleted() {
                        getViewState().onLoadingStart();
                        getViewState().onLoadingEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().onError(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BoardPage boardPage) {
                        getViewState().setProgressBarLoading();
                        getViewState().onThreadsLoaded(boardPage.getThreads());
                    }
                });
    }



}
