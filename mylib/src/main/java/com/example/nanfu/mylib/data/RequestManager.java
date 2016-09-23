package com.example.nanfu.mylib.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/8/24.
 */
public class RequestManager {

    public static RequestQueue requestQueue;
    public static ImageLoader mImageLoader;

    /**
     * 初始化
     * @param context
     * @param
     */
    public static void init(Context context, ImageLoader.ImageCache imageCache){
        requestQueue= Volley.newRequestQueue(context);
        mImageLoader=new ImageLoader(requestQueue,imageCache);
    }

    /**
     * 添加一个请求
     * @param request
     */
    public static void addRequst(Request<?> request){
        requestQueue.add(request);
    }
    /**
     * 取消请求
     */
    public static void cancelRequest(Request<?> request){
        if(request.getTag()!=null)
            requestQueue.cancelAll(request.getTag());
    }
    public static void cancelRequest(Object object){
        if(object!=null){
            requestQueue.cancelAll(object);
        }
    }

    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

}
