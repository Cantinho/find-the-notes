package com.findthenotes.findthenotes.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.activeandroid.ActiveAndroid;

public class FindTheNotesApplication extends Application {
    private static Context context;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ActiveAndroid.initialize(this);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
