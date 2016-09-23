package com.example.nanfu.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanfu.libs.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */

public class CustomIndicator extends LinearLayout {

    private Context mContext;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * tab底部条
     */
    private Path mPath;
    /**
     * Tab数量
     */
    private int itemCount;
    /**
     * 默认显示的Tab数量
     */
    private static final int DEFAULT_ITEM_COUNT=3;
    /**
     * 装载内容的ViewPager
     */
    private ViewPager mViewPager;
    /**
     * tab上的内容
     */
    private List<String> mTabTitle;

    private PageChangeListener pageChangeListener;

    private int COLOR_TEXT_NORMAL= Color.BLACK;

    /**
     * tab底部三角形宽高
     */
    private int mTriangleWidth;
    private int mTriangleHeight;

    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private static final float RADIO_TRIANGEL = 1.0f / 6;
    /**
     * 三角形的最大宽度
     */
    private final int DIMENSION_TRIANGEL_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);

    /**
     * 初始时，三角形指示器的偏移量
     */
    private int mInitTranslationX;
    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    public CustomIndicator(Context context) {
        super(context);
        init(context);
    }

    public CustomIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context,attrs);
        init(context);
    }

    public CustomIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context,attrs);
        init(context);
    }

    private void setAttrs(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CustomIndicator);
        itemCount=array.getInteger(R.styleable.CustomIndicator_item_count,DEFAULT_ITEM_COUNT);
        
        if(itemCount<0){
            itemCount=DEFAULT_ITEM_COUNT;
        }
        array.recycle();
    }

    private void init(Context context){
        mContext=context;

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / itemCount * RADIO_TRIANGEL);// 1/6 of
        // width
        mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);

        // 初始化三角形
        initTriangle();
//        initLine();

        // 初始时的偏移量
        mInitTranslationX = getWidth() / itemCount / 2 - mTriangleWidth;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        // 画笔平移到正确的位置
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 初始化三角形指示器
     */
    private void initTriangle(){
        mPath = new Path();

        mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 初始化线形指示器
     */
    private void initLine(){
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.close();
    }
    /**
     * 设置内容viewpager
     * @param viewPager
     * @param position
     */
    public void setViewPager(ViewPager viewPager, int position){
        mViewPager=viewPager;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTextViewAttr();
                setTextViewAttr(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetTextViewAttr();
        setTextViewAttr(position);
    }

    /**
     * 指示器滑动
     */
    private void scroll(int position, float offset){

    }

    private void setItemClickEvent(){
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d("Tag","点击了");
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
    /**
     * 设置显示多少个Tab
     */
    public void setVisibleTabCount(int count){
        itemCount=count;
    }

    /**
     * 设置tab布局内容，可在布局中写死
     * @param datas
     */
    public void setTabItemText(List<String> datas){
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0){

            this.removeAllViews();
            this.mTabTitle= datas;

            for (String title : mTabTitle)  {
                // 添加view
                addView(generateTextView(title));
            }
             //设置item的click事件
            setItemClickEvent();
        }
    }

    /**
     * 选中textview的属性
     */
    private void setTextViewAttr(int position){
        View view=getChildAt(position);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(Color.BLUE);
            ((TextView) view).setTextSize(22);
        }
    }

    /**
     * 重置TextView属性
     */
    private void resetTextViewAttr(){
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(Color.BLACK);
                ((TextView) view).setTextSize(16);
            }
        }
    }

    /**
     * 根据标题生成我们的TextView
     *
     * @param text
     * @return
     */
    private TextView generateTextView(String text){
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / itemCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);
        return tv;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int count=getChildCount();

        Log.d("Tag",count+"数量");
        if(count==0){
            return;
        }

        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            LinearLayout.LayoutParams params= (LayoutParams) view.getLayoutParams();
            params.weight=0;
            params.width = getScreenWidth() / itemCount;
            view.setLayoutParams(params);
        }

        setItemClickEvent();
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public interface PageChangeListener{
        public void onPageScrolled(int position, float positionOffset,
        int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }
}
