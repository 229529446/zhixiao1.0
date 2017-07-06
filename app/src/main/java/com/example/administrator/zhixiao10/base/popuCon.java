package com.example.administrator.zhixiao10.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.Activity.passageActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.fragments.ListFragment;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.view.ScrollViewPager;
import com.example.administrator.zhixiao10.view.ViewPagerIndicator;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */
public class popuCon {

    public passageActivity mActivity;

    public View getmRootView() {
        return mRootView;
    }

    private View mRootView;
    private String pid;

    private EditText CommentText;
    private Button Send_Button;


    public popuCon(Activity mActivity, String pid) {
        this.mActivity = (passageActivity) mActivity;
        this.pid = pid;
        initView();
        initData();
    }

    private void initData() {
        Send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, pid, Toast.LENGTH_SHORT).show();
            }
        });

        Send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommentText.getText() == null || CommentText.getText().toString() == "") {
                    Toast.makeText(mActivity, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    sendComment();
                }


                mActivity.getCommentFragment().getPopupwindow().dismiss();
            }
        });
    }


    public void initView() {
        mRootView = View.inflate(mActivity, R.layout.popu_con_layout, null);

        Send_Button = (Button) mRootView.findViewById(R.id.send_comment);
        CommentText = (EditText) mRootView.findViewById(R.id.comment_edtext);


    }


    public void sendComment() {
        String cid = PrefUtils.getAccount(mActivity, "AccountID", "");
        String cer = PrefUtils.getAccount(mActivity, "AccountName", cid);
        String con = CommentText.getText().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String ctime = formatter.format(curDate);


        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pid", pid);
        requestParams.addBodyParameter("cid", cid);

        requestParams.addBodyParameter("con", con);
        requestParams.addBodyParameter("ctime", ctime);
        requestParams.addBodyParameter("cer", cer);


        HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1000 * 5);

        httpUtils.send(HttpRequest.HttpMethod.POST, ConnectionUrl.SERVICE_URL + "ReleaseComment",requestParams,new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseInfo.result.toString());
                JsonObject root = element.getAsJsonObject();
                JsonPrimitive code = root.getAsJsonPrimitive("code");
                if (code.getAsString().equals("200")) {
                    Toast.makeText(mActivity, "评论成功", Toast.LENGTH_SHORT).show();
                    CommentText.setText("");
                    mActivity.getCommentFragment().initData();
                } else {
                    Toast.makeText(mActivity, "评论失败", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("sendComment", "onFailure: "+msg);
                Toast.makeText(mActivity, "网络出问题咯", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
