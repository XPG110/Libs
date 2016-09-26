package com.example.nanfu.libs.network;

import android.util.Log;

import com.example.nanfu.libs.bean.MovieEntity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/9.
 */
public class MovieApi extends BaseApi<MovieEntity> {

    private static MovieApi movieApi=new MovieApi();
    private MovieService service;

    private MovieApi(){
        super();
        service= ApiConfig.ISTEST?ApiConfig.getInstance().getRetrofit().create(MovieService.class)
                :ApiConfig.getRetrofit("").create(MovieService.class);
    }

    public static MovieApi getInstance(){
        movieApi.initData();
        return movieApi;
    }

    public void getMovie1(final ProgressDialogSubscribe<MovieEntity> subscribe) {

//        Observable observable=service.getMovie(0,10).map(new HttpResultFunc());
        Observable<MovieEntity> observable = service.getMovie(0, 10);

        request1(observable, subscribe);

    }

    public void getMovie(final IHttpResult<MovieEntity> result){
//        Log.d("Tag",service.toString());
        Observable<MovieEntity> obs=service.getMovie(0,10);

        request(obs, new SpliceListener<MovieEntity>() {
            @Override
            public void onNext(MovieEntity movieEntity) {
                if(result!=null){
                    result.onSuccess(movieEntity);
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
