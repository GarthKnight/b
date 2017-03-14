package com.appb.app.appb.api;

import com.appb.app.appb.data.BoardPage;
import com.appb.app.appb.data.Boards;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by 1 on 06.03.2017.
 */

public class API {

    private static final String URL = "http://2ch.hk";
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


    public void getThreads(Callback<BoardPage> callback) {
        serviceBoards.threads().enqueue(callback);
    }




    public interface DvachService {
        @GET("makaba/mobile.fcgi?task=get_boards")
        Call<Boards> boards();

        @GET("b/index.json")
        Call<BoardPage> threads();
    }

}
