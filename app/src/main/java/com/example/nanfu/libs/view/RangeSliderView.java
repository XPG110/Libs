package com.example.nanfu.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.nanfu.libs.R;

/**
 * Created by Administrator on 2016/9/22.
 */

public class RangeSliderView extends View {

    private Context mContext;

    private Paint mPaint1;
    private Paint mPaint;
    /**
     * 背景路径
     */
    private Path mPath;
    /**
     * 覆盖路径
     */
    private Path mPath1;
    /**
     * 默认颜色
     */
    private static final String DEFAULT_COLOR = "#ff0000";
    /**
     * 默认节点数
     */
    private static final int DEFAULT_NODE_COUNT=3;
    /**
     * 实际节点数
     */
    private int count;
    /**
     * 节点间距
     */
    private int NODE_LENGTH=200;
    /**
     * 节点半径
     */
    private int NODE_RADIUS=25;

    private int heigth=30;

    public RangeSliderView(Context context) {
        super(context);
        init(context);
    }

    public RangeSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttr(context,attrs);
    }

    public RangeSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttr(context,attrs);
    }

    private void init(Context context){
        mContext=context;
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.parseColor(DEFAULT_COLOR));
        mPaint.setStrokeWidth(5f);

        mPaint1=new Paint();
        //设置镂空（方便查看效果）
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint1.setAntiAlias(true);
        mPaint1.setFilterBitmap(true);
        mPaint1.setColor(Color.parseColor(DEFAULT_COLOR));
        mPaint1.setStrokeWidth(5f);

        mPath=new Path();
        mPath1=new Path();

        count=DEFAULT_NODE_COUNT;
    }
    /**
     * 设置属性
     * @param context
     * @param attrs
     */
    private void setAttr(Context context, AttributeSet attrs){
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.RangeSliderView);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width=25;
        for(int i=0;i<count;i++){
            canvas.drawCircle(width+NODE_LENGTH,heigth,NODE_RADIUS,mPaint);
            mPath.moveTo(width,heigth);
            mPath.lineTo(width+NODE_LENGTH,heigth);
            width+=NODE_LENGTH;
            Log.d("Tag","width宽度"+width);
        }
        canvas.drawPath(mPath,mPaint);

        width=25;
        mPaint1.setColor(Color.GREEN);
        for(int i=1;i<2;i++){
            canvas.drawCircle(width+NODE_LENGTH,heigth,NODE_RADIUS,mPaint1);
            canvas.drawCircle(width+NODE_LENGTH,heigth,NODE_RADIUS+3,mPaint1);
            mPath1.moveTo(width,heigth);
            mPath1.lineTo(width+NODE_LENGTH,heigth);
            width+=NODE_LENGTH;
            Log.d("Tag","覆盖后width宽度"+width);
        }
        canvas.drawPath(mPath1,mPaint1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置节点个数
     */
    public void setSchedule(int count){
        this.count=count;
        if(count<1){
            try {
                throw new Exception("节点个数不能小于1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        invalidate();
    }
}
