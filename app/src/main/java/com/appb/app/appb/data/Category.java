package com.appb.app.appb.data;

import com.appb.app.appb.ui.adapters.ListAdapterItem;

import java.util.ArrayList;

/**
 * Created by seishu on 08.10.2017.
 */

public class Category implements ListAdapterItem {

    private String name;
    private ArrayList<Board> boards;

    public Category(String name, ArrayList<Board> boards) {
        this.name = name;
        this.boards = boards;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    @Override
    public String getItemName() {
        return getName();
    }
}
