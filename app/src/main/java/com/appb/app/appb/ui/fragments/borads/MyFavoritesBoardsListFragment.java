package com.appb.app.appb.ui.fragments.borads;

import android.content.Intent;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.ui.activities.ThreadsListActivity;

import java.util.ArrayList;

public class MyFavoritesBoardsListFragment extends BaseBoardListFragment {

    private static final String TAG = "MyFavoritesBoardsListFr";

    @Override
    public ArrayList<Board> getArray() {
        return Data.get().getMyBoards();
    }

    @Override
    public void onItemClick(int pos) {
        openThreadsListActivity(getArray().get(pos).getId());
    }

    private void openThreadsListActivity(String boardId) {
        Intent intent = new Intent(getContext(), ThreadsListActivity.class);
        intent.putExtra(EXTRAS_BOARD_ID, boardId);
        startActivity(intent);
    }

    public void notifyDataSetChanged(){
        log(TAG + " notifyDataSetChanged");
        rvAdapter.notifyDataSetChanged();
    }
}