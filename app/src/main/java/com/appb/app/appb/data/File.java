package com.appb.app.appb.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 1 on 11.03.2017.
 */

public class File implements Parcelable{

    String displayname;
    String fullname;
    String name;
    String path;
    String thumbnail;

    protected File(Parcel in) {
        displayname = in.readString();
        fullname = in.readString();
        name = in.readString();
        path = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

    public String getDisplayname() {
        return displayname;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        if(name.toLowerCase().contains(".jpg")){
         name = ".jpg";
        } else if(name.toLowerCase().contains(".webm")){
            name = ".webm";
        } else if(name.toLowerCase().contains(".png")){
            name = ".png";
        } else{
            name = "?";
        }
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayname);
        dest.writeString(fullname);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(thumbnail);

    }
}


