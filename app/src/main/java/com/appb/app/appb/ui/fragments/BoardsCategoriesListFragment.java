package com.appb.app.appb.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.File;
import com.appb.app.appb.mvp.presenters.BoardsListPresenter;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.BoardListAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by seishu on 15.07.17.
 */

public class BoardsCategoriesListFragment extends BaseFragment implements BoardlistView {

    @BindView(R.id.rvBoards)
    RecyclerView rvBoard;

    BoardListAdapter boardListAdapter;
    ArrayList<Board> boards = new ArrayList<>();
    ArrayList<String> boardsNames = new ArrayList<>();

    @InjectPresenter
    BoardsListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        bindUI(v);
        return v;    }

    @Override
    public void init() {
        if (boards.size() == 0){
            loadBoardsRX();
        }
        getBoardsNames();
    }

    private void loadBoardsRX(){
        presenter.getBoards();
    }


    private void initRV(ArrayList<Board> different) {
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        boardListAdapter = new BoardListAdapter(different){
            @Override
            public void onBoardClick(String board) {
                super.onBoardClick(board);
                showFragment(ThreadListFragment.create(board), true);
            }
        };
        rvBoard.setAdapter(boardListAdapter);
    }

    public void getBoardsNames(){
        presenter.getBoardsNames();
    }

    @Override
    public void onDifferentBoardsLoaded(ArrayList<Board> _boards) {
        boards = _boards;
        initRV(boards);
    }

    @Override
    public void onBoardsLoaded(ArrayList<Board> boards) {

    }

    @Override
    public void getBoardsNames(ArrayList<String> _boardsNames) {
        boardsNames = _boardsNames;
    }

    @Override
    public void onError(String error) {
        log("onFailure: " + error);

    }

    public static void openImages(Context c, ArrayList<File> files, int pos) {
        Intent intent = new Intent(c, PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, pos);
        c.startActivity(intent);
    }
}
