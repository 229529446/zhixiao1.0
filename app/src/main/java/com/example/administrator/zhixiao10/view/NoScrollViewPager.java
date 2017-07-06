package com.example.administrator.zhixiao10.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/6/5.
 */

/*
* 不能滑动的ViewPager
* */
public class NoScrollViewPager extends ViewPager{
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    * 拦截触屏事件
    * */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }


    /**
     * 表示事件是否拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 事件分发
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

            getParent().requestDisallowInterceptTouchEvent(true);


        return super.dispatchTouchEvent(ev);
    }
}
