package com.example.nanfu.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.nanfu.libs.R;

/**
 * Created by Administrator on 2016/9/14.
 * 自定义扭曲图片
 */
public class CustomMeshView extends View {

    private Bitmap mBitmap;

    private Paint mPaint;
    /**
     * 图片横竖方向网格数
     */
    private static int SUB_WIDTH=19,SUB_HEIGHT=19;
    //将图片划分成200*200个小格
    private static final int WIDTH=200;
    private static final int HEIGHT=200;
    //小格相交的总的点数
    private int COUNT=(WIDTH+1)*(HEIGHT+1);
    private float[] verts=new float[COUNT*2];
    private float[] origs=new float[COUNT*2];
    private float k;

    private int endWidth,endHeight;
    private Bitmap dstBitmap;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public CustomMeshView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p>
     * <p>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(Context, AttributeSet, int)
     */
    public CustomMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context,attrs);
        init(context);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @see #View(Context, AttributeSet)
     */
    public CustomMeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context,attrs);
        init(context);
    }

    private void setAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomMeshView);
    }

    private void init(Context context){
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        initBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
        canvas.drawBitmapMesh(dstBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchWrap(event.getX(),event.getY());
        return super.onTouchEvent(event);
    }

    private void initBitmap(){
        int index=0;
        mBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.canvas);
        Matrix matrix=new Matrix();
        matrix.setScale(0.5f,0.5f);
        dstBitmap=Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth()/2,mBitmap.getHeight()/2,matrix,true);
        float bitmapwidth=dstBitmap.getWidth();
        float bitmapheight=dstBitmap.getHeight();
        for(int i=0;i<HEIGHT+1;i++){
            float fy=bitmapwidth/HEIGHT*i;
            for(int j=0;j<WIDTH+1;j++){
                float fx=bitmapheight/WIDTH*j;
                //偶数位记录x坐标  奇数位记录Y坐标
                origs[index*2+0]=verts[index*2+0]=fx;
                origs[index*2+1]=verts[index*2+1]=fy;
                index++;
            }
        }
    }

    private void drawBitmap(Canvas canvas){
        canvas.drawBitmap(dstBitmap,0,dstBitmap.getHeight(),null);
    }

    private void touchWrap(float x,float y){
        for(int i=0;i<COUNT*2;i+=2){
            //x/y轴每个点坐标与当前x/y坐标的距离
            float dx=x-origs[i+0];
            float dy=y-origs[i+1];
            float dd=dx*dx+dy*dy;
            //计算每个坐标点与当前点（x、y）之间的距离
            float d=(float) Math.sqrt(dd);
            //计算扭曲度，距离当前点越远的点扭曲度越小
            float pull=80000/((float)(dd*d));
            //对verts重新赋值
            if(pull>=1){
                verts[i+0]=x;
                verts[i+1]=y;
            }else{
                verts[i+0]=origs[i+0]+dx*pull;
                verts[i+1]=origs[i+1]+dy*pull;
            }

        }
        invalidate();
    }

}
