package com.appb.app.appb.data;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */
@Parcel
public class Post {

    protected int banned;
    protected int closed;
    protected String comment;
    protected String date;
    protected String email;
    protected ArrayList<DvachMediaFile> dvachMediaFiles;
    protected int files_count;
    protected long lastshit;
    protected int op;
    protected String parent;
    protected int post_count;
    protected int sticky;
    protected String subject;
    protected String tags;
    protected long timestamp;
    protected String trip;
    protected int num;
    protected ArrayList<Post> answers = new ArrayList<>();
    protected ArrayList<Integer> postNumbersFromComments;


    public String getName() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getNum() {
        return num;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<DvachMediaFile> getDvachMediaFiles() {
        return dvachMediaFiles;
    }

    public ArrayList<Post> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Post> answers) {
        this.answers = answers;
    }


    public ArrayList<Integer> getPostNumbersFromComments() {
        return postNumbersFromComments;
    }

    public void setPostNumbersFromComments(ArrayList<Integer> postNumbersFromComments) {
        this.postNumbersFromComments = postNumbersFromComments;
    }


}
