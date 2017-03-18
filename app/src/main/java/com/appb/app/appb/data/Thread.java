package com.appb.app.appb.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 1 on 10.03.2017.
 */

public class Thread implements Parcelable {

    int files_count;
    int post_count;
    String thread_num;
    ArrayList<Post> posts;

    protected Thread(Parcel in) {
        files_count = in.readInt();
        post_count = in.readInt();
        thread_num = in.readString();
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
        dest.writeInt(post_count);
        dest.writeString(thread_num);
    }
}
