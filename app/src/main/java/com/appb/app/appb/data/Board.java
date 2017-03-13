package com.appb.app.appb.data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 06.03.2017.
 */

public class Board {
    int bump_limit;
    String category;
    String default_name;
    String id;
    String name;
    int pages;
    int sage;
    int tripcodes;
    int enable_likes;
    int enable_posting;
    int enable_thread_tags;
    ArrayList<Objects> icons;

    public String getName() {
        return name;
    }
}
