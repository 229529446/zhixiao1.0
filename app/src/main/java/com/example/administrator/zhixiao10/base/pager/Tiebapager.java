package com.example.administrator.zhixiao10.base.pager;

import android.app.Activity;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.base.BasePager;
import com.example.administrator.zhixiao10.base.tieba_context;


/**
 * Created by Administrator on 2016/6/5.
 */
public class Tiebapager extends BasePager {

    private tieba_context tieba_context;

    public Tiebapager(Activity mActivity) {
        super(mActivity);
    }
    @Override
    public void initData() {
        if (tieba_context == null){
            tieba_context = new tieba_context((MainActivity) mActivity);
            fl.addView(tieba_context.getmRootView());
        }

    }


}
