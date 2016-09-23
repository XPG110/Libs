package com.example.nanfu.libs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/28.
 */
public class HRDiagramView extends View {

    private Context context;

    /**
     * 画笔
     */
    private Paint mPaint;
    public HRDiagramView(Context context) {
        super(context);
        init(context);
    }

    public HRDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HRDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
