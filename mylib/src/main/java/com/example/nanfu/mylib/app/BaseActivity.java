package com.example.nanfu.mylib.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nanfu.mylib.data.RequestManager;
import com.example.nanfu.mylib.utils.UIUtils;
import com.example.nanfu.mylib.view.LoadView;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class BaseActivity extends FragmentActivity {

    private LoadView loadView;
    private static View rootView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView= LayoutInflater.from(this).inflate(getLayoutRes(),null);
        ActivityManager.activityCreate(this);
        setContentView(rootView);
        ButterKnife.bind(this);

    }

    public LoadView getLoadView(){
        if(loadView==null){
            UIUtils.createLoadView(this);
        }else {
        }
        return loadView;
    }
    /**
     * 绑定资源
     * @return
     */
    public abstract int getLayoutRes();

    /**
     * 数据加载
     */
    protected void afterInject(Bundle savedInstanceState){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.activityDestroy(this);
        RequestManager.cancelRequest(this);
    }
}
