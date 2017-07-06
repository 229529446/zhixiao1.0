package com.example.administrator.zhixiao10.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.view.CustomImageView;

/**
 * Created by Administrator on 2016/6/5.
 */
public class BasePager {
    public Activity mActivity;

    public View getmRootView() {
        return mRootView;
    }

    private   View mRootView;
    public ImageButton ibtn;
    public FrameLayout fl;
    public TextView tv;
    private CustomImageView cv;
    public LinearLayout act_bar;


    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
    }


    //初始化布局
    public void initView(){
       mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        act_bar = (LinearLayout) mRootView.findViewById(R.id.act_bar);
        fl = (FrameLayout) mRootView.findViewById(R.id.content);
        tv = (TextView) mRootView.findViewById(R.id.account_name);
        cv = (CustomImageView) mRootView.findViewById(R.id.touxiang);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ShowMenu();
            }
        });

        tv.setText( PrefUtils.getAccount(mActivity,"AccountName","").equals("")?PrefUtils.getAccount(mActivity,"AccountID",""):PrefUtils.getAccount(mActivity,"AccountName",""));
    }


    //初始化数据
    public void initData(){

    }
}
