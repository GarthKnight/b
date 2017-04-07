package com.appb.app.appb.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */

public class BoardPage {

    @SerializedName("Board")
    String board;
    @SerializedName("BoardInfo")
    String boardInfo;
    @SerializedName("BoardName")
    String boardName;
    @SerializedName("board_speed")
    int boardSpeed;
    @SerializedName("current_page")
    int currentPage;
    @SerializedName("bump_limit")
    int bumpLimit;
    ArrayList<Thread> threads;

    public ArrayList<Thread> getThreads() {
        return threads;
    }
}
