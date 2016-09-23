package com.example.nanfu.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.nanfu.libs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class CustomRateView extends View {

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 半径
     */
    private float radius;
    /**
     * 圆弧之间间隙
     */
    private float interval=2;
    /**
     * 字体大小
     */
    private float textSize;
    /**
     * 圆环宽度
     */
    private float strokeWidth;
    /**
     * 圆环外切矩形
     */
    private RectF rectF;

    private int centre;
    private List<Float> angles;
    private List<Integer> colors;
    private List<Float> persent;


    public CustomRateView(Context context) {
        super(context);
        init(context);
    }

    public CustomRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        init(context);
    }

    public CustomRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context,attrs);
        init(context);
    }

    private void getAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomRateView);
        radius=typedArray.getFloat(R.styleable.CustomRateView_radius,50);
        textSize=typedArray.getDimension(R.styleable.CustomRateView_textSize,30);
        strokeWidth=typedArray.getFloat(R.styleable.CustomRateView_strokeWidth,15);
    }
    private void init(Context context){
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.BUTT);

        initAngelAndColor();
    }

    private void initAngelAndColor(){
        angles=new ArrayList<>();
        colors=new ArrayList<>();
        persent=new ArrayList<>();

        persent.add(1f);
        int total=0;

        for (int i=0;i<persent.size();i++){
            total+=persent.get(i);
        }
        for (int i=0;i<persent.size();i++){
            Log.d("Tag","角度："+(persent.get(i)/total)*360);
            angles.add((persent.get(i)/total)*360);
        }

        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.LTGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        float initialAngle=0;

        //view半径
        centre=getWidth()/2;
        int radius=(int)(centre-strokeWidth/2);

        /**
         * 绘制圆弧
         */
        mPaint.setStrokeWidth(strokeWidth);
        rectF=new RectF(centre-radius,centre-radius,centre+radius,centre+radius);

        for(int i=0;i<angles.size();i++){
            Log.d("Tag",initialAngle+"");
            mPaint.setColor(colors.get(i));
            canvas.drawArc(rectF,initialAngle,angles.get(i)-interval,false,mPaint);
            initialAngle=initialAngle+angles.get(i);
        }

        /**
         * 环中的字
         */
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        float textWidth=mPaint.measureText("店铺");
        float textHeigth=mPaint.measureText("店");
        canvas.drawText("店铺",centre-textWidth/2,centre+textHeigth/2,mPaint);

    }

    /**
     * 圆环弧形百分比数值及其颜色
     * @param persent
     * @param colors
     */
    public void setPersentAndColor(List<Float> persent,List<Integer> colors){
        this.angles.clear();
        this.colors.clear();

        this.persent=persent;
        this.colors=colors;

        if(persent==null||persent.size()==0){
            return;
        }
        int total=0;
        for (int i=0;i<persent.size();i++){
            total+=persent.get(i);
        }
        for (int i=0;i<persent.size();i++){
            Log.d("Tag","角度："+(persent.get(i)/total)*360);
            angles.add((persent.get(i)/total)*360);
        }
        Log.d("Tag",angles.size()+"anglesize");
        invalidate();
    }


}
