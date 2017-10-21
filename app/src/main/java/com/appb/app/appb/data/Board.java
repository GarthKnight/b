package com.appb.app.appb.data;

import com.appb.app.appb.ui.adapters.ListAdapterItem;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 06.03.2017.
 */
@Parcel
public class Board implements ListAdapterItem {

    @SerializedName("bump_limit")
    int bumpLimit;
    @SerializedName("category")
    String categoryName;
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

    public Board() {
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getCategoryName() {
        return categoryName;
    }


    @Override
    public String getItemName() {
        return getName();
    }
}
