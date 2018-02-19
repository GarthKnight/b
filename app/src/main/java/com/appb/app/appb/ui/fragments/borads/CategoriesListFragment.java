package com.appb.app.appb.ui.fragments.borads;

import com.appb.app.appb.data.Category;
import com.appb.app.appb.data.Data;

import java.util.ArrayList;

/**
 * Created by seishu on 15.07.17.
 */

public class CategoriesListFragment extends BaseBoardListFragment<Category> {


    @Override
    public ArrayList<Category> getArray() {
        return Data.get().getCategories();
    }

    @Override
    public void onItemClick(int pos) {
        showFragment(BoardsByCategoriesListFragment.create(getArray().get(pos).getName()), true);
    }

    public void notifyDataSetChanged(){
        Data.get().getCategories();
        rvAdapter.notifyDataSetChanged();
    }

}
