package com.example.administrator.zhixiao10.base.pager;

import android.app.Activity;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.base.BasePager;
import com.example.administrator.zhixiao10.base.home_context_page;

/**
 * Created by Administrator on 2016/6/5.
 */
public class HomePager extends BasePager{

    home_context_page hcp;

    public HomePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        if (hcp == null){
            hcp = new home_context_page((MainActivity) mActivity);
            fl.addView(hcp.getmRootView());

        }

    }

}

