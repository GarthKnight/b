package com.appb.app.appb;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    private static BaseApp instance;
    private static String TAG = BaseApp.class.getSimpleName();
//    private Merlin merlin;

    public BaseApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);   //this must be uncomment when min api level is 14
    }

}