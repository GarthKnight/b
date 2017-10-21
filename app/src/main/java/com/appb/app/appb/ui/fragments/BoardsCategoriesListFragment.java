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
import com.appb.app.appb.data.Category;
import com.appb.app.appb.data.Data;
import com.appb.app.appb.data.File;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.adapters.ListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by seishu on 15.07.17.
 */

public class BoardsCategoriesListFragment extends BaseFragment {

    @BindView(R.id.rvList)
    RecyclerView rvList;

    ListAdapter adapter;
    ArrayList<Category> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        categories = Data.getInstance().getCategories();
        initRV();
    }

    private void initRV() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter<Category>(categories) {
            @Override
            public void onItemClick(int pos) {
                super.onItemClick(pos);
                showFragment(BoardsListFragment.create(categories.get(pos).getBoards()), true);
            }
        };
        rvList.setAdapter(adapter);
    }
}
