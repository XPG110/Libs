package com.example.nanfu.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/30.
 */
public class CustomViewGroup extends ViewGroup {

    private Context mContext;

    /**
     * 横竖排列
     */
    private static final int ORIENTATION_HORIZONTAL=0,ORIENTATION_VERTICAL=1;

    private int mOritation=0;

    /**
     * 默认最大横竖数目
     */
    private static final int DEFAULT_MAX_COLUM=Integer.MAX_VALUE,DEFAULT_MAX_ROW=Integer.MAX_VALUE;

    private int max_Colum,max_Row;


    public CustomViewGroup(Context context) {
        super(context);
        mContext=context;
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth=0;
        int parentHeight=0;

        // 声明临时变量存储子元素的测量状态
        int childMeasureState = 0;

        if(getChildCount()>0){
            for(int i=0;i<getChildCount();i++){
                View child=getChildAt(i);

                if(child.getVisibility()!=View.GONE){

                    measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);

                    int childMeasureSize=Math.max(child.getMeasuredWidth(),child.getMeasuredHeight());

                    int childMeasureSpec=MeasureSpec.makeMeasureSpec(childMeasureSize,MeasureSpec.EXACTLY);

                    child.measure(childMeasureSpec,childMeasureSpec);

                    MarginLayoutParams mlp= (MarginLayoutParams) child.getLayoutParams();

                    int actualChildWidth=mlp.leftMargin+mlp.rightMargin+child.getMeasuredWidth();
                    int actualChildHeight=mlp.topMargin+mlp.bottomMargin+child.getMeasuredHeight();

                    if(mOritation==ORIENTATION_VERTICAL){
                        parentWidth+=actualChildWidth;
                        parentHeight=Math.max(actualChildHeight,parentHeight);

                    }else {
                        parentHeight+=actualChildHeight;
                        parentWidth=Math.max(actualChildWidth,parentWidth);
                    }
                    childMeasureState = combineMeasuredStates(childMeasureState, child.getMeasuredState());
                }

            }
            /**
             * 与系统推荐的大小比较取其大
             */
            parentWidth=getPaddingLeft()+getPaddingRight()+parentWidth;
            parentHeight+=getPaddingTop()+getPaddingBottom();

            parentWidth=Math.max(parentWidth,getSuggestedMinimumWidth());
            parentHeight=Math.max(parentHeight,getSuggestedMinimumHeight());

            setMeasuredDimension(resolveSizeAndState(parentWidth, widthMeasureSpec, childMeasureState),
                    resolveSizeAndState(parentHeight, heightMeasureSpec, childMeasureState << MEASURED_HEIGHT_STATE_SHIFT));
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(getChildCount()>0){
            int multi=0;
            for(int i=0;i<getChildCount();i++){
                View child=getChildAt(i);

                if(child.getVisibility()!=View.GONE){

                    MarginLayoutParams mlp= (MarginLayoutParams) child.getLayoutParams();
                    int childActualSize=child.getMeasuredWidth();

                    if(mOritation==ORIENTATION_VERTICAL){
                        child.layout(mlp.leftMargin+getPaddingLeft()+multi,getPaddingTop() + mlp.topMargin, childActualSize + getPaddingLeft()
                                + mlp.leftMargin + multi, childActualSize + getPaddingTop() + mlp.topMargin);

                        // 累加倍增值
                        multi += childActualSize + mlp.leftMargin + mlp.rightMargin;
                    } else if (mOritation==ORIENTATION_HORIZONTAL) {
                        // 确定子元素左上、右下坐标
                        child.layout(getPaddingLeft() + mlp.leftMargin, getPaddingTop() + mlp.topMargin + multi, childActualSize + getPaddingLeft()
                                + mlp.leftMargin, childActualSize + getPaddingTop() + mlp.topMargin + multi);

                        // 累加倍增值
                        multi += childActualSize + mlp.topMargin + mlp.bottomMargin;
                    }
                }

            }
        }
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(mContext,attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
