package com.appb.app.appb.data;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */

public class BoardPage {


    String Board;
    String BoardInfo;
    String BoardName;
    int board_speed;
    int current_page;
    int bump_limit;
    ArrayList<Thread> threads;

    public ArrayList<Thread> getThreads() {
        return threads;
    }
}
