package com.appb.app.appb.data;

import com.appb.app.appb.PrefUtils;
import com.appb.app.appb.ui.fragments.BaseBoardListFragment;

import java.util.ArrayList;
import java.util.HashSet;

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
        if (this.boards == null) {
            this.boards = new ArrayList<>();
        }
        this.boards.clear();
        this.boards.addAll(boards);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public ArrayList<Board> getBoardsByCategory(String name){
        ArrayList<Board> boards = new ArrayList<>();
        for (Category category : categories){
            if (category.getName().equals(name)){
                boards = category.getBoards();
            }
        }
        return boards;
    }

    public ArrayList<Board> getMyBoards(){

        ArrayList<Board> myBoards = new ArrayList<>();
        HashSet<String> boardIds = PrefUtils.getMyBoards();

        for (String id : boardIds) {
            for (Board board : boards) {
                if (board.getId().equals(id)) {
                    myBoards.add(board);
                }
            }
        }

        return myBoards;
    }

}
