package com.example.nanfu.libs.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nanfu.libs.bean.MovieEntity;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 */

public class ProgressDialogSubscribe<T extends HttpResult> extends Subscriber<T> {

    public IHttpResult iHttpResult;

    private Context context;

    public ProgressDialogSubscribe(IHttpResult iHttpResult, Context context) {
        this.iHttpResult = iHttpResult;
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Tag", "This is a Dialog Start");
    }

    //
    @Override
    public void onCompleted() {
        Log.d("Tag", "This is a Dialog End");
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        iHttpResult.onSuccess(t);
    }
//
//    @Override
//    public void onError(Throwable e) {
//
//    }
//
//    @Override
//    public void onNext(Object o) {
//
//    }

}
