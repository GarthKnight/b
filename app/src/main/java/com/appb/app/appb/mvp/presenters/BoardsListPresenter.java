package com.appb.app.appb.mvp.presenters;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Logvinov.sv on 13.06.2017.
 */

@InjectViewState
public class BoardsListPresenter extends MvpPresenter<BoardlistView> {

    private ArrayList<Board> differentBoards = new ArrayList<>();
    private ArrayList<Board> boards = new ArrayList<>();

    public void getBoards(){

        API.getInstance()
                .getBoards()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Board>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Board> _boards) {
                        boards = _boards;
                        getViewState().onBoardsLoaded(boards);
                    }
                });

        API.getInstance()
                .getDifferentBoardsRX()
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
                    public void onNext(Boards _boards) {
                        differentBoards = _boards.getDifferent();
                        getViewState().onDifferentBoardsLoaded(differentBoards);
                    }
                });
    }

    public void getBoardsNames(){
        ArrayList<String> boardsNames = new ArrayList<>();
        for (Board board : differentBoards){
            boardsNames.add(board.getId());
        }
        getViewState().getBoardsNames(boardsNames);
    }
}
