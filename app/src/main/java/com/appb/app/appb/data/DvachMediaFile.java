package com.appb.app.appb.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 1 on 11.03.2017.
 */

public class DvachMediaFile implements Parcelable {

    private static final String JPG = ".jpg";
    private static final String PNG = ".png";
    private static final String WEBM = ".webm";
    private static final String GIF = ".gif";
    private static final String UNKNOWN = "?";

    @SerializedName("displayName")
    String displayName;
    @SerializedName("fullName")
    String fullName;
    String name;
    String path;
    String thumbnail;

    protected DvachMediaFile(Parcel in) {
        displayName = in.readString();
        fullName = in.readString();
        name = in.readString();
        path = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<DvachMediaFile> CREATOR = new Creator<DvachMediaFile>() {
        @Override
        public DvachMediaFile createFromParcel(Parcel in) {
            return new DvachMediaFile(in);
        }

        @Override
        public DvachMediaFile[] newArray(int size) {
            return new DvachMediaFile[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        if (name.toLowerCase().contains(JPG)) {
            name = JPG;
        } else if (name.toLowerCase().contains(WEBM)) {
            name = WEBM;
        } else if (name.toLowerCase().contains(PNG)) {
            name = PNG;
        } else if (name.toLowerCase().contains(GIF)) {
            name = GIF;
        } else {
            name = UNKNOWN;
        }
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(fullName);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(thumbnail);

    }
}


