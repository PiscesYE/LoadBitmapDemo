package com.piscesye.loadbitmapdemo.global;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context globalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = getApplicationContext();
    }

    public static Context getGlobalContext() {
        return globalContext;
    }
}
