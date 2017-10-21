package com.appb.app.appb.ui.fragments.borads;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.ui.adapters.ListAdapter;
import com.appb.app.appb.ui.adapters.ListAdapterItem;
import com.appb.app.appb.ui.fragments.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by seishu on 17.10.2017.
 */

public abstract class BaseBoardListFragment<T extends ListAdapterItem> extends BaseFragment {
    public static final String EXTRAS_BOARD_ID = "boardId";

    @BindView(R.id.rvList) RecyclerView rvBoard;

    ListAdapter<T> rvAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        rvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdapter = new ListAdapter<T>(getArray()) {
            @Override
            public void onItemClick(int pos) {
                super.onItemClick(pos);
                BaseBoardListFragment.this.onItemClick(pos);
            }
        };
        rvBoard.setAdapter(rvAdapter);
    }



    @Override
    public void onResume() {
        super.onResume();
        rvAdapter.notifyDataSetChanged();
    }

    public abstract ArrayList<T> getArray();

    public abstract void onItemClick(int pos);
}
