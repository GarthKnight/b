package com.appb.app.appb.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */

public class Threads {

    @SerializedName("threads")
    private ArrayList<Thread> threads;

    public ArrayList<Thread> getThreads() {
        return threads;
    }

}
