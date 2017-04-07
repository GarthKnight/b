package com.appb.app.appb.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1 on 10.03.2017.
 */

public class Thread implements Parcelable {

    @SerializedName("files_count")
    int files_count;
    @SerializedName("post_count")
    int postCount;
    @SerializedName("thread_num")
    String threadNum;
    ArrayList<Post> posts;

    protected Thread(Parcel in) {
        files_count = in.readInt();
        postCount = in.readInt();
        threadNum = in.readString();
    }

    public static final Creator<Thread> CREATOR = new Creator<Thread>() {
        @Override
        public Thread createFromParcel(Parcel in) {
            return new Thread(in);
        }

        @Override
        public Thread[] newArray(int size) {
            return new Thread[size];
        }
    };

    public ArrayList<Post> getPosts(){
        return posts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(files_count);
        dest.writeInt(postCount);
        dest.writeString(threadNum);
    }
}
