package com.example.administrator.zhixiao10.Activity;

import android.app.Application;

import com.example.administrator.zhixiao10.lib.ChatConnection;

/**
 * Created by Administrator on 2017/6/14.
 */
public class zhixiaoApp extends Application{

    private ChatConnection myConn;//连接
    private String myAccount;//登录账号

    public ChatConnection getMyConn() {
        return myConn;
    }

    public void setMyConn(ChatConnection myConn) {
        this.myConn = myConn;
    }

    public String getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(String myAccount) {
        this.myAccount = myAccount;
    }
}
