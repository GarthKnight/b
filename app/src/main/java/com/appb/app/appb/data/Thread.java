package com.appb.app.appb.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 10.03.2017.
 */

public class Thread {

    int files_count;
    int post_count;
    String thread_num;
    ArrayList<Post> posts;

    public ArrayList<Post> getPosts(){
        return posts;
    }

}
