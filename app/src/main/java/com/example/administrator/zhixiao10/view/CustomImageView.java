package com.example.administrator.zhixiao10.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import android.widget.ImageView;

import com.example.administrator.zhixiao10.R;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class CustomImageView extends ImageView {

    //图标类型
    private  int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    /**
     * 图片 位图
     */
    private Bitmap mSrc;

    /**
     * 圆角的大小
     */
    private int mRadius;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;



//    public void setImageBitmap(Bitmap bm) {
//        this.mSrc = bm;
//    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        this.mSrc = bm;
    }


    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context) {
        this(context,null);
    }


    /**
     * 初始化参数
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义配置文件
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView,defStyleAttr,0);

        for(int i = 0;i < a.getIndexCount();i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_src:
                    mSrc = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.CustomImageView_type:
                    type = a.getInt(attr,0);//默认为0，（圆形）
                    break;
                case R.styleable.CustomImageView_borderRadius:
                    mRadius= a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));// 默认为10DP
                    break;


            }
        }

        a.recycle();
    }


    /**
     * 确定控件宽高，由两个参数组合确定，mode与size，可以用onMeasure传入的两个参数获取到
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);


        if (specMode == MeasureSpec.EXACTLY)// 是一个精确的数值
        {
            mWidth = specSize;
        } else
        {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight()
                    + mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST)//  wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            } else

                mWidth = desireByImg;
        }


        /**
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else
        {
            int desire = getPaddingTop() + getPaddingBottom()
                    + mSrc.getHeight();

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            } else
                mHeight = desire;
        }


        //设置宽高

        setMeasuredDimension(mWidth, mHeight);

    }


    /**
     * 绘制
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {

        switch (type)
        {
            // 如果是TYPE_CIRCLE绘制圆形
            case TYPE_CIRCLE:

                int min = Math.min(mWidth, mHeight);
                /**
                 * 长度如果不一致，按小的值进行压缩
                 */
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);

                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                break;

            case TYPE_ROUND:
                canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
                break;

        }

    }


    /**
     * 画圆角矩形
     * @param mSrc
     * @return
     */

    private Bitmap createRoundConerImage(Bitmap mSrc) {

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, mSrc.getWidth(), mSrc.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSrc, 0, 0, paint);
        return target;

    }


    /**
     * 画圆形
     *
     * @param mSrc
     * @param min
     * @return
     */

    private Bitmap createCircleImage(Bitmap mSrc, int min) {

        final Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，第一个绘制的是个圆形，第二个绘制的是个Bitmap，于是交集为圆形，展现的是Bitmap，就实现了圆形图片效果。
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(mSrc, 0, 0, paint);
        return target;
    }




}
