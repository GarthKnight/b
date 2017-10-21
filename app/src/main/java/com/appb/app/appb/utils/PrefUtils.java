package com.appb.app.appb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import static com.appb.app.appb.BaseApp.getContext;

/**
 * Created by seishu on 08.10.2017.
 */

public class PrefUtils {


    private static final String MY_BOARDS = "myBoards";

    public static HashSet<String> getMyBoards(){
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        Log.d("MY_BOARDS_GET", "syncFavouritesWithPreference: " +  ((HashSet<String>) sp.getStringSet(MY_BOARDS, new HashSet<>())).toString());
        return ((HashSet<String>) sp.getStringSet(MY_BOARDS, new HashSet<>()));
    }

    public static void setMyBoards(Set<String> boards){
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(MY_BOARDS, boards).commit();
        Log.d("MY_BOARDS SAVE: ", boards.toString());
    }


}
