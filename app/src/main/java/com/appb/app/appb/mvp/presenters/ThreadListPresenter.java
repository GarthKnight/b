package com.appb.app.appb.mvp.presenters;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.mvp.views.ThreadListView;
import com.arellomobile.mvp.MvpPresenter;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Logvinov.sv on 09.06.2017.
 */

public class ThreadListPresenter extends MvpPresenter<ThreadListView> {

    private int currentPage = 1;
    private boolean isLoadingData;


    public void getThreads(int threadNumber) {

        isLoadingData = true;

        API.getInstance().getThreadsRX(currentPage)
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
                        isLoadingData = false;
                        getViewState().onError(e.getMessage(), isLoadingData);
                    }

                    @Override
                    public void onNext(BoardPage boardPage) {
                        currentPage++;
                        isLoadingData = false;
                        getViewState().setProgressBarLoading();
                        getViewState().onThreadsLoaded(boardPage.getThreads(), isLoadingData);
                    }
                });
    }

}
