package com.example.nanfu.libs.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/23.
 */

public class ChartView extends View {

    private Context mContext;
    /**
     * 画笔
     */
    private Paint mPaint;

    private Path mPath;
    /**
     * 控件宽高
     */
    private int preWidth, preHeigth;
    /**
     * 默认颜色
     */
    private static final int DEFAULT_COLOR = 0xff000000;


    public ChartView(Context context) {
        super(context);
        init(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }
}
