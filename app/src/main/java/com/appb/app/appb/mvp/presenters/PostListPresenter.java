package com.appb.app.appb.mvp.presenters;

import android.text.Html;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.views.PostListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 22.04.2017.
 */
@InjectViewState
public class PostListPresenter extends MvpPresenter<PostListView> {


    private static final int FIRST = 1;

    ArrayList<Post> posts;
    HashMap<Integer, Integer> answers;

    public HashMap<Integer, Integer> getAnswers() {

        answers = new HashMap<>();

        for (int i = 0; i < posts.size(); i++) {

            for (int j = 0; j < posts.size(); j++) {

                String comment =  Html.fromHtml(posts.get(j).getComment()).toString();
                String number = String.valueOf(posts.get(i).getNum());

                if (comment.contains(number)) {

                    answers.put(posts.get(i).getNum(), posts.get(j).getNum());
                }

            }

        }

        return answers;
    }

    public void getPosts(int threadNumber) {
        String board = "b";


        API.getInstance()
                .getPostsRX(board, threadNumber, FIRST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getViewState().onLoadingStart())
                .doOnTerminate(() -> getViewState().onLoadingEnd())
                .subscribe(new Observer<ArrayList<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       getViewState().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Post> posts) {
                        getViewState().onPostsLoaded(posts);

                    }
                });
    }

    public void searchAnswers() {

    }
}