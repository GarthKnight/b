package com.appb.app.appb.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */
@Parcel
public class Post {

    int banned;
    int closed;
    String comment;
    String date;
    String email;
    @SerializedName("files")
    ArrayList<DvachMediaFile> dvachMediaFiles;
    int files_count;
    long lastshit;
    int op;
    protected String parent;
    int post_count;
    int sticky;
    String subject;
    String tags;
    long timestamp;
    String trip;
    int num;
    ArrayList<Post> answers = new ArrayList<>();
    ArrayList<Integer> postNumbersFromComments;


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
