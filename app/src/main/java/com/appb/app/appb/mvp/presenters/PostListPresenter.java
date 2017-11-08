package com.appb.app.appb.mvp.presenters;

import android.text.Html;
import android.util.Log;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.views.PostListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final int ARROWS_LENGTH = 2;
    private static final String TAG = "PostListPresenter";

    public void loadPosts(int threadNumber, String board) {

        API.getInstance()
                .getPostsRX(board, threadNumber, FIRST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(posts -> subscribeForAnswers(posts)
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

    private Observable<ArrayList<Post>> setAnswersForPosts(ArrayList<Post> posts){
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

    private Observable<ArrayList<Post>> subscribeForAnswers(ArrayList<Post> posts){
        return Observable.create(subscriber -> {

            HashMap<Integer, Post> postNumbers = new HashMap<>();

            for (Post post : posts){
                setPostNumberFromComment(post);
                postNumbers.put(post.getNum(), post);
            }

            for (Post post : posts){
                ArrayList<Post> answersPosts = new ArrayList<>();
                for (int postNumber : post.getPostNumbersFromComments()){
                    Post answerPost = postNumbers.get(postNumber);
                    answersPosts.add(answerPost);
                }
                post.setAnswers(answersPosts);
            }

            subscriber.onNext(posts);
        });
    }

    private void setPostNumberFromComment(Post post){
        ArrayList<Integer> postNumbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("([>]{2})+([0-9]+)");
        Matcher matcher = pattern.matcher(post.getComment());
        while (matcher.find()){
            postNumbers.add(Integer.valueOf(matcher.group(2)));
        }
        post.setPostNumbersFromComments(postNumbers);
    }
}