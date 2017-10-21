package com.appb.app.appb.ui.fragments;

import android.os.Bundle;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Data;

import java.util.ArrayList;

/**
 * Created by seishu on 17.10.2017.
 */

public class BoardsByCategoriesListFragment extends BaseBoardListFragment{

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
    public ArrayList<Board> getBoards() {
        categoryName = getArguments().getString(CATEGORY_NAME);
        return Data.getInstance().getBoardsByCategory(categoryName);
    }

}
