package com.example.nanfu.libs.network;

import com.example.nanfu.libs.bean.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface MovieService {
    @GET("top250")
    Observable<MovieEntity> getMovie(@Query("start") int start, @Query("count") int count);
}
