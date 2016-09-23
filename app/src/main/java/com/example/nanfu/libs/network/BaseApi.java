package com.example.nanfu.libs.network;

import android.util.Log;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/9.
 */
public class BaseApi<T> {

    public BaseApi(){
        initData();
    }

    protected void initData(){

    }

    public void request(Observable<T> obs, final SpliceListener listener){

        obs.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<T>() {
            @Override
            public void call(T responseBody) {
                listener.onNext(responseBody);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                listener.onError();
            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });
    }

    protected interface SpliceListener<T> {
        void onNext(T t);
        void onError();
    }
}
