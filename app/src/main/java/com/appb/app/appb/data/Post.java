package com.appb.app.appb.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */

public class Post implements Parcelable{

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

    private Post(Parcel in) {
        banned = in.readInt();
        closed = in.readInt();
        comment = in.readString();
        date = in.readString();
        email = in.readString();
        files = in.createTypedArrayList(File.CREATOR);
        files_count = in.readInt();
        lastshit = in.readLong();
        op = in.readInt();
        parent = in.readString();
        post_count = in.readInt();
        sticky = in.readInt();
        subject = in.readString();
        tags = in.readString();
        timestamp = in.readLong();
        trip = in.readString();
        num = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(banned);
        dest.writeInt(closed);
        dest.writeString(comment);
        dest.writeString(date);
        dest.writeString(email);
        dest.writeTypedList(files);
        dest.writeInt(files_count);
        dest.writeLong(lastshit);
        dest.writeInt(op);
        dest.writeString(parent);
        dest.writeInt(post_count);
        dest.writeInt(sticky);
        dest.writeString(subject);
        dest.writeString(tags);
        dest.writeLong(timestamp);
        dest.writeString(trip);
        dest.writeInt(num);
    }
}
