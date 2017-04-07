package com.appb.app.appb.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 06.03.2017.
 */

public class Boards {

    private static final String B = "b";

    @SerializedName("Разное")
    private ArrayList<Board> different;

    public ArrayList<Board> getDifferent() {
        return different;
    }

    public ArrayList<Board> getBordsByIndex(){
        ArrayList<Board> newBoards = new ArrayList<>();
        for (int i = 0; i < different.size(); i++){
            if (different.get(i).id.toLowerCase().contains(B) ){
                newBoards.add(different.get(i));
            }
        }
        return newBoards;
    }

}
