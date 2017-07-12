package com.appb.app.appb.api;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.data.Post;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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



    public Observable<ArrayList<Board>> getBoards() {
        return serviceBoards.boards();
    }


    public Observable<Boards> getDifferentBoardsRX() {
        return serviceBoards.differentBoardsRX();
    }

    public Observable<BoardPage> getThreadsRX(int index, String board) {
        String page = index == 1 ? "index" : String.valueOf(index);
        return serviceBoards.threadsRX(board, page);
    }

    public Observable<ArrayList<Post>> getPostsRX(String boardName, int threadNumber, int pathNum) {
        return serviceBoards.postsRX("get_thread", boardName, threadNumber, pathNum);
    }



    public interface DvachService {

        @GET("/boards.json")
        Observable<ArrayList<Board>> boards();

        @GET("{board}/{index}.json")
        Observable<BoardPage> threadsRX(@Path("board") String board,
                                        @Path("index") String index);

        @GET("makaba/mobile.fcgi?task=get_boards")
        Observable<Boards> differentBoardsRX();

        @GET("/makaba/mobile.fcgi")
        Observable<ArrayList<Post>> postsRX(@Query("task") String thread,
                                    @Query("board") String boardName,
                                    @Query("thread") int threadNumber,
                                    @Query("post") int pathNum);

    }

}
