package com.example.administrator.zhixiao10.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.fragments.ListFragment;
import com.example.administrator.zhixiao10.view.ScrollViewPager;
import com.example.administrator.zhixiao10.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/5.
 */
public class home_context_page {
    public MainActivity mActivity;

    public View getmRootView() {
        return mRootView;
    }

    private View mRootView;
    private ArrayList<ListFragment> fragments = new ArrayList<ListFragment>();
    private newListFragmentAdapter mAdapter;
    private ScrollViewPager mViewPager;
         private List<String> mDatas = Arrays.asList("推荐", "搞笑", "赛事", "生活",
   "情感", "热点");
//    private List<String> mDatas = Arrays.asList("短信", "收藏", "推荐");

    private ViewPagerIndicator mIndicator;


    public home_context_page(MainActivity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();
    }


    //初始化布局
    public void initView() {
        mRootView = View.inflate(mActivity, R.layout.home_context_layout, null);
        mViewPager = (ScrollViewPager) mRootView.findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) mRootView.findViewById(R.id.id_indicator);
    }


    //初始化数据
    public void initData() {

        for (int i = 0; i < mDatas.size(); i++) {
            fragments.add(new ListFragment(mDatas.get(i)));
        }


        mAdapter = new newListFragmentAdapter(mActivity.getSupportFragmentManager());


        //设置Tab上的标题
        mIndicator.setmTabTitles(mDatas);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOffscreenPageLimit(mDatas.size());
        //设置关联的ViewPager

        mIndicator.setViewPager(mViewPager, 0);

//        ViewPagerIndicator.PageChangeListener pcl = new ViewPagerIndicator.PageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.i("jj", "onPageSelected: in it!!!!!!!!!!!");
//                fragments.get(position).initDate(mDatas.get(position));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//        mIndicator.setOnPageChangeListener(pcl);


    }


    private class newListFragmentAdapter extends FragmentStatePagerAdapter{
//        private ArrayList<ListFragment> fragments = new ArrayList<ListFragment>();

        public newListFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public ListFragment getItem(int position) {
            Log.i("jj", "getItem: "+position);
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("jj", "destroyItem: "+position);
            super.destroyItem(container, position, object);
        }
    }



}
