package com.example.nanfu.mylib.view;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CustomLoadingDialog extends Dialog implements LoadView{
    public CustomLoadingDialog(Context context) {
        super(context);
    }

    public CustomLoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onShowLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }
}
