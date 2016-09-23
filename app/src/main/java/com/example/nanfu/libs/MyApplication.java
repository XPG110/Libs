package com.example.nanfu.libs;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
