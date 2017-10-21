package com.appb.app.appb.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Category;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.ui.adapters.ListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by seishu on 15.07.17.
 */

public class CategoriesListFragment extends BaseFragment {

    @BindView(R.id.rvList)
    RecyclerView rvList;
    ListAdapter rvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        initRV();
    }

    private void initRV() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdapter = new ListAdapter<Category>(getCategories()) {
            @Override
            public void onItemClick(int pos) {
                super.onItemClick(pos);
                showFragment(BoardsByCategoriesListFragment.create(getCategories().get(pos).getName()), true);
            }
        };
        rvList.setAdapter(rvAdapter);
    }

    public void notifyDataSetChanged(){
        rvAdapter.notifyDataSetChanged();
    }

    private ArrayList<Category> getCategories() {
        return Data.getInstance().getCategories();
    }
}
