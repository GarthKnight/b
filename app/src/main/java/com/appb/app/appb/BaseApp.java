package com.appb.app.appb;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;

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
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getContext());
//        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);   //this must be uncomment when min api level is 14
    }

    public static BaseApp getInstance() {
        if (instance == null) {
            synchronized (BaseApp.class) {
                if (instance == null) {
                    instance = new BaseApp();
                }
            }
        }
        return instance;
    }

}