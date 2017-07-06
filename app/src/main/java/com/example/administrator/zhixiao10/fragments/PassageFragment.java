package com.example.administrator.zhixiao10.fragments;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhixiao10.Activity.passageActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.passage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/5/30.
 */
public class PassageFragment extends BaseFragment {

    private TextView tv, conTitle, conTime;
    private passage ps;
    private ImageView titleImage;
    private BitmapUtils bitmapUtils;
    private View view;
    private ImageButton BackBtn;

    @Override
    public View initViews() {

        view = View.inflate(mActivity, R.layout.passage_layout, null);

        BackBtn = (ImageButton) view.findViewById(R.id.back_btn);
        tv = (TextView) view.findViewById(R.id.tv_con);
        conTitle = (TextView) view.findViewById(R.id.con_title);
        conTime = (TextView) view.findViewById(R.id.con_time);
        titleImage = (ImageView) view.findViewById(R.id.title_image);
        return view;
    }

    @Override
    public void initData() {

        bitmapUtils = new BitmapUtils(mActivity);
        bitmapUtils.configDefaultLoadingImage(R.drawable.bagg);

        String pid = mActivity.getIntent().getStringExtra("pid");

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pid", pid);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.POST, ConnectionUrl.SERVICE_URL + "getPassageByPid", requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseInfo.result.toString());
                JsonObject root = element.getAsJsonObject();
                JsonPrimitive code = root.getAsJsonPrimitive("code");
                if (code.getAsString().equals("200")) {
                    JsonObject data = root.getAsJsonObject("data");
                    ps = new Gson().fromJson(data, new TypeToken<passage>(){}.getType());
                    tv.setText(ps.getPcon());
                    conTitle.setText(ps.getPtitle());
                    conTime.setText(ps.getPtime());
                    bitmapUtils.display(titleImage,ConnectionUrl.ROOT_URL+"PassageImgs/"+ps.getPurl()+".jpg");
                }
            }
            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passageActivity passageActivity = (passageActivity) mActivity;
                passageActivity.changPage(1);
            }
        });


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });








        super.initData();
    }
}
