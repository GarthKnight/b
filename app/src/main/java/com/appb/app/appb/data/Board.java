package com.appb.app.appb.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 06.03.2017.
 */

public class Board {
    @SerializedName("bump_limit")
    int bumpLimit;
    String category;
    @SerializedName("default_name")
    String defaultName;
    String id;
    String name;
    int pages;
    int sage;
    int tripcodes;
    @SerializedName("enable_likes")
    int enableLikes;
    @SerializedName("enable_posting")
    int enablePosting;
    @SerializedName("enable_thread_tags")
    int enableThreadTags;
    ArrayList<Objects> icons;

    public String getName() {
        return name;
    }
}
