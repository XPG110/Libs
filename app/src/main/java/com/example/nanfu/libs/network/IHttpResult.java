package com.example.nanfu.libs.network;

/**
 * Created by Administrator on 2016/9/8.
 */
public interface IHttpResult<T> {
    /**
     * 请求成功返回结果
     */
    void onSuccess(T t);
    /**
     * 请求失败返回结果
     */
    void onDefealt(T t);
    /**
     * 取消请求
     */
    void onCancel();
}
