package com.example.administrator.zhixiao10.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.zhixiao10.bean.ChatBean.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/18.
 */
public class PrefUtils {



    public  static final  String PREF_NAME = "config";
    public  static final  String LOGIN = "login";
    public  static  final String CHAT_RECORD = "chat";



    public static String getString(Context ctx,String key, String defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defaultValue);
    }

    public static void setString(Context ctx,String key, String value){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getAccount(Context ctx,String key, String defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(LOGIN,Context.MODE_PRIVATE);
        return sp.getString(key,defaultValue);
    }

    public static void setAccount(Context ctx,String key, String value){
        SharedPreferences sp = ctx.getSharedPreferences(LOGIN,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }



    /**
     * 保存聊天记录
     */
    public static <T> void setRecord(Context ctx,String key, ArrayList<T> datalist){
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_RECORD,Context.MODE_PRIVATE);
        if (null == datalist || datalist.size() <= 0)
            return;
        Gson gson = new Gson();
        String data = gson.toJson(datalist);
        sp.edit().putString(key,data).commit();
    }


    /**
     * 获取聊天记录
     */

    public static  ArrayList<Message> getRecord(Context ctx, String key, String defaultValue){
        ArrayList<Message> datalist = new ArrayList<Message>();
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_RECORD,Context.MODE_PRIVATE);
        String data = sp.getString(key,defaultValue);

        if (data == null)
            return datalist;

        Gson gson = new Gson();
        datalist = gson.fromJson(data,new TypeToken<ArrayList<Message>>(){}.getType());

        return datalist;

    }




}
