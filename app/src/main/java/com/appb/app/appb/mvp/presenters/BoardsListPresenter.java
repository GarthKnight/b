package com.appb.app.appb.mvp.presenters;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.arellomobile.mvp.MvpPresenter;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Logvinov.sv on 13.06.2017.
 */

public class BoardsListPresenter extends MvpPresenter<BoardlistView> {

    public void getBoards(){
        API.getInstance()
                .getBoardsRX()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boards>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boards boards) {
                        getViewState().onBoardsLoaded(boards.getDifferent());
                    }
                });
    }
}
