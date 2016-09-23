package com.example.nanfu.libs.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanfu.libs.R;

/**
 * Created by Administrator on 2016/8/29.
 */
public class CircleMenuLayout extends ViewGroup {

    // 该容器内child item的默认尺寸
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    // 该容器的内边距,无视padding属性，如需边距请用该变量
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;
    // 该容器的内边距,无视padding属性，如需边距请用该变量
    private float mPadding;

    private Context mContext;
    /**
     * 手指按下坐标
     */
    private float x,y;

    /**
     * 菜单子项图标
     */
    private int[] imgs;
    /**
     * 菜单子项文本
     */
    private String[] texts;

    /**
     * 菜单子项个数
     */
    private int menuItemCount;

    /**
     *  菜单子项id
     */
    private int mMenuItemLayoutId=R.layout.item_circlemenu;

    private int mStartAngle;

    private int mRadius;

    public CircleMenuLayout(Context context) {
        super(context);
        setPadding(0, 0, 0, 0);
        mContext=context;
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, 0, 0, 0);
        mContext=context;
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0, 0, 0, 0);
        mContext=context;
    }

    public void setIconsAndTexts(int[] imgs,String[] texts) {
        if(imgs==null&&texts==null){
            throw new IllegalArgumentException("图片文本不能全为空！");
        }
        this.imgs=imgs;
        this.texts=texts;

        menuItemCount=imgs==null?imgs.length:texts.length;
        if(imgs!=null&&texts!=null){
            menuItemCount=Math.max(imgs.length,texts.length);
        }
        buildMenuItem();
    }

    private void buildMenuItem(){
        for(int i=0;i<menuItemCount;i++){
            View view=inflateView(i);
            initMenuItem(view,i);
            addView(view);
        }
    }

    private View inflateView(final int childIndex){
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        View itemView = mInflater.inflate(mMenuItemLayoutId, this, false);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mOnMenuItemClickListener != null) {
//                    mOnMenuItemClickListener.onClick(v, childIndex);
//                }
            }
        });
        return itemView;
    }
    public void initMenuItem(View itemView,int childIndex){
        ImageView imageView= (ImageView) itemView.findViewById(R.id.item_iv);
        TextView textView= (TextView) itemView.findViewById(R.id.item_tv);

        imageView.setImageResource(imgs[childIndex]);
        textView.setText(texts[childIndex]);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        Log.d("Tag","child个数："+childCount);
        int left, top;
        // menu item 的尺寸
        int itemWidth = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        // 根据menu item的个数，计算item的布局占用的角度
        float angleDelay = 360 / childCount;
        // 遍历所有菜单项设置它们的位置
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            // 菜单项的起始角度
            mStartAngle %= 360;
            // 计算，中心点到menu item中心的距离
            float distanceFromCenter = mRadius / 2f
                    - itemWidth / 2 - mPadding;
            // distanceFromCenter cosa 即menu item中心点的left坐标
            left = mRadius / 2 + (int)Math.round(distanceFromCenter
                    * Math.cos(Math.toRadians(mStartAngle))
                    * - 1 / 2f * itemWidth);
            // distanceFromCenter sina 即menu item的纵坐标
            top = mRadius / 2
                    + (int) Math.round(distanceFromCenter
                    * Math.sin( Math.toRadians(mStartAngle) )
                    * - 1 / 2f * itemWidth);
            // 布局child view
            child.layout(left, top,
                    left + itemWidth, top + itemWidth);
            // 叠加尺寸
            mStartAngle += angleDelay;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureSelf(widthMeasureSpec,heightMeasureSpec);
        measureChild();
    }

    private int getDefaultWidth()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 测量菜单宽高
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void measureSelf(int widthMeasureSpec, int heightMeasureSpec){
        int resultWidth=0;
        int resultHeight=0;

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode!=MeasureSpec.EXACTLY||heightMode!=MeasureSpec.EXACTLY){

            resultWidth=getSuggestedMinimumWidth();
            resultWidth=resultHeight==0?getDefaultWidth():resultWidth;

            resultHeight=getSuggestedMinimumHeight();
            resultHeight=resultHeight==0?getDefaultWidth():resultHeight;
        }else {
            resultWidth = resultHeight= Math.min(widthSize, heightSize);
        }
        setMeasuredDimension(resultWidth,resultHeight);
    }
    /**
     * 测量菜单item宽高
     */
    private void measureChild(){
        int resultWidth=0;
        int resultHeight=0;

        // menu item数量
        final int count = getChildCount();
        // menu item尺寸
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;
        // 迭代测量
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                    childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }
        mPadding = RADIO_PADDING_LAYOUT * mRadius;
    }
}
