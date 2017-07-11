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



}
