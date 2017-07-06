package com.example.administrator.zhixiao10.base.pager;

import android.app.Activity;
import android.view.View;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.base.BasePager;
import com.example.administrator.zhixiao10.base.shenbian_context;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ShenbianPager extends BasePager {


    private shenbian_context shenbian_context;

    public ShenbianPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        if (shenbian_context==null){
            shenbian_context = new shenbian_context((MainActivity) mActivity);
            fl.addView(shenbian_context.getmRootView());
        }


        act_bar.setVisibility(View.GONE);

    }
}
