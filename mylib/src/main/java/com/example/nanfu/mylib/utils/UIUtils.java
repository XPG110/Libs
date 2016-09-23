package com.example.nanfu.mylib.utils;

import android.content.Context;

import com.example.nanfu.mylib.view.CustomLoadingDialog;
import com.example.nanfu.mylib.view.LoadView;

/**
 * Created by Administrator on 2016/8/26.
 */
public class UIUtils {

    public static LoadView createLoadView(Context context){
        return new CustomLoadingDialog(context);
    }
}
