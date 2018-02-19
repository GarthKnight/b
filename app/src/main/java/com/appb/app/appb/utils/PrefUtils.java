package com.appb.app.appb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.appb.app.appb.BaseApp.getContext;

/**
 * Created by seishu on 08.10.2017.
 */

public class PrefUtils {

    private static final String TAG = "PrefUtils";
    private static final String MY_BOARDS = "my_boards";

    public static ArrayList<String> getMyBoards() {
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        String boards = sp.getString(MY_BOARDS, "[]");
        Log.d(TAG, "getMyBoards: " + boards);
        return new Gson().fromJson(boards, new TypeToken<List<String>>() {
        }.getType());
    }

    public static void saveBoard(String newBoard) {
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        ArrayList<String> boards = getMyBoards();
        boolean wasEquals = false;
        for (String b : boards) {
            if (b.equals(newBoard)) {
                wasEquals = true;
                break;
            }
        }
        if (!wasEquals) {
            boards.add(newBoard);
        }
        editor.putString(MY_BOARDS, new Gson().toJson(boards));
        editor.apply();
        Log.d(TAG, "saveBoard: " + getMyBoards().toString());
    }

    public static void removeBoard(String board) {
        SharedPreferences sp = getContext().getSharedPreferences(MY_BOARDS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        ArrayList<String> boards = getMyBoards();
        boolean wasRemoved = false;
        for (String b : boards) {
            if (b.equals(board)) {
                boards.remove(b);
                wasRemoved = true;
                break;
            }
        }
        if (wasRemoved) {
            editor.putString(MY_BOARDS, new Gson().toJson(boards));
            editor.apply();
            Log.d(TAG, "saveBoard: " + getMyBoards().toString());
        }
    }

    public static boolean isFavourite(String board) {
        return getMyBoards().contains(board);
    }

}
