package com.example.administrator.zhixiao10.lib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.zhixiao10.bean.User;
import com.example.administrator.zhixiao10.bean.result;
import com.example.administrator.zhixiao10.lib.callback.ObjCallback;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class ConnManager {
    private static ConnManager instance;
    private static Context context;


    public static ConnManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ConnManager.class) {
                if (instance == null) {
                    instance = new ConnManager(context);
                }
            }
        }
        return instance;
    }

    private ConnManager(Context context) {
        this.context = context;

    }

    public void sendRequest(String url, RequestParams params, final ObjCallback callback) {

        //创建连接
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);


        http.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {


                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(responseInfo.result.toString());
                        JsonObject root = element.getAsJsonObject();

                        JsonPrimitive code = root.getAsJsonPrimitive("code");

                        if (code.getAsString().equals("200")) {

                            JsonObject data = root.getAsJsonObject("data");


                            if (data != null) {

                                if (callback != null) {
                                    Object obj = new Gson().fromJson(data, callback.getDataClass());
                                    Log.i("connMangger", "onSuccess: "+obj.toString());
                                    callback.onSuccess(obj);
                                }

                            }
                        }

                        if (code.getAsString().equals("400")) {

                            if (callback != null) {
                                callback.onFailure();
                            }
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {


                    }
                });


    }

}
