package com.appb.app.appb.ui.fragments;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Data;

import java.util.ArrayList;

/**
 * Created by seishu on 17.10.2017.
 */

public class MyFavoritesBoardsListFragment extends BaseBoardListFragment{

    private static final String TAG = "MyFavoritesBoardsListFr";

    @Override
    public ArrayList<Board> getBoards() {
        return Data.getInstance().getMyBoards();
    }

    public void notifyDataSetChanged(){
        log(TAG + " notifyDataSetChanged");
        rvAdapter.notifyDataSetChanged();
    }
}
