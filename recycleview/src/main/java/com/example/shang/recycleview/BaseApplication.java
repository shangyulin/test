package com.example.shang.recycleview;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shang on 2017/9/19.
 */

public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
