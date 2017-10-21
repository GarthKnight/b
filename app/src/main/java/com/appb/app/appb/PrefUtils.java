package com.appb.app.appb;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.appb.app.appb.data.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.appb.app.appb.BaseApp.getContext;

/**
 * Created by seishu on 08.10.2017.
 */

public class PrefUtils {


    private static final String MY_BOARDS = "myBoards";

    public static HashSet<String> getMyBoards(){
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        Log.d("MY_BOARDS_GET", "getMyBoards: " +  ((HashSet<String>) sp.getStringSet(MY_BOARDS, new HashSet<>())).toString());
        return ((HashSet<String>) sp.getStringSet(MY_BOARDS, new HashSet<>()));
    }

    public static void setMyBoards(Set<String> boards){
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(MY_BOARDS, boards).apply();
        Log.d("MY_BOARDS SAVE: ", boards.toString());
    }


}
