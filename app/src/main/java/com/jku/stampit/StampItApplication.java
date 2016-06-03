package com.jku.stampit;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 09/05/16.
 */
public class StampItApplication extends Application {
    private static StampItApplication instance;
    public static String APP_PREFERENCES = "StampItPreferences";
    public static StampItApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
