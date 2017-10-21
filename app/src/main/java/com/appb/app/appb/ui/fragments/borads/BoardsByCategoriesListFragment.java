package com.appb.app.appb.ui.fragments.borads;

import android.content.Intent;
import android.os.Bundle;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.ui.activities.ThreadsListActivity;

import java.util.ArrayList;

/**
 * Created by seishu on 17.10.2017.
 */

public class BoardsByCategoriesListFragment extends BaseBoardListFragment<Board> {



    private static final String CATEGORY_NAME = "categoryName";
    private String categoryName;

    public static BoardsByCategoriesListFragment create(String CategoryName) {
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, CategoryName);
        BoardsByCategoriesListFragment fragment = new BoardsByCategoriesListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public ArrayList<Board> getArray() {
        categoryName = getArguments().getString(CATEGORY_NAME);
        return Data.getInstance().getBoardsByCategory(categoryName);
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
}
