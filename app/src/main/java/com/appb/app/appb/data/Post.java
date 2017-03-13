package com.appb.app.appb.data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 10.03.2017.
 */

public class Post {

    int banned;
    int closed;
    String comment;
    String date;
    String email;
    ArrayList<File> files;
    int files_count;
    long lastshit;
    int op;
    String parent;
    int post_count;
    int sticky;
    String subject;
    String tags;
    long timestamp;
    String trip;
    int num;

    public String getName(){
        return subject;
    }

    public String getComment(){
        return comment;
    }

    public String getDate(){
        return date;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<File> getFiles() {
        return files;
    }
}
