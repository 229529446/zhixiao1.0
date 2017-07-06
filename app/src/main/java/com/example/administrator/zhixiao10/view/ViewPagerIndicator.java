package com.example.administrator.zhixiao10.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
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

import com.example.administrator.zhixiao10.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class ViewPagerIndicator extends LinearLayout {


    /**
     * 绘制画笔
     */
    private Paint mPaint;

    /**
     * 绘制的图像
     */
    private Path mPath;

    /**
     * 图像的宽度
     */
    private int mTriangleWidth;


    /**
     * 图像的高度
     */
    private float mTriangleHeight;

    /**
     * 指示器的宽度单个为Tab的5/6
     */
    private static final float RADIO_TRIANGEL = 5.0f/6;

    /**
     * 指示器的最大宽度
     */
    private  final int DIMENSION_TRIANGEL_WIDTH =  (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        WindowManager mWindowManager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * 初始时，指示器的偏移量
     */
    private int mInitTranslationX;


    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    /**
     * 默认的Tab数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;


    /**
     * tab的数量
     */
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;


    /**
     * tab上的内容
     */
    private List<String> mTabTitles;

    /**
     * 绑定的ViewPage
     */
    public ViewPager mViewPager;

    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0xff858383;



    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xe03285ff;




    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context,attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_item_count,COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0)
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        a.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#e03285ff"));
        mPaint.setStyle(Paint.Style.FILL);



    }

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }


    /**
     * 设置布局中的一些必要属性
     */
    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        int cCount = getChildCount();
        if(cCount == 0)
            return;
        for (int i = 0;i < cCount;i++){
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(lp);

        }
        // 设置点击事件
        setItemClickEvent();


    }


    /**
     * 设置点击事件
     */

    private void setItemClickEvent() {
        int cCount  = getChildCount();
        for (int i = 0;i < cCount;i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });

        }


    }

    /**
     * 初始化指示器形状的大小
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (w/mTabVisibleCount * RADIO_TRIANGEL);
        mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);
        //初始化指示器形状
        initTriangle();

        //初始时的偏移量
        mInitTranslationX = getWidth()/mTabVisibleCount/2 - mTriangleWidth/2;


    }


    private void initTriangle() {
        mPath = new Path();

//        mTriangleHeight = (int) (mTriangleWidth/2/Math.sqrt(2));

        mTriangleHeight = (float) (mTriangleWidth*0.75);
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth, (float) (mTriangleHeight*0.1));
        mPath.lineTo(0, (float) (mTriangleHeight*0.1));
        mPaint.ascent();
        mPath.close();

    }


    /**
     * 绘制指示器
     */

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        //画笔平移到正确的位置
        canvas.translate(mInitTranslationX+mTranslationX,getHeight()-10);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);

    }



    //设置关联的ViewPage
    public void setViewPager(ViewPager mViewPager, int pos){
        this.mViewPager = mViewPager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滚动
                scroll(position, positionOffset);

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }

            }

            @Override
            public void onPageSelected(int position) {
                //设置字体颜色高亮
                resetTextViewColor();
                highLightTextView(position);

                //回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });

        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }





    /**
     * 对外的ViewPager的回调接口
     *
     */
    public interface PageChangeListener{
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }





    /**
     * 指示器跟随手指移动，以及容器滚动
     * @param position
     * @param positionOffset
     */

    private void scroll(int position, float positionOffset) {

        //不断改变偏移量，invalidate
        mTranslationX = getWidth() / mTabVisibleCount *                                                       (position + positionOffset);
        int tabWidth = getScreenWidth()/mTabVisibleCount;

        //容器滚动，当移动的倒数最后一个的时候，开始滚动
        if (positionOffset > 0 && position >= (mTabVisibleCount-2) && getChildCount() > mTabVisibleCount){
            if (mTabVisibleCount != 1){
                this.scrollTo( (position-(mTabVisibleCount-2))*tabWidth + (int)(tabWidth * positionOffset),0);
            }else {
                //count为1特殊处理
                this.scrollTo(position * tabWidth + (int )(tabWidth * positionOffset),0);
            }
        }
        invalidate();

    }

    /**
     * 高亮文本
     * @param position
     */


    private void highLightTextView(int position) {
        View view = getChildAt(position);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }

    }

    /**
     * 重置文本颜色
     */

    private void resetTextViewColor() {

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }


    public void setmTabVisibleCount(int mTabVisibleCount) {
        this.mTabVisibleCount = mTabVisibleCount;
    }



    public void setmTabTitles(List<String> datas) {
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0) {
            this.removeAllViews();
            this.mTabTitles = datas;

            for (String title : mTabTitles) {
                // 添加view
                addView(generateTextView(title));
            }
            // 设置item的click事件
            setItemClickEvent();
        }
    }


    /**
     * 根据标题生成我们的TextView
     *
     * @param title
     * @return
     */
    private TextView generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(title);
        tv.setLayoutParams(lp);
        return tv;
    }





}







































