package com.example.nanfu.libs.network;

import android.util.Log;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/26.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.resultCode != 1) {
            try {
                throw new ApiException();
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
//        Log.d("Tag","resultCode="+tHttpResult.resultCode);
        return tHttpResult.data;
    }
}
