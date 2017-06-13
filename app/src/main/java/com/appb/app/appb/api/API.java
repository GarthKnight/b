package com.appb.app.appb.api;

import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.data.Post;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 1 on 06.03.2017.
 */

public class API {

    public static final String URL = "http://2ch.hk";
    private Retrofit retrofit;

    private static API instance = new API();
    private DvachService serviceBoards;

    public static API getInstance() {
        return instance;
    }

    private API() {
        init();
    }

    public void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        serviceBoards = retrofit.create(DvachService.class);

    }

    public void getLists(Callback<Boards> callback) {
        serviceBoards.boards().enqueue(callback);
    }

    public void getThreads(int index, Callback<BoardPage> callback) {
        String page = index == 1 ? "index" : String.valueOf(index);
        serviceBoards.threads(page).enqueue(callback);
    }

    public void getPosts(String boardName, int threadNumber, int pathNum, Callback<ArrayList<Post>> callback) {
        serviceBoards.posts("get_thread", boardName, threadNumber, pathNum).enqueue(callback);
    }

    public Observable<Boards> getBoardsRX() {
        return serviceBoards.boardsRX();
    }

    public Observable<BoardPage> getThreadsRX(int index) {
        String page = index == 1 ? "index" : String.valueOf(index);
        return serviceBoards.threadsRX(page);
    }

    public Observable<ArrayList<Post>> getPostsRX(String boardName, int threadNumber, int pathNum) {
        return serviceBoards.postsRX("get_thread", boardName, threadNumber, pathNum);
    }



    public interface DvachService {
        @GET("makaba/mobile.fcgi?task=get_boards")
        Call<Boards> boards();

        @GET("b/{index}.json")
        Call<BoardPage> threads(@Path("index") String index);

        @GET("/makaba/mobile.fcgi")
        Call<ArrayList<Post>> posts(@Query("task") String thread,
                                    @Query("board") String boardName,
                                    @Query("thread") int threadNumber,
                                    @Query("post") int pathNum);



        @GET("b/{index}.json")
        Observable<BoardPage> threadsRX(@Path("index") String index);

        @GET("makaba/mobile.fcgi?task=get_boards")
        Observable<Boards> boardsRX();

        @GET("/makaba/mobile.fcgi")
        Observable<ArrayList<Post>> postsRX(@Query("task") String thread,
                                    @Query("board") String boardName,
                                    @Query("thread") int threadNumber,
                                    @Query("post") int pathNum);

    }

}
