package com.example.administrator.zhixiao10.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.utils.PrefUtils;

/**
 * Created by Administrator on 2016/5/30.
 */
public class LeftFragment extends BaseFragment{

    private  View view;
    private TextView username;
    private LinearLayout MyCon,msg;
    private MainActivity mainActivity;

    @Override
    public View initViews() {

        view =  View.inflate(mActivity, R.layout.left_fragment, null);
        MyCon = (LinearLayout) view.findViewById(R.id.my_con);
        msg = (LinearLayout) view.findViewById(R.id.msg);
        username = (TextView) view.findViewById(R.id.username);

        return view;
    }


    @Override
    public void initData() {
        mainActivity = (MainActivity) mActivity;

        MyCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.getContentFragment().goutPage(2);
                mainActivity.showContent();

            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getContentFragment().goutPage(1);
                mainActivity.showContent();
            }
        });

        username.setText( PrefUtils.getAccount(mActivity,"AccountName","").equals("")?PrefUtils.getAccount(mActivity,"AccountID",""):PrefUtils.getAccount(mActivity,"AccountName",""));


        super.initData();


    }
}
