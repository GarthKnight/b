package com.appb.app.appb.mvp.presenters;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.data.Category;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Objects;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Logvinov.sv on 13.06.2017.
 */

@InjectViewState
public class BoardsListPresenter extends MvpPresenter<BoardlistView> {

    private ArrayList<Category> categories = new ArrayList<>();

    public void getData() {

        API.getInstance()
                .getBoards()
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
                        Data.getInstance().setBoards(boards.getBoards());
                        Data.getInstance().setCategories(getCategories(boards.getBoards()));
                        getViewState().onDataLoaded();
                    }
                });
    }

    public ArrayList<Category> getCategories(ArrayList<Board> boards) {
        for (Board board : boards){
            addBoardsToCategory(board);
        }
//        boards.forEach(this::addBoardsToCategory);
        return categories;
    }

    private void addBoardsToCategory(Board board) {
        for (Category category : categories) {
            if (Objects.equals(category.getItemName(), board.getCategoryName())) {
                category.getBoards().add(board);
            }
        }
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(board);
        categories.add(new Category(board.getCategoryName(), boards));
    }
}
