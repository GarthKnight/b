package com.appb.app.appb.data;

/**
 * Created by 1 on 11.03.2017.
 */

public class File {

    String displayname;
    String fullname;
    String name;
    String path;
    String thumbnail;

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
        } else{
            name = ".webm";
        }
        return name;
    }
}


