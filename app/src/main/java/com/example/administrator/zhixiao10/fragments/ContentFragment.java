package com.example.administrator.zhixiao10.fragments;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.base.BasePager;
import com.example.administrator.zhixiao10.base.pager.HomePager;
import com.example.administrator.zhixiao10.base.pager.ShenbianPager;
import com.example.administrator.zhixiao10.base.pager.Tiebapager;
import com.example.administrator.zhixiao10.view.NoScrollViewPager;
import com.example.administrator.zhixiao10.view.tabButton;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ContentFragment extends BaseFragment implements View.OnClickListener {

    private tabButton[] tab;

    private NoScrollViewPager mViewpager;

    private ArrayList<BasePager> mPagerList;
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        tab = new tabButton[3];
        tab[0] = (tabButton) view.findViewById(R.id.zhuye);
        tab[1] = (tabButton) view.findViewById(R.id.tieba);
        tab[2] = (tabButton) view.findViewById(R.id.shenbian);
        mViewpager = (NoScrollViewPager) view.findViewById(R.id.vp);

        return view;
    }

    @Override
    public void initData() {

        tab[0].setIsSelect(true);
        //初始化3个主页面
        mPagerList = new ArrayList<BasePager>();
//        for (int i = 0;i < 3;i++){
//            BasePager basePager = new BasePager(mActivity);
//            mPagerList.add(basePager);
//        }
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new Tiebapager(mActivity));
        mPagerList.add(new ShenbianPager(mActivity));

        mViewpager.setAdapter(new ContentAdapter());
        setListen();

    }

    private void setListen() {
        for (int i = 0; i < tab.length; i++) {
            tab[i].setOnClickListener(this);
        }
        mPagerList.get(0).initData();//初始化第一个页面
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {//监听当前第几个pager
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0){
                    mPagerList.get(position).initData();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        clearSelect();
        switch (v.getId()) {
            case R.id.zhuye:
                tab[0].setIsSelect(true);
                mViewpager.setCurrentItem(0,false);//第几个跟是否去掉切换页面动画
                break;
            case R.id.tieba:
                tab[1].setIsSelect(true);
                mViewpager.setCurrentItem(1,false);
                break;
            case R.id.shenbian:
                tab[2].setIsSelect(true);
                mViewpager.setCurrentItem(2,false);
                break;

        }
    }

    private void clearSelect() {
        for (int i = 0; i < tab.length; i++) {
            tab[i].setIsSelect(false);
        }
    }


    public class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = mPagerList.get(position);
//            basePager.initData();
            container.addView(basePager.getmRootView());
            return basePager.getmRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }



    public void goutPage(int page){
        clearSelect();
        switch (page){
            case 0:

                tab[0].setIsSelect(true);
                mViewpager.setCurrentItem(0,false);
                break;
            case 1:

                tab[1].setIsSelect(true);
                mViewpager.setCurrentItem(1,false);
                break;
            case 2:

                tab[2].setIsSelect(true);
                mViewpager.setCurrentItem(2,false);
                break;

        }

    }


}







