package com.appb.app.appb.data;

import java.util.ArrayList;

/**
 * Created by seishu on 08.10.2017.
 */

public class Data {
    private static final Data ourInstance = new Data();

    public static Data getInstance() {
        return ourInstance;
    }

    private Data() {
    }

     private ArrayList<Board> boards = new ArrayList<>();
     private ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }



}
