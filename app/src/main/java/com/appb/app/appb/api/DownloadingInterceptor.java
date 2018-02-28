package com.appb.app.appb.api;

import com.appb.app.appb.data.DvachError;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by seishu on 27.02.2018.
 */

public class DownloadingInterceptor implements Interceptor {


    private static final String ERROR = "Error";
    private static final String CODE = "Code";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;

        newRequest = request.newBuilder()
                .addHeader("Downloader-Id", "download-identifier")
                .method(request.method(), request.body())
                .build();

        Response response = chain.proceed(newRequest);
        Response oneMoreResponse = chain.proceed(newRequest);

        String responseString = oneMoreResponse.body().source().readUtf8();
        DvachError error = null;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has(ERROR)) {
                error = new DvachError(jsonObject.getInt(CODE), jsonObject.getString(ERROR));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (error != null) {
            throwException(new DownloadErrorException(error));
        }

        return response;
    }

    private void throwException(DownloadErrorException e) throws IOException {
        throw e;
    }
}
