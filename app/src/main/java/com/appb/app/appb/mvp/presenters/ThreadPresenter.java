package com.appb.app.appb.mvp.presenters;

import android.text.Html;

import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.views.ThreadView;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 1 on 22.04.2017.
 */

public class ThreadPresenter extends MvpPresenter<ThreadView> {

    ArrayList<Post> posts;
    HashMap<Integer, Integer> answers;

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public HashMap<Integer, Integer> getAnswers() {

        answers = new HashMap<>();

        for (int i = 0; i < posts.size(); i++) {

            for (int j = 0; j < posts.size(); j++) {

                if (Html.fromHtml(posts.get(j).getComment()).toString().contains(String.valueOf(posts.get(i).getNum()))) {

                }

            }

        }

        return answers;
    }

    public void searchAnswers() {

    }
}