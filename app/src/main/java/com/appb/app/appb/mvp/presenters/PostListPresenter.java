package com.appb.app.appb.mvp.presenters;

import android.text.Html;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.views.PostListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 22.04.2017.
 */
@InjectViewState
public class PostListPresenter extends MvpPresenter<PostListView> {

    private static final int FIRST = 1;

    public void loadPosts(int threadNumber, String board) {

        API.getInstance()
                .getPostsRX(board, threadNumber, FIRST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(posts -> setAnswersForPosts(posts)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                )
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

    public Observable<ArrayList<Post>> setAnswersForPosts(ArrayList<Post> posts){
        return Observable.create(subscriber -> {

            for (int i = 0; i < posts.size(); i++) {

                ArrayList<Post> answers = new ArrayList<>();

                for (int j = 0; j < posts.size(); j++) {

                    String comment =  Html.fromHtml(posts.get(j).getComment()).toString();
                    String number = String.valueOf(posts.get(i).getNum());

                    if (comment.contains(number)) {
                        answers.add(posts.get(j));
                    }
                }

                if (answers.size() > 0){
                    posts.get(i).setAnswers(answers);
                }
            }

            subscriber.onNext(posts);
        });
    }
}