package com.example.nanfu.mylib;

import android.content.Context;

import com.example.nanfu.mylib.data.ImageCache;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyLib {

    private static Context appContext;
    public static boolean ISDEBUG=true;

    /**
     * 初始化libs
     * @param context
     */
    public static void init(Context context){
        init(context,null);
    }

    /**
     * 初始化libs
     * @param context
     * @param imageCache
     */
    public static void init(Context context,ImageCache imageCache){
        appContext=context;
        imageCache=new ImageCache();
    }

    /**
     * 获取上下文
     * @param context
     * @return
     */
    public static Context getAppContext(Context context){
        return appContext;
    }

}
