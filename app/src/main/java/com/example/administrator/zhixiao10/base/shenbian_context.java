package com.example.administrator.zhixiao10.base;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zhixiao10.Activity.EditAccoutActivity;
import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.utils.PrefUtils;

/**
 * Created by Administrator on 2017/6/16.
 */
public class shenbian_context {

    public MainActivity mActivity;
    private TextView account,sex,address,phone,username,edit;


    public View getmRootView() {
        return mRootView;
    }

    private View mRootView;


    public shenbian_context(MainActivity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();
    }

    private void initData() {
        String SexString;
        String AddressString;
        String PhoneNumString;
        String NameString;

        account.setText(PrefUtils.getAccount(mActivity,"AccountID","还没填写噢"));


        if (PrefUtils.getAccount(mActivity,"AccountSex","").equals("0")){
            SexString = "还没填写噢";
        }else {
            SexString = PrefUtils.getAccount(mActivity,"AccountSex","还没填写噢").equals("1")?"女":"男";
        }



        AddressString = PrefUtils.getAccount(mActivity,"AccountAddress","").equals("")?"还没填写噢":PrefUtils.getAccount(mActivity,"AccountAddress","");
        PhoneNumString = PrefUtils.getAccount(mActivity,"AccountPhoneNum","").equals("")?"还没填写噢":PrefUtils.getAccount(mActivity,"AccountPhoneNum","");
        NameString = PrefUtils.getAccount(mActivity,"AccountName","").equals("")?"游游乐用户":PrefUtils.getAccount(mActivity,"AccountName","");




        sex.setText(SexString);
        address.setText(AddressString);
        phone.setText(PhoneNumString);
        username.setText(NameString);


        Log.i("shenbian", "initData: "+SexString+"     "+AddressString+"      "+PhoneNumString+"      "+NameString);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EditAccoutActivity.class);
                mActivity.startActivity(intent);

            }
        });



    }

    private void initView() {
        mRootView = View.inflate(mActivity, R.layout.shenbian_fragment, null);
        account = (TextView) mRootView.findViewById(R.id.account);
        sex = (TextView) mRootView.findViewById(R.id.sex);
        address = (TextView) mRootView.findViewById(R.id.address);
        phone = (TextView) mRootView.findViewById(R.id.phone);
        username = (TextView) mRootView.findViewById(R.id.username);
        edit = (TextView) mRootView.findViewById(R.id.edit);

    }
}
