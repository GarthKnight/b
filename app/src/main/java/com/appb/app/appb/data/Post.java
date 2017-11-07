package com.appb.app.appb.data;
import org.parceler.Parcel;
import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */
@Parcel
public class Post {

    private int banned;
    private int closed;
    private String comment;
    private String date;
    private String email;
    private ArrayList<File> files;
    private int files_count;
    private long lastshit;
    private int op;
    private String parent;
    private int post_count;
    private int sticky;
    private String subject;
    private String tags;
    private long timestamp;
    private String trip;
    private int num;
    private ArrayList<Post> answers;
    private ArrayList<Integer> postNumbersFromComments;




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

    public String getSubject(){
        return subject;
    }

    public ArrayList<File> getFiles() {
        return files;
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
